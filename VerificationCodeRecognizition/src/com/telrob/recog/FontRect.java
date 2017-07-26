package com.telrob.recog;

/**
 * 
 * @author 张瑞志
 *
 * 创建时间:2017年7月26日 下午5:30:15
 *	分割后的文字在图片中的位置信息
 */
public class FontRect {
	public int left=0;
	public int right=0;
	public int top=0;
	public int bottom=0;
	public FontRect(){}
	@Override
	public String toString() {
		return "FontRect [left=" + left + ", right=" + right + ", top=" + top
				+ ", bottom=" + bottom + "]";
	};
}
