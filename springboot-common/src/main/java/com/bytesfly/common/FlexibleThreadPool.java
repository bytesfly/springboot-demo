package com.bytesfly.common;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.system.SystemUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 可伸缩的线程池，可根据当前任务数自动调整corePoolSize
 * 实际场景中，有时很难估算出合理的线程数
 * 参考美团技术团队博客而做此实现，详情见：https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
 */
public class FlexibleThreadPool {
    private static final int CORE_POOL_SIZE = (int) SystemUtil.getInt("FLEXIBLE_THREAD_POOL_CORE_SIZE", 8);
    private static final int MAX_POOL_SIZE = (int) SystemUtil.getInt("FLEXIBLE_THREAD_POOL_MAX_SIZE", 128);
    private static final long KEEP_ALIVE_MINUTES = SystemUtil.getInt("FLEXIBLE_THREAD_POOL_KEEP_ALIVE_MINUTES", 5);

    private static final int MAX_TASK_SIZE = (int) SystemUtil.getInt("FLEXIBLE_THREAD_POOL_QUEUE_SIZE", 128);

    private static final double THREAD_INCREASE_FACTOR = 1.5;

    // 可动态调整coreSize大小的线程池
    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_MINUTES,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(MAX_TASK_SIZE),
            ThreadUtil.newNamedThreadFactory("flexible-thread-pool-", false),
            new ThreadPoolExecutor.CallerRunsPolicy());

    static {
        THREAD_POOL.allowCoreThreadTimeOut(true);

        // 开启定期更新threadPool的coreSize任务
        schedule();

        // 关闭线程池
        Runtime.getRuntime().addShutdownHook(new Thread(THREAD_POOL::shutdown,
                "FlexibleThreadPool-ShutdownHook-Thread"));
    }

    private static void schedule() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "AdjustFlexibleThreadPoolCoreSize");
            thread.setDaemon(true);
            return thread;
        });
        // 每3秒检测是否需要调整corePoolSize
        final long period = 3L;
        scheduler.scheduleAtFixedRate(FlexibleThreadPool::adjustThreadPoolCoreSize, 1, period, TimeUnit.SECONDS);
    }

    private static void adjustThreadPoolCoreSize() {
        final int minThreads = CORE_POOL_SIZE;
        final int maxThreads = MAX_POOL_SIZE;
        final double factor = THREAD_INCREASE_FACTOR;

        final int coreSize = THREAD_POOL.getCorePoolSize();
        int size = coreSize;
        final int n = (int) (coreSize / factor);

        if (!THREAD_POOL.getQueue().isEmpty()) {
            // 任务队列中有排队任务，应适当增加coreSize
            size = (int) (coreSize * factor);
        } else if (THREAD_POOL.getActiveCount() <= n) {
            // 线程池中活跃线程数低于 coreSize/factor时，应适当减少coreSize
            size = n + 3;
        } else {
            return;
        }
        // coreSize不能低于minThreads，也不能高于maxThreads
        size = Math.min(Math.max(minThreads, size), maxThreads);

        // 这个判断是必须的(coreSize不需要改变时不要调用setCorePoolSize，否则会频繁interrupt因poll而阻塞的thread)
        if (size != coreSize) {
            THREAD_POOL.setCorePoolSize(size);
        }
    }

    public static ThreadPoolExecutor getThreadPool() {
        return THREAD_POOL;
    }

    /**
     * 提交任务(有返回值)
     */
    public static <T> Future<T> submitTask(Callable<T> task) {
        return THREAD_POOL.submit(task);
    }

    /**
     * 提交任务(无返回值)
     */
    public static void submitTask(Runnable task) {
        THREAD_POOL.submit(task);
    }

    /**
     * 对外暴露 线程池相关的指标信息, 可用于对系统吞吐, 性能的监控等
     */
    public static Map<String, Object> metrics(ThreadPoolExecutor threadPool) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("active_count", threadPool.getActiveCount());
        map.put("pool_size", threadPool.getPoolSize());
        map.put("core_size", threadPool.getCorePoolSize());
        map.put("queue_size", threadPool.getQueue().size());
        map.put("keep_alive_minute", threadPool.getKeepAliveTime(TimeUnit.MINUTES));
        map.put("history_largest_pool_size", threadPool.getLargestPoolSize());
        map.put("max_pool_size", threadPool.getMaximumPoolSize());
        map.put("task_count", threadPool.getTaskCount());
        map.put("completed_task_count", threadPool.getCompletedTaskCount());
        return map;
    }

    /**
     * 查看当前线程池的指标信息
     */
    public static Map<String, Object> metrics() {
        return metrics(THREAD_POOL);
    }
}
