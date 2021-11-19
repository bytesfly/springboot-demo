package com.bytesfly.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bytesfly.mybatis.model.PageResult;
import com.bytesfly.mybatis.model.db.Hero;
import com.bytesfly.mybatis.model.param.QueryHeroParam;

public interface HeroService extends IService<Hero> {

    PageResult<Hero> query(QueryHeroParam param);
}
