package com.bytesfly.mybatis.model.db.resultmap;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bytesfly.mybatis.annotation.AutoResultMap;
import com.bytesfly.mybatis.model.db.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AutoResultMap
public class BookAgg extends Book {

    @TableField(value = "hero_ids", keepGlobalFormat = true)
    private Object heroIds;
}
