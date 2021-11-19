package com.bytesfly.mybatis.controller;

import com.bytesfly.mybatis.model.db.resultmap.BookAgg;
import com.bytesfly.mybatis.service.BookService;
import com.bytesfly.validation.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("agg")
    public ApiResponse<List<BookAgg>> queryById() {
        return ApiResponse.successful(bookService.agg());
    }
}
