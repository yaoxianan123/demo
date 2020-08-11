package com.tp.wechat.conrroller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tp.wechat.entity.Token;
import com.tp.wechat.job.QuartzGetTokenJob;
import com.tp.wechat.service.TokenService;
import com.tp.wechat.utill.DateUtill;
import com.tp.wechat.utill.HttpClientUtill;

import ch.qos.logback.classic.Logger;

@Controller
public class TestController {


    @Autowired
    TokenService service;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(QuartzGetTokenJob.class);

    @RequestMapping("/hello1")
    public String test() {
        String appid = "wxa1d0a0b03df30484";
        String secret = "e47c0012e826c52d73bb278a20c1dd97";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        System.out.println("获取AcssToken开始");
        String json = HttpClientUtill.sendGET(url);
        if (null != json) {
            JSONObject jsonText = JSON.parseObject(json);
            String tokenText = (String) jsonText.get("access_token");
            Integer date = (Integer) jsonText.get("expires_in");
            System.out.println("获取的token" + tokenText);
            System.out.println("获取的date" + date);
            if (null != tokenText) {
                Token token = new Token();
                token.setId("1");
                token.setToKen(tokenText);
                token.setUpdate(DateUtill.getStringDate());
                service.setToken(token);
                logger.info("获取AcssToken成功");
            } else {
                logger.info("获取AcssToken失败");
            }
        }
        return "ok";
    }

    @RequestMapping("/test1")
    public String test2() {
        System.out.println("进入控制器");

        return "test";
    }

    @RequestMapping("/jspIndex")
    public String jspIndex() {
        System.out.println("进入jsp控制器！");
        return "jspIndex";
    }

    @RequestMapping("/result")
    public String result(@PathVariable("s") String s, @PathVariable("e") String e) {
        double end = 0;
        end = Double.valueOf(s) * Double.valueOf(e);
        return "js";
    }

}
