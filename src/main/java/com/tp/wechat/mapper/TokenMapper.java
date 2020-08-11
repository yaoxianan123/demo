package com.tp.wechat.mapper;

import org.springframework.stereotype.Repository;

import com.tp.wechat.entity.OpenIdInfo;
import com.tp.wechat.entity.Token;




@Repository
public interface TokenMapper {
	int setToken(Token token);
	String getToken();
}
