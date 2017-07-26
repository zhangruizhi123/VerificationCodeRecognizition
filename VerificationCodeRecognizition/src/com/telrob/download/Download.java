package com.telrob.download;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author 张瑞志
 *
 * 创建时间:2017年7月26日 下午5:11:53
 *
 *	从网络中下载验证码图片
 */
public class Download {
	
	public static void main(String[] args) {
		String url = "http://www.udreamtech.com/UDCMS/verifyCode";
		for(int i=0;i<10;i++){
			InputStream in=getStream(url);
			write(in,"download/"+i+".jpg");
			System.out.print(". ");
		}
		System.out.println("下载完成");
	}
	
	/**
	 * 根据url获取图片流
	 * @param url
	 * @return
	 */
	public synchronized static InputStream getStream(String url) {
		InputStream in = null;
		try {
			URL u = new URL(url);
			HttpURLConnection connect = (HttpURLConnection) u.openConnection();
			in = connect.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * 把图片保存在本地
	 * @param in
	 * @param name
	 */
	public synchronized static void write(InputStream in, String name) {
		try {
			OutputStream out = new FileOutputStream(name);
			byte bb[] = new byte[1024 * 5];
			int len;
			while ((len = in.read(bb)) > 0) {
				out.write(bb, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
