package com.bytesfly.validation.component;

import com.bytesfly.common.SnowFlake;
import org.springframework.stereotype.Component;

/**
 * ID生成器(使用Snowflake算法)
 */
@Component
public class IdGenerator {

    private static final SnowFlake snowFlake = new SnowFlake(0);

    public static Long nextId() {
        return snowFlake.nextId();
    }
}
