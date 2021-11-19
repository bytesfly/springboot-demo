package com.bytesfly.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytesfly.mybatis.model.db.Book;
import com.bytesfly.mybatis.model.db.resultmap.BookAgg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper extends BaseMapper<Book> {

    @ResultMap("auto.mybatis-plus_BookAgg")
    @Select({"SELECT tb_book.id, max(tb_book.name) as name, max(tb_book.author) as author, array_agg(distinct tb_hero.id order by tb_hero.id asc) as hero_ids" +
            " FROM tb_hero" +
            " INNER JOIN tb_book" +
            " ON tb_hero.bid = tb_book.id" +
            " GROUP BY tb_book.id"})
    List<BookAgg> agg();
}
