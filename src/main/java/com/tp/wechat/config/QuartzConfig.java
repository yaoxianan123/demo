package com.tp.wechat.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tp.wechat.job.QuartzGetTokenJob;
import com.tp.wechat.job.QuartzJob1;

@Configuration
public class QuartzConfig {
	@Bean
    public JobDetail jobDetail1(){
        return JobBuilder.newJob(QuartzJob1.class).storeDurably().build();
	}
	@Bean
    public JobDetail jobDetail2(){
        return JobBuilder.newJob(QuartzGetTokenJob.class).storeDurably().build();
	}
	
/*	 @Bean
	    public Trigger trigger1(){
	        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
	                .withIntervalInSeconds(1) //每一秒执行一次
	                .repeatForever(); //永久重复，一直执行下去
	        return TriggerBuilder.newTrigger()
	                .forJob(jobDetail1())
	                .withSchedule(scheduleBuilder)
	                .build();
	    }*/
/*	 @Bean
	    public Trigger trigger2(){
	        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
	                .withIntervalInSeconds(60*60*60) //每一秒执行一次
	                .repeatForever(); //永久重复，一直执行下去
	        return TriggerBuilder.newTrigger()
	                .forJob(jobDetail2())
	                .withSchedule(scheduleBuilder)
	                .build();
	    }*/
}
