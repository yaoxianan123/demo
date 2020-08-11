package com.tp.wechat.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tp.wechat.entity.Token;
import com.tp.wechat.service.TokenService;
import com.tp.wechat.utill.DateUtill;
import com.tp.wechat.utill.HttpClientUtill;

public class QuartzGetOpenIdJob extends QuartzJobBean{


	@Autowired
	TokenService service;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		String appid = "wxa1d0a0b03df30484";
		String secret = "e47c0012e826c52d73bb278a20c1dd97";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		System.out.println("获取AcssToken开始");
		String json = HttpClientUtill.sendGET(url);
		if(null!=json) {
			JSONObject jsonText = JSON.parseObject(json);
			String tokenText =(String) jsonText.get("access_token");
			Integer date = (Integer) jsonText.get("expires_in");
			System.out.println("获取的token"+tokenText);
			System.out.println("获取的date"+date);
			Token token = new Token();
			token.setId("1");
			token.setToKen(tokenText);
			token.setUpdate(DateUtill.getStringDate());
			service.setToken(token);
			System.out.println("获取AcssToken结束");
		}
	
		
	}

}
