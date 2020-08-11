package com.tp.wechat.Thread;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tp.wechat.conrroller.SubscribeInfoController;
import com.tp.wechat.entity.OpenIdInfo;
import com.tp.wechat.entity.SubscribeInfo;
import com.tp.wechat.service.OpenIdInfoService;
import com.tp.wechat.service.SubscribeInfoService;
import com.tp.wechat.service.TokenService;
import com.tp.wechat.utill.DateUtill;
import com.tp.wechat.utill.HttpClientUtill;
import com.tp.wechat.utill.RedisUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UpdateByThread implements Runnable {

    Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(UpdateByThread.class);

    private OpenIdInfoService service;
    @Autowired
    TokenService tokenService;
    @Autowired
    RedisUtils redisUtils;
    private SubscribeInfoService SubscribeInfoService;

    private static volatile Integer start = 0;

    private static Lock lock = new ReentrantLock();

    public UpdateByThread(/*int start,*/OpenIdInfoService service,SubscribeInfoService SubscribeInfoService){
       // this.start = start;
        this.service = service;
        this.SubscribeInfoService = SubscribeInfoService;
    }
    @Override
    public void run() {
        log.info("线程启动:===============================>>"+Thread.currentThread().getName());
        String value = "";
        log.info("进入关注状态更新控制器");
       // String token = tokenService.getToken();
        //请求腾讯微信公众号用户信息接口
      //  String infoUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + token;
        String str = null;
        int end = 100;
        Integer index = null;
       // int count = service.getCount();
        log.info("start：============================》"+start);
        List<String> list = null;
        do {
            Map<String, Integer> mapParm = new HashMap<String, Integer>();
            lock.lock();
            try{
                List<OpenIdInfo> list3 = new ArrayList<OpenIdInfo>();
                System.out.println(Thread.currentThread().getName()+"获得锁");
               // System.out.println("参数=============》》"+Thread.currentThread().getName()+" >>>>>>"+0);
                mapParm.put("start", 0);
               // System.out.println("start====="+Thread.currentThread().getName()+" >>>>>>"+0);
                mapParm.put("end", end);
                list = service.getOpenIdList(mapParm);
                index = list.size();
                for (int i = 0; i < list.size(); i++) {
                    String date = DateUtill.getStringDate();
                    Map<String, String> map = new HashMap<String, String>();
                    OpenIdInfo openIdInfo = new OpenIdInfo();
                    openIdInfo.setOpenid(list.get(i).trim());
                    openIdInfo.setStatus("4");
                    openIdInfo.setCreat_date(date);
                    list3.add(openIdInfo);
                    if(i == 100){
                        System.out.println("本批次最后一个ID======"+list.get(i).trim());
                    }
                    start++;
                }
                service.updateStatusList(list3);
                list3.clear();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"解锁"+"index"+start);
                lock.unlock();
            }
            if(index>0) {
                Map<String, List<Map<String, String>>> strMap = new HashMap<String, List<Map<String, String>>>();
                List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("openid", list.get(i).trim());
                    map.put("lang", "zh_CN");
                    listMap.add(map);
                  //  log.info(i + ": 数据 ： " + list.get(i));
                }
                strMap.put("user_list", listMap);
                String munelist = JSONArray.toJSONString(strMap);
               // log.info("请求报文："+munelist);
                //str = HttpClientUtill.sendPost(infoUrl, munelist);
                //JSONObject jsonInfo = JSONObject.parseObject(str);
               // String flag = jsonInfo.getString("errcode");
             /*   if(null != flag && !"".equals(flag)) {
                    value = "AssToken失效";
                    break;
                }*/
                //JSONArray jsonArray = jsonInfo.getJSONArray("user_info_list");
                List<SubscribeInfo> list2 = new ArrayList<SubscribeInfo>();
                List<OpenIdInfo> list3 = new ArrayList<OpenIdInfo>();
                for (int i = 0; i < list.size(); i++) {
                    JSONObject js = null;
                    /*js = (JSONObject) jsonArray.get(i);
                    String openid = (String) js.get("openid");
                    String date = DateUtill.getStringDate();*/
                    SubscribeInfo subscribeInfo = new SubscribeInfo();
                    //subscribeInfo.setOpenId(openid);
                    //subscribeInfo.setDelFlag(String.valueOf((Integer) js.get("subscribe")));
                   //subscribeInfo.setCreatDate(date);
                    subscribeInfo.setDelFlag("3");
                    list2.add(subscribeInfo);
                  //  OpenIdInfo openIdInfo = new OpenIdInfo();
                   // openIdInfo.setOpenid(openid);
                    //openIdInfo.setStatus("3");
                   // openIdInfo.setCreat_date(date);
                    //list3.add(openIdInfo);
                }
                SubscribeInfoService.addSubscribeList(list2);
               // service.updateStatusList(list3);
                list2.clear();
               // list3.clear();
            }
            value = "更新结束";
        } while (index > 0);
    }
}
