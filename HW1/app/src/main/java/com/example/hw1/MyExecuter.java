package com.example.hw1;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MyExecuter
{
    private static ThreadPoolExecutor executor;

    public static ThreadPoolExecutor getExecutor()
    {
        return executor;
    }

    public static void createExecutor()
    {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    }
}
