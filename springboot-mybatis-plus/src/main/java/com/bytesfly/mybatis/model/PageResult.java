package com.bytesfly.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    // 当前页
    private int page;

    // 每页数据量
    private int size;

    // 总数
    private long total;

    // 本次查询出来的数据
    private List<T> data;
}
