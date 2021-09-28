package com.bytesfly.validation.service;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    /**
     * 是否是系统保留邮箱
     */
    public boolean isSystemEmail(String email) {
        // 模拟从缓存或db查询数据
        List<String> systemMails = CollUtil.toList("git@github.com", "root@github.com");
        return systemMails.contains(email);
    }
}
