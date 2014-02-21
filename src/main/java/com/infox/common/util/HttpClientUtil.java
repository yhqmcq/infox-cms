package com.infox.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {

	public static void main(String[] args) throws Exception {
		JSONObject json = JSON.parseObject(HttpClientUtil.getHttpString("http://ip.taobao.com/service/getIpInfo.php?ip=58.67.137.193"));
		System.out.println(json);
		JSONObject data = JSON.parseObject(json.get("data").toString()) ;
		System.out.println(data.get("area"));
	}

	public static String getHttpString(String url) {
		String str = null;
		try {
			// 创建HttpClient实例
			HttpClient httpclient = HttpClients.createDefault();
			// 创建Get方法实例
			HttpGet httpgets = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpgets);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				str = convertStreamToString(instreams);
				httpgets.abort();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
