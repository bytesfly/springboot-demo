package com.bytesfly.mybatis.controller;

import com.bytesfly.mybatis.model.PageResult;
import com.bytesfly.mybatis.model.db.Hero;
import com.bytesfly.mybatis.model.param.QueryHeroParam;
import com.bytesfly.mybatis.service.HeroService;
import com.bytesfly.validation.component.IdGenerator;
import com.bytesfly.validation.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hero")
public class HeroController {

    @Autowired
    private HeroService heroService;

    @GetMapping("{id}")
    public ApiResponse<Hero> queryById(@PathVariable("id") Long id) {
        return ApiResponse.successful(heroService.getById(id));
    }

    @GetMapping("page")
    public ApiResponse<List<Hero>> queryById(QueryHeroParam param) {
        PageResult<Hero> result = heroService.query(param);
        return ApiResponse.successful(result.getData(), result.getTotal());
    }

    @PostMapping
    public ApiResponse<Boolean> create(@RequestBody Hero hero) {
        hero.setId(IdGenerator.nextId());
        return ApiResponse.successful(heroService.save(hero));
    }

    @PutMapping
    public ApiResponse<Boolean> update(@RequestBody Hero hero) {
        return ApiResponse.successful(heroService.updateById(hero));
    }

    @DeleteMapping("{id}")
    public ApiResponse<Boolean> delete(@PathVariable("id") Long id) {
        return ApiResponse.successful(heroService.removeById(id));
    }
}
