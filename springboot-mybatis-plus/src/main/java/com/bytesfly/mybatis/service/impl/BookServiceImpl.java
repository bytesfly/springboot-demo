package com.bytesfly.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bytesfly.mybatis.mapper.BookMapper;
import com.bytesfly.mybatis.model.db.Book;
import com.bytesfly.mybatis.model.db.resultmap.BookAgg;
import com.bytesfly.mybatis.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl
        extends ServiceImpl<BookMapper, Book>
        implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<BookAgg> agg() {
        List<BookAgg> result = bookMapper.agg();
        return result;
    }
}
