package com.fast.demo.basic.util;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fast.demo.basic.vo.ResponseMsg;
import net.sf.ezmorph.object.DateMorpher;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * HttpSend工具類
 * 
 * @author C3901094
 * @date 2020年9月25日 下午1:38:38
 */
public class HttpSendUtil {

	public static String doGet(String url, Map<String, String> param, Map<String, String> head) {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			httpclient = HttpClients.createDefault();
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			if(!MySystemUtil.isAnyEmpty(head)) {
				for (String key : head.keySet()) {
					httpGet.addHeader(key, head.get(key));
				}				
			}
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doGet(String url, Map<String, String> param) {
		return doGet(url, param, null);
	}


	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			httpClient = HttpClients.createDefault();
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultString;
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}

	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			httpClient = HttpClients.createDefault();
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/**
	 * 接口測試
	 * @author C3901094
	 * @date 2020年9月26日 下午4:23:33
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		String result = doPostJson("http://10.244.168.180/dfm/api/dataprovider/gluedatatoort",
				"{\r\n" + 
				"  \"barcode\": [\r\n" + 
				"    \"FCT0000076355041\"\r\n" + 
				"  ],\r\n" + 
				"  \"smallPart\": [\r\n" + 
				"    \"Stand\"\r\n" + 
				"  ]\r\n" + 
				"}");
		//FCT0000075988486
		//FCT0000075220255
//		System.out.println(result);//打印POST請求返回值

//		odcList.forEach(System.out::println);
	}

}