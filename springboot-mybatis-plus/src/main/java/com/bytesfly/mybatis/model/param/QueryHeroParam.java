package com.bytesfly.mybatis.model.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QueryHeroParam {

    private Long bookId;

    private String skill;

    private Integer page = 1;

    private Integer size = 5;
}
