package com.bytesfly.mybatis.model.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@TableName(value = "tb_hero", autoResultMap = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hero {

    @TableId("id")
    private Long id;

    @TableField(value = "name", keepGlobalFormat = true)
    private String name;

    @TableField(value = "age", keepGlobalFormat = true)
    private Integer age;

    @TableField(value = "skill", keepGlobalFormat = true)
    private String skill;

    @TableField(value = "bid", keepGlobalFormat = true)
    private Long bookId;

    // *********************************
    // 数据库表中不存在以下字段(表join时会用到)
    // *********************************

    @TableField(value = "book_name", exist = false)
    private String bookName;

    @TableField(value = "author", exist = false)
    private String author;
}
