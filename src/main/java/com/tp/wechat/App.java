package com.tp.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 *
 */
@MapperScan("com.tp.wechat.mapper")
@SpringBootApplication

public class App 
{
    public static void main( String[] args )
    
    {
    	SpringApplication.run(App.class, args);
    }
}
