package com.tp.wechat.conrroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tp.wechat.entity.OpenIdInfo;
import com.tp.wechat.entity.Token;
import com.tp.wechat.service.OpenIdInfoService;
import com.tp.wechat.service.TokenService;
import com.tp.wechat.utill.DateUtill;
import com.tp.wechat.utill.HttpClientUtill;
import com.tp.wechat.utill.RedisUtils;

import ch.qos.logback.classic.Logger;

@RestController
public class OpenIdInfoController {

	Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(OpenIdInfoController.class);
	
	@Autowired
	OpenIdInfoService service;
	@Autowired
	TokenService tokenService;
	@Autowired
	RedisUtils redisUtils;

	@RequestMapping("getOpenId/{id}")
	public String GetUser( Integer id) {
		System.out.println("进入控制器"+id);

		return "ok";
	}

	@RequestMapping("SetToken")
	public String SetToken() {
		System.out.println("进入控制器");
		Token token = new Token();
		token.setId("1");
		token.setToKen("TestTOken");
		token.setUpdate(DateUtill.getStringDate());
		int falg = tokenService.setToken(token);
		if (falg != 0) {
			System.out.println("修改token成功");
		}
		return "ok";
	}

	@RequestMapping("TestRedis")
	public String TestRedis() {
		String token = tokenService.getToken();
		long start = System.currentTimeMillis();
		System.out.println("进入测试redis控制器");
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+token+"&next_openid=";
		String infoUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+token;
		// 查询缓存中是否存在
		boolean hasKey = redisUtils.exists("open");
		String id = "open";
		String str = "";
	//	String json = "{\"total\":23000,\"count\":10000,\"data\":{\"openid\":[\"OPENID1\",\"OPENID2\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\"]},\"next_openid\":\"OPENID10000\"}";
		String json = HttpClientUtill.sendGET(url);
		JSONObject jsonObject = JSONObject.parseObject(json);
		if (hasKey) {
			// 获取缓存
			Object object = redisUtils.get(id);
			log.info("从缓存获取的数据" + object);
			str = object.toString();
			JSONObject jsonText = JSONObject.parseObject(str);
			 List<String> sz =  (List<String>) jsonText.get("openid");
			 Map<String,List<Map<String,String>>> strMap = new HashMap<String, List<Map<String,String>>>();
			 List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
			for (int i = 0; i < 100; i++) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("openid", sz.get(i));
				map.put("lang","zh_CN");
				listMap.add(map);
				System.out.println(i+": 数据 ： "+sz.get(i));
			}
			strMap.put("user_list",listMap);
			String munelist = JSONArray.toJSONString(strMap);
			log.info("post请求参数"+munelist);
			str =HttpClientUtill.sendPost(infoUrl,munelist);
			JSONObject jsonInfo = JSONObject.parseObject(str);
			JSONArray jsonArray = jsonInfo.getJSONArray("user_info_list");
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject js = null;
				js =(JSONObject)jsonArray.get(i);
				OpenIdInfo openIdInfo = new OpenIdInfo();
				openIdInfo.setOpenid((String)js.get("openid"));
				openIdInfo.setStatus(String.valueOf((Integer)js.get("subscribe")));
				openIdInfo.setCreat_date(DateUtill.getStringDate());
				service.setOpenId(openIdInfo);
			}
			
		} else {
			// 从数据库中获取信息
			log.info("从数据库中获取数据");
			str = jsonObject.getString("data");
			// 数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
			redisUtils.set(id, str, 10L, TimeUnit.MINUTES);
			log.info("数据插入缓存" + str);
		}
		long end = System.currentTimeMillis();
		log.info("程序运行时间："+(end-start)+"hs");
		return str;

	}

}