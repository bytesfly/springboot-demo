package com.bytesfly.mybatis.model.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@TableName("tb_book")
public class Book {

    @TableId("id")
    protected Long id;

    @TableField(value = "name", keepGlobalFormat = true)
    protected String name;

    @TableField(value = "author", keepGlobalFormat = true)
    protected String author;
}
