package com.tp.wechat.conrroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.tp.wechat.Thread.UpdateByThread;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tp.wechat.entity.OpenIdInfo;
import com.tp.wechat.entity.SubscribeInfo;
import com.tp.wechat.service.OpenIdInfoService;
import com.tp.wechat.service.SubscribeInfoService;
import com.tp.wechat.service.TokenService;
import com.tp.wechat.utill.DateUtill;
import com.tp.wechat.utill.HttpClientUtill;
import com.tp.wechat.utill.RedisUtils;

import ch.qos.logback.classic.Logger;

@RestController
public class SubscribeInfoController {

    Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(SubscribeInfoController.class);

    @Autowired
    OpenIdInfoService service;
    @Autowired
    TokenService tokenService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    SubscribeInfoService SubscribeInfoService;

    @RequestMapping("testSub")
    public String GetUser() {
        log.info("进入控制器");
        SubscribeInfo subscribeInfo = new SubscribeInfo();
        subscribeInfo.setOpenId("Test");
        subscribeInfo.setDelFlag("1");
        subscribeInfo.setCreatDate(DateUtill.getStringDate());
        int i = SubscribeInfoService.setSubscribe(subscribeInfo);
        if (i > 0) {
            log.info("新增数据成功!");
        }
        return "ok";
    }

    @RequestMapping("updateSub")
    public String updateSubscribeInfo() {
        String value = "";
        log.info("进入关注状态更新控制器");
        String token = tokenService.getToken();
        //请求腾讯微信公众号用户信息接口
        String infoUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + token;
        String str = null;
        int start = 0;
        int end = 100;
        Integer index = null;
        int count = service.getCount();

        do {
            Map<String, Integer> mapParm = new HashMap<String, Integer>();
            mapParm.put("start", start);
            mapParm.put("end", end);
            List<String> list = service.getOpenIdList(mapParm);
            index = list.size();

            if (index > 0) {
                Map<String, List<Map<String, String>>> strMap = new HashMap<String, List<Map<String, String>>>();
                List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("openid", list.get(i).trim());
                    map.put("lang", "zh_CN");
                    listMap.add(map);
                    log.info(i + ": 数据 ： " + list.get(i));
                }
                strMap.put("user_list", listMap);
                String munelist = JSONArray.toJSONString(strMap);
                str = HttpClientUtill.sendPost(infoUrl, munelist);
                JSONObject jsonInfo = JSONObject.parseObject(str);
                String flag = jsonInfo.getString("errcode");
                if (null != flag && !"".equals(flag)) {
                    value = "AssToken失效";
                    break;
                }
                JSONArray jsonArray = jsonInfo.getJSONArray("user_info_list");
                List<SubscribeInfo> list2 = new ArrayList<SubscribeInfo>();
                List<OpenIdInfo> list3 = new ArrayList<OpenIdInfo>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject js = null;
                    js = (JSONObject) jsonArray.get(i);
                    String openid = (String) js.get("openid");
                    String date = DateUtill.getStringDate();
                    SubscribeInfo subscribeInfo = new SubscribeInfo();
                    subscribeInfo.setOpenId(openid);
                    subscribeInfo.setDelFlag(String.valueOf((Integer) js.get("subscribe")));
                    subscribeInfo.setCreatDate(date);
                    list2.add(subscribeInfo);
                    OpenIdInfo openIdInfo = new OpenIdInfo();
                    openIdInfo.setOpenid(openid);
                    openIdInfo.setStatus("1");
                    openIdInfo.setCreat_date(date);
                    list3.add(openIdInfo);
                }
                SubscribeInfoService.addSubscribeList(list2);
                service.updateStatusList(list3);
                list2.clear();
                list3.clear();
            }
            value = "更新结束";
        } while (index > 0);
        return value;
    }

    @RequestMapping("TestRedis2")
    public String TestRedis() {
        String token = tokenService.getToken();
        long start = System.currentTimeMillis();
        System.out.println("进入测试redis控制器");
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + token + "&next_openid=";
        String infoUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + token;
        // 查询缓存中是否存在
        boolean hasKey = redisUtils.exists("open");
        String id = "open";
        String str = "";
        // String json =
        // "{\"total\":23000,\"count\":10000,\"data\":{\"openid\":[\"OPENID1\",\"OPENID2\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\",\"OPENID10000\"]},\"next_openid\":\"OPENID10000\"}";
        String json = HttpClientUtill.sendGET(url);
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (hasKey) {
            // 获取缓存
            Object object = redisUtils.get(id);
            log.info("从缓存获取的数据" + object);
            str = object.toString();
            JSONObject jsonText = JSONObject.parseObject(str);
            List<String> sz = (List<String>) jsonText.get("openid");
            Map<String, List<Map<String, String>>> strMap = new HashMap<String, List<Map<String, String>>>();
            List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
            for (int i = 0; i < 100; i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("openid", sz.get(i));
                map.put("lang", "zh_CN");
                listMap.add(map);
                System.out.println(i + ": 数据 ： " + sz.get(i));
            }
            strMap.put("user_list", listMap);
            String munelist = JSONArray.toJSONString(strMap);
            log.info("post请求参数" + munelist);
            str = HttpClientUtill.sendPost(infoUrl, munelist);
            JSONObject jsonInfo = JSONObject.parseObject(str);
            JSONArray jsonArray = jsonInfo.getJSONArray("user_info_list");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject js = null;
                js = (JSONObject) jsonArray.get(i);
                OpenIdInfo openIdInfo = new OpenIdInfo();
                openIdInfo.setOpenid((String) js.get("openid"));
                openIdInfo.setStatus(String.valueOf((Integer) js.get("subscribe")));
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
        log.info("程序运行时间：" + (end - start) + "hs");
        return str;
    }

    @RequestMapping("updateSubList")
    public String updateUser() {
        log.info("进入多线程更新数据控制器");
        int count = service.getCount();
        int x = 0;
        //int star = count / 5;
         int star = 0;

        try {
            for (int i = 0; i < 5; i++) {
       /*         if (i == 0) {
                    new Thread(new UpdateByThread(x,service,SubscribeInfoService), "scoreAPI-" + i).start();
                }
               new Thread(new UpdateByThread(star,service,SubscribeInfoService), "scoreAPI-" + i).start();
                star += star;*/
        new Thread(new UpdateByThread(service,SubscribeInfoService),"scoreApi-"+i).start();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return "ok";

    }

    @RequestMapping("main")
    public String test() {
        Map<String, Integer> mapParm = new HashMap<String, Integer>();
        mapParm.put("start", 0);
        mapParm.put("end", 100);
        List<String> list = service.getOpenIdList(mapParm);
        return "ok";
    }
}
