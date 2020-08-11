package com.tp.wechat.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tp.wechat.entity.OpenIdInfo;


@Repository
public interface OpenIdInfoMapper {
	OpenIdInfo getOpenId(String id);
	int setOpenId(OpenIdInfo openIdInfo);
	List<String> getOpenIdList(Map<String,Integer> map);
	int updateStatus(OpenIdInfo openIdInfo);
	void updateStatusList(List<OpenIdInfo> list);
	int getCount();
}
