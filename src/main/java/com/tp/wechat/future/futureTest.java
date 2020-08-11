package com.tp.wechat.future;

import java.util.concurrent.Callable;

public class futureTest implements Callable<String> {
    private String value;
    public futureTest(String value){
        this.value = value;
    }
    @Override
    public String call() throws Exception {
        StringBuffer bf = new StringBuffer();
        bf.append(value);
        bf.append(value);
        bf.append(value);
        Thread.sleep(1000);
        return bf.toString();
    }
}
