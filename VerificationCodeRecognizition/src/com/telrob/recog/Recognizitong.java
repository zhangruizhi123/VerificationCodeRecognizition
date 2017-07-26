package com.telrob.recog;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 
 * @author 张瑞志
 * 
 *         创建时间:2017年7月26日 下午6:02:56 图像识别类
 */
public class Recognizitong {
	// 要加载的模板
	private BufferedImage mods[] = null;
	// 要识别模板对应字符串
	private String recogStr = null;

	/**
	 * 加载默认的识别模板
	 * 
	 * @throws Exception
	 */
	public Recognizitong() throws Exception {
		recogStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		mods = new BufferedImage[62];
		for (int i = 0; i < recogStr.length(); i++) {
			mods[i] = ImageIO.read(new File("mod/m_" + i + ".bmp"));
		}
	}

	/**
	 * 加载用户自定义模板
	 * 
	 * @param mods
	 *            模板图片,必须是经过处理的二值化的16*16的位图
	 * @param str
	 */
	public Recognizitong(BufferedImage mods[], String str) {
		recogStr = str;
		this.mods = mods;
	}

	/**
	 * 开始识别图片
	 * 
	 * @param img
	 *            要识别的图片文件
	 * @return
	 * @throws Exception
	 */
	public String startRecognize(File img) throws Exception {
		String result = "";
		// 获取图片对象
		BufferedImage recog = ImageIO.read(img);
		// 图片进行二值化处理
		recog = ImageUtils.convertBin(recog, 150);
		List<BufferedImage> listRecog = CutFont.cutImages(recog);
		for (BufferedImage temp : listRecog) {
			// 对图片进行缩放（16*16）
			temp = ImageUtils.scaleImg(temp, 16, 16);
			int index = recog(mods, temp);
			result += getStr(recogStr, index);
		}
		return result;
	}

	/**
	 * 从模板中识别图像
	 * 
	 * @param imgs
	 *            识别模板
	 * @param recog
	 *            要识别的图像
	 * @return 匹配最相似的模板的索引
	 */
	private int recog(BufferedImage[] imgs, BufferedImage recog) {
		double result = 0, temp = 0;
		int index = 0;
		for (int i = 0; i < imgs.length; i++) {
			temp = ImageUtils.compareImg(imgs[i], recog);
			if (temp > result) {
				result = temp;
				index = i;
			}
		}
		return index;
	}

	private String getStr(String str, int index) {
		return str.substring(index, index + 1);
	}
}
