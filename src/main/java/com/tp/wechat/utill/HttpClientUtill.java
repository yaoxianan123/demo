package com.tp.wechat.utill;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import ch.qos.logback.classic.Logger;

public class HttpClientUtill {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(HttpClientUtill.class);
	
	/*发送post请求
	 * @param url  请求的url
	 * 
	 * @param menuString
	 *       post请求的参数 json格式
	 * @return
	 *       响应的内容
	 * 
	 * */
	public static String sendPost(String url,String menuString) {
		logger.info("httpCliendUtill.sendPost 请求的url："+url);
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter("http.protocol.content-charset","UTF-8");
		String responseBody = "";
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
			//post参数
			httpPost.setHeader(HttpHeaders.CONNECTION,"close");
			StringEntity reqEntity = new StringEntity(menuString,"utf-8");
			httpPost.setEntity(reqEntity);
			//响应对象
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpClient.execute(httpPost,responseHandler);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("请求该url失败==========="+url);
		}finally {
			try {
				if(null != httpPost) {
					httpPost.releaseConnection();
				}
				if(null != httpClient) {
					httpClient.getConnectionManager().shutdown();
				}
			} catch (Exception e2) {
				System.out.println("sendWechatPost is error"+e2.getMessage());
			}
		}
		
		System.out.println("HttpclientUtil.sendPost 响应内容" + responseBody);
		
		
		return responseBody;
	}
	/*发送get请求
	 * @param url  请求的url
	 *       
	 * @return
	 *       响应的内容
	 * 
	 * */
	public static String sendGET(String url) {
		logger.info("httpCliendUtill.sendget 请求的url："+url);
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter("http.protocol.content-charset","UTF-8");
		System.setProperty("https.protocols", "TLSv1");
		String responseBody = "";
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(url);
			
	
			//响应对象
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpClient.execute(httpGet,responseHandler);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("请求该url失败==========="+url);
		}	
		logger.info("HttpclientUtil.sendPost 响应内容" + responseBody);
		return responseBody;
	}
	
	public static void main(String[] args) {
/*		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxa1d0a0b03df30484&secret=e47c0012e826c52d73bb278a20c1dd97";
		String value = HttpClientUtill.sendGET(url);
		if(value!=null) {
			System.out.println("返回的内容"+value);
		}*/
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=pEHkx5dLoOTUOKFj2uVuzjBkEKDjADAPVO                                                                                                                                                                                                                                                                                          ";
		String value = HttpClientUtill.sendGET(url);
		if(value!=null) {
			System.out.println("返回的内容"+value);
		}
		
	}
	
	
	
}
