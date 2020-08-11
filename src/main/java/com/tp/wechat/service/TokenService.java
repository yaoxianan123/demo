package com.tp.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp.wechat.entity.OpenIdInfo;
import com.tp.wechat.entity.Token;
import com.tp.wechat.mapper.OpenIdInfoMapper;
import com.tp.wechat.mapper.TokenMapper;


@Service
public class TokenService {


    @Autowired
	TokenMapper tokenMapper;
	
	public int setToken(Token token) {
		return tokenMapper.setToken(token);
	}
	public String getToken() {
		return tokenMapper.getToken();
	};
}
