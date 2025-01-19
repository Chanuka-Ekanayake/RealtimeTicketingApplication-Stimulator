package org.example.ticketingapplication.util;

import org.springframework.stereotype.Component;


import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Component
public class ThreadPoolManager {

    private static ThreadPoolExecutor executor;

    public ThreadPoolManager() {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setCorePoolSize(10); //Minimum Number of threads in the threadPool
        executor.setMaximumPoolSize(50); //Maximum Number of Threads in the ThreadPool
        executor.setKeepAliveTime(60, TimeUnit.SECONDS); // Keep idle threads alive for 60 seconds

    }

    //Submit a task to threadPool
    public static void submitTask(Runnable task) {
        if (executor.isShutdown()) {
            System.out.println("Thread pool is shutdown, cannot submit task.");
            return;
        }
        executor.submit(task);
    }

    public static void submitPriorityTask(Thread task) {
        if (executor.isShutdown()) {
            System.out.println("Thread pool is shutdown, cannot submit priority task.");
            return;
        }
        executor.submit(() -> {
            Thread.currentThread().setPriority(task.getPriority());
            task.run();
        });
    }

    public static void shutdown() {
        executor.shutdownNow();
        System.out.println("ThreadPool has been shut down!");
    }

    public int getPoolSize() {
        return executor.getPoolSize();
    }

    public int getActiveThreadCount() {
        return executor.getActiveCount();
    }

    public static void initializeNewPool() {
        if (executor.isShutdown()) {
            executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            System.out.println("New thread pool initialized.");
        }
    }

    public void waitForCompletion() {
        try {
            Thread.sleep(5000); // Wait for threads to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    
}
