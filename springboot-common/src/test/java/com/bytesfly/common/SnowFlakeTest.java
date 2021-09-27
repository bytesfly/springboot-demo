package com.bytesfly.common;

import org.junit.jupiter.api.Test;

public class SnowFlakeTest {

    @Test
    public void t1() {
        SnowFlake snowFlake = new SnowFlake(0);

        for (int i = 0; i < 10; i++) {
            System.out.println(snowFlake.nextId());
        }
    }
}
