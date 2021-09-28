package com.bytesfly.validation.controller;

import com.bytesfly.validation.model.ApiResponse;
import com.bytesfly.validation.model.request.CreateReq;
import com.bytesfly.validation.model.request.UpdateReq;
import com.bytesfly.validation.validator.NotEqual;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation")
@Validated
public class ValidationController {

    @PostMapping
    public ApiResponse create(@RequestBody @Validated CreateReq req) {
        return ApiResponse.successful();
    }

    @PutMapping
    public ApiResponse update(@RequestBody @Validated UpdateReq req) {
        return ApiResponse.successful();
    }

    @GetMapping
    public ApiResponse get(@RequestParam("id") @NotEqual(invalidValue = "1", message = "ID为1的数据不允许查询") Long id) {
        return ApiResponse.successful();
    }

    @DeleteMapping("{id}")
    public ApiResponse delete(@PathVariable("id") @NotEqual(invalidValue = "1", message = "ID为1的数据不允许删除") Long id) {
        return ApiResponse.successful();
    }
}
