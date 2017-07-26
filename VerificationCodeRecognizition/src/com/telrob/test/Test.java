package com.telrob.test;

import java.io.File;

import com.telrob.recog.Recognizitong;

/**
 * 
 * @author 张瑞志
 *
 * 创建时间:2017年7月26日 下午6:26:12
 * 对工具进行测试
 */
public class Test {
	public static void main(String[] args) {
		try {
			Recognizitong rec=new Recognizitong();
			for(int i=9;i<10;i++){
				String result=rec.startRecognize(new File("download/"+i+".jpg"));
				System.out.println(i+":"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
