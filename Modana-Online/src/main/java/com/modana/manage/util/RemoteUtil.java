package com.modana.manage.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * 远程url工具类.
 * 
 * @author Maqiang34
 */
public class RemoteUtil {
	
	/**
	 * 
	 * @return
	 */
	public static String getServerIP(){
		try {
			InetAddress addr = InetAddress.getLocalHost();
			 return addr.getHostAddress();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	/**
	 * 发送Http协议 通过post传参数到接口并返回数据
	 * 
	 */
	public static String httpPost(String urlStr, Map<String, Object> params) {
		URL connect;
		StringBuffer data = new StringBuffer();
		try {
			connect = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) connect.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);// post不能使用缓存
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			OutputStreamWriter paramout = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			String paramsStr = ""; // 拼接Post 请求的参数
			for (String param : params.keySet()) {
				paramsStr += "&" + param + "=" + params.get(param);
			}
			if (!paramsStr.isEmpty()) {
				paramsStr = paramsStr.substring(1);
			}
			paramout.write(paramsStr);
			paramout.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				data.append(line);
			}
			paramout.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data.toString();
	}

}