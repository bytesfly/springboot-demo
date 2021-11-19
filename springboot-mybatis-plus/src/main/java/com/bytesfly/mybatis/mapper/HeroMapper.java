package com.bytesfly.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bytesfly.mybatis.model.db.Hero;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HeroMapper extends BaseMapper<Hero> {
    @ResultMap("mybatis-plus_Hero")
    @Select({"SELECT tb_hero.*, tb_book.name as book_name, tb_book.author" +
            " FROM tb_hero" +
            " LEFT JOIN tb_book" +
            " ON tb_hero.bid = tb_book.id" +
            " ${ew.customSqlSegment}"})
    IPage<Hero> pageQueryHero(@Param(Constants.WRAPPER) Wrapper<Hero> queryWrapper,
                              Page<Hero> page);
}
