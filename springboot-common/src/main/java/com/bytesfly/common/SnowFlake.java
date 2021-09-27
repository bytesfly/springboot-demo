package com.bytesfly.common;

import com.bytesfly.common.exception.SnowFlakeException;

/**
 * 雪花算法 缩短位数
 */
public class SnowFlake {
    /**
     * 起始的时间戳
     */
    private static final long START_STAMP = 1589036800000L;

    /**
     * 机器标识占用的位数（2^5=32）
     */
    private static final long MACHINE_BIT = 5;

    /**
     * 序列号占用的位数，相同毫秒内，序列号自增（2^10=1024）
     */
    private static final long SEQUENCE_BIT = 10;

    /**
     * 机器标识的最大值31
     */
    private static final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);

    /**
     * 序列号的最大值（2^10=1024）
     */
    private static final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 机器标识向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;

    /**
     * 时间戳向左的位移
     */
    private static final long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    private long machineId;       //机器标识
    private long sequence = 0L;   //序列号
    private long lastStamp = -1L; //上一次时间戳

    public SnowFlake(long machineId) {
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     */
    public synchronized long nextId() {
        long current = current();
        if (current < lastStamp) {
            throw new SnowFlakeException("Clock moved backwards.  Refusing to generate id");
        }

        if (current == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                current = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = current;

        return (current - START_STAMP) << TIMESTAMP_LEFT //时间戳部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = current();
        while (mill <= lastStamp) {
            mill = current();
        }
        return mill;
    }

    private long current() {
        return System.currentTimeMillis();
    }
}
