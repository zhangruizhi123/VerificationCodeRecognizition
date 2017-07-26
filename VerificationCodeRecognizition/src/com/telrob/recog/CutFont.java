package com.telrob.recog;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 张瑞志
 * 
 *         创建时间:2017年7月26日 下午5:39:14 对字体进行剪切
 * 
 */
public class CutFont {
	
	
	/**
	 * 对图片进行分割(其他操作不做任何处理)
	 * @param cutImg 经过二值化处理的图片
	 * @return
	 */
	public static synchronized List<BufferedImage> cutImages(BufferedImage cutImg){
		List<FontRect> list=new ArrayList<FontRect>();
		List<BufferedImage> listImage=new ArrayList<BufferedImage>();
		BufferedImage tempImg=null;
		culLR(list,cutImg);
		culTB(list,cutImg);
		for(FontRect fr:list){
			tempImg=cutImage(fr,cutImg);
			listImage.add(tempImg);
		}
		return listImage;
	}
	/**
	 * 计算左右边界
	 * 
	 * @param img
	 * @return
	 */
	public static synchronized List<FontRect> culLR(List<FontRect> list, BufferedImage img) {
		boolean start = false;
		// 黑色
		Color c = new Color(0, 0, 0);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				// System.out.println(img.getRGB(i, j));
				if (start == false) {
					if (img.getRGB(i, j) == c.getRGB()) {
						start = true;
						FontRect f = new FontRect();
						f.left = i;
						list.add(f);
						break;
					}
				} else {
					if (img.getRGB(i, j) == c.getRGB()) {
						break;
					}
					if (j == img.getHeight() - 1) {
						start = false;
						list.get(list.size() - 1).right = i - 1;
					}
				}
			}
		}
		return list;
	}

	/**
	 * 计算上下边界,分为两步，上边界，下边界
	 * 
	 * @param img
	 * @return
	 */
	public static synchronized List<FontRect> culTB(List<FontRect> list, BufferedImage img) {
		boolean start = false;
		// 黑色
		Color c = new Color(0, 0, 0);
		for (int k = 0; k < list.size(); k++) {
			// 遍历字体
			FontRect fr = list.get(k);
			// 上边界扫描
			for (int i = 0; i < img.getHeight(); i++) {
				// y坐标
				for (int j = fr.left; j <= fr.right; j++) {
					// x坐标
					if (start == false) {
						if (img.getRGB(j, i) == c.getRGB()) {
							start = true;
							fr.top = i;
							break;
						}
					}

				}
				if (start) {
					start = false;
					break;
				}
			}
			// 从下边界扫描
			for (int i = img.getHeight() - 1; i >= 0; i--) {
				// y坐标
				for (int j = fr.left; j <= fr.right; j++) {
					// x坐标
					if (start == false) {
						if (img.getRGB(j, i) == c.getRGB()) {
							start = true;
							fr.bottom = i;
							break;
						}
					}

				}
				if (start) {
					start = false;
					break;
				}
			}

		}
		return list;
	}

	/**
	 * 切割图像
	 * 
	 * @param rect
	 * @param img
	 * @return
	 */
	public static synchronized BufferedImage cutImage(FontRect rect, BufferedImage img) {
		int width = rect.right - rect.left + 1;
		int height = rect.bottom - rect.top + 1;
		BufferedImage bI = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = img.getRGB(x + rect.left, y + rect.top);
				bI.setRGB(x, y, rgb);
			}
		}
		return bI;
	}
}
