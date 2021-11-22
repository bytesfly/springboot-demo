package com.bytesfly.mybatis.config;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.bytesfly.mybatis.annotation.AutoResultMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 可添加一些插件
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = "com.bytesfly.mybatis.mapper")
@Slf4j
public class MybatisPlusConfig {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 分页插件(根据jdbcUrl识别出数据库类型, 自动选择适合该方言的分页插件)
     * 相关使用说明: https://baomidou.com/guide/page.html
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(DataSourceProperties dataSourceProperties) {

        String jdbcUrl = dataSourceProperties.getUrl();
        DbType dbType = JdbcUtils.getDbType(jdbcUrl);

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(dbType));
        return interceptor;
    }

    /**
     * @AutoResultMap注解的实体类自动构建resultMap并注入
     */
    @PostConstruct
    public void initAutoResultMap() {
        try {
            log.info("--- start register @AutoResultMap ---");

            String namespace = "auto";

            String packageName = "com.bytesfly.mybatis.model.db.resultmap";
            Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(packageName, AutoResultMap.class);

            org.apache.ibatis.session.Configuration configuration = sqlSessionTemplate.getConfiguration();

            for (Class clazz : classes) {
                MapperBuilderAssistant assistant = new MapperBuilderAssistant(configuration, "");
                assistant.setCurrentNamespace(namespace);
                TableInfo tableInfo = TableInfoHelper.initTableInfo(assistant, clazz);

                if (!tableInfo.isAutoInitResultMap()) {
                    // 设置 tableInfo的autoInitResultMap属性 为 true
                    ReflectUtil.setFieldValue(tableInfo, "autoInitResultMap", true);
                    // 调用 tableInfo#initResultMapIfNeed() 方法，自动构建 resultMap 并注入
                    ReflectUtil.invoke(tableInfo, "initResultMapIfNeed");
                }
            }

            log.info("--- finish register @AutoResultMap ---");
        } catch (Throwable e) {
            log.error("initAutoResultMap error", e);
            System.exit(1);
        }
    }
}
