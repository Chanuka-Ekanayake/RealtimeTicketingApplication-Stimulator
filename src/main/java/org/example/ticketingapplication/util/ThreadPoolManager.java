package org.example.ticketingapplication.util;

import org.springframework.stereotype.Component;


import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Component
public class ThreadPoolManager {

    private ThreadPoolExecutor executor;

    public ThreadPoolManager() {
        this.executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.executor.setCorePoolSize(10); //Minimum Number of threads in the threadPool
        this.executor.setMaximumPoolSize(50); //Maximum Number of Threads in the ThreadPool
        this.executor.setKeepAliveTime(60, TimeUnit.SECONDS); // Keep idle threads alive for 60 seconds

    }

    //Submit a task to threadPool
    public void submitTask(Runnable task) {
        if (executor.isShutdown()) {
            System.out.println("Thread pool is shutdown, cannot submit task.");
            return;
        }
        executor.submit(task);
    }

    public void shutdown() {
        executor.shutdown();
        System.out.println("ThreadPool has been shut down!");
    }

    public int getPoolSize() {
        return executor.getPoolSize();
    }

    public int getActiveThreadCount() {
        return executor.getActiveCount();
    }

    public void initializeNewPool() {
        if (executor.isShutdown()) {
            executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            System.out.println("New thread pool initialized.");
        }
    }
}
