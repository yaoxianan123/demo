package com.tp.wechat.job;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tp.wechat.entity.Token;
import com.tp.wechat.service.TokenService;
import com.tp.wechat.utill.DateUtill;
import com.tp.wechat.utill.HttpClientUtill;
import com.tp.wechat.utill.RedisUtils;

import ch.qos.logback.classic.Logger;

public class QuartzGetTokenJob extends QuartzJobBean{


	@Autowired
	TokenService service;
	
	
	@Autowired
	RedisUtils redisUtils;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(QuartzGetTokenJob.class);
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		String appid = "wxa1d0a0b03df30484";
		String secret = "e47c0012e826c52d73bb278a20c1dd97";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		System.out.println("获取AcssToken开始");
		String json = HttpClientUtill.sendGET(url);
		String id = "AssToken";
		if(null!=json) {
			JSONObject jsonText = JSON.parseObject(json);
			String tokenText =(String) jsonText.get("access_token");
			Integer date = (Integer) jsonText.get("expires_in");
			System.out.println("获取的token"+tokenText);
			System.out.println("获取的date"+date);
			String str = null;
			if(null != tokenText) {
				Token token = new Token();
				token.setId("1");
				token.setToKen(tokenText);
				token.setUpdate(DateUtill.getStringDate());
				service.setToken(token);
				str = tokenText;
				// 数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
				redisUtils.set(id, str, 120L, TimeUnit.MINUTES);
				logger.info("数据插入缓存" + str);
				logger.info("获取AcssToken成功");
			}else {
				logger.info("获取AcssToken失败");
			}		
		}			
	}

}
