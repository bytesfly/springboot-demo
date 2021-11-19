package com.bytesfly.mybatis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bytesfly.mybatis.mapper.HeroMapper;
import com.bytesfly.mybatis.model.PageResult;
import com.bytesfly.mybatis.model.db.Hero;
import com.bytesfly.mybatis.model.param.QueryHeroParam;
import com.bytesfly.mybatis.service.HeroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HeroServiceImpl
        extends ServiceImpl<HeroMapper, Hero>
        implements HeroService {

    @Autowired
    private HeroMapper heroMapper;

    @Override
    public PageResult<Hero> query(QueryHeroParam param) {
        QueryWrapper<Hero> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StrUtil.isNotBlank(param.getSkill()), Hero::getSkill, param.getSkill())
                .eq(param.getBookId() != null, Hero::getBookId, param.getBookId());
        // 分页
        Page<Hero> p = new Page<>(param.getPage(), param.getSize());
        // 按照id排序
        p.addOrder(new OrderItem("id", true));

        // 分页查询
        IPage<Hero> result = heroMapper.pageQueryHero(queryWrapper, p);
        List<Hero> heroes = result.getRecords();

        return new PageResult<>(param.getPage(), param.getSize(), result.getTotal(), heroes);
    }
}
