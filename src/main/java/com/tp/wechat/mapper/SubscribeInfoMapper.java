package com.tp.wechat.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tp.wechat.entity.OpenIdInfo;
import com.tp.wechat.entity.SubscribeInfo;
import com.tp.wechat.entity.Token;




@Repository
public interface SubscribeInfoMapper {
	int setSubscribe(SubscribeInfo subscribeInfo);
	
	void addSubscribeList(List<SubscribeInfo> list);
	
}
