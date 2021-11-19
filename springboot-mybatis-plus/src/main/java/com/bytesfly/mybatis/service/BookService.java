package com.bytesfly.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bytesfly.mybatis.model.db.Book;
import com.bytesfly.mybatis.model.db.resultmap.BookAgg;

import java.util.List;

public interface BookService extends IService<Book> {

    List<BookAgg> agg();
}
