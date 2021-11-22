package com.bytesfly.mybatis.model.db.resultmap;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bytesfly.mybatis.annotation.AutoResultMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AutoResultMap
public class BookAgg {

    @TableId("id")
    private Long bookId;

    @TableField("name")
    private String bookName;

    @TableField("hero_ids")
    private Object heroIds;
}
