package com.tp.wechat;

import com.tp.wechat.future.futureTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new futureTest("A"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(futureTask);
        System.out.println("请求完毕！");
        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }
        System.out.println("返回的数据为"+futureTask.get());
    }
}
