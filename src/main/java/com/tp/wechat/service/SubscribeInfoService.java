package com.tp.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.wechat.entity.OpenIdInfo;
import com.tp.wechat.entity.SubscribeInfo;
import com.tp.wechat.entity.Token;
import com.tp.wechat.mapper.OpenIdInfoMapper;
import com.tp.wechat.mapper.SubscribeInfoMapper;
import com.tp.wechat.mapper.TokenMapper;


@Service
public class SubscribeInfoService {


    @Autowired
	SubscribeInfoMapper subscribeInfoMapper;
	
	public int setSubscribe(SubscribeInfo subscribeInfo) {
		return subscribeInfoMapper.setSubscribe(subscribeInfo);
	}
	
	@Transactional
	public  void addSubscribeList(List<SubscribeInfo> list) {
		subscribeInfoMapper.addSubscribeList(list);
	}
}
