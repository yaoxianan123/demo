package com.tp.wechat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp.wechat.entity.OpenIdInfo;
import com.tp.wechat.mapper.OpenIdInfoMapper;

@Service
public class OpenIdInfoService {


    @Autowired
	OpenIdInfoMapper openIdInfoMapper;
	
	public OpenIdInfo getOpenId(String id) {
		return openIdInfoMapper.getOpenId(id);
	}
	
	public int setOpenId(OpenIdInfo openIdInfo) {
		return openIdInfoMapper.setOpenId(openIdInfo);
		
	}
	
	public List<String> getOpenIdList(Map<String,Integer> map){
		return openIdInfoMapper.getOpenIdList(map);
	}
	public int updateStatus(OpenIdInfo openIdInfo) {
		return openIdInfoMapper.updateStatus(openIdInfo);
	}
	@Transactional
	public void updateStatusList(List<OpenIdInfo> list) {
		openIdInfoMapper.updateStatusList(list);
	}

	public int getCount(){
		return openIdInfoMapper.getCount();
	}
	
}
