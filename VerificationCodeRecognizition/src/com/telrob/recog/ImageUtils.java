package com.telrob.recog;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 
 * @author 张瑞志
 * 
 *         创建时间:2017年7月26日 下午5:32:52 图片处理的工具类
 */
public class ImageUtils {
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			BufferedImage bfO = ImageIO
					.read(new File("download/" + i + ".jpg"));
			BufferedImage bf1 = convertBin(bfO, 150);
			ImageIO.write(bf1, "bmp", new File("recg/" + i + "_1.bmp"));
		}
		System.out.println("处理完成");

	}

	/**
	 * 将图片转换成灰度图片
	 * 
	 * @param bfO
	 * @return
	 */
	public static BufferedImage convertGrayLevel(BufferedImage bfO) {
		BufferedImage bf = new BufferedImage(bfO.getWidth(), bfO.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		for (int y = 0; y < bfO.getHeight(); y++) {
			for (int x = 0; x < bfO.getWidth(); x++) {
				int rgb = bfO.getRGB(x, y);
				Color color = new Color(rgb);
				int d = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				Color c = new Color(d, d, d);
				bf.setRGB(x, y, c.getRGB());
			}
		}
		return bf;
	}

	/**
	 * 将图片转换成二值化位图
	 * 
	 * @param bfO 原图片对象
	 * @param limit 二值化阈值
	 * @return
	 */
	public static BufferedImage convertBin(BufferedImage bfO, int limit) {
		BufferedImage bf = new BufferedImage(bfO.getWidth(), bfO.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);
		for (int y = 0; y < bfO.getHeight(); y++) {
			for (int x = 0; x < bfO.getWidth(); x++) {
				int rgb = bfO.getRGB(x, y);
				Color color = new Color(rgb);
				int d = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				if (d > limit) {
					bf.setRGB(x, y, 0xFFFFFF);
				} else {
					bf.setRGB(x, y, 0);
				}

			}
		}
		return bf;
	}

	/**
	 * 比较图像的相似度(输入图片要求16*16的二值化图片)
	 * 
	 * @param src
	 *            模板图像
	 * @param dst
	 *            要比较的图像
	 * @return 返回图片的相似度
	 */
	public static double compareImg(BufferedImage src, BufferedImage dst) {
		int width1 = src.getWidth();
		int height1 = src.getHeight();
		int width2 = dst.getHeight();
		int height2 = dst.getHeight();
		int totals = 0;// 黑点的总个数
		int same = 0;// 相同点的个数
		int maxW = width1 > width2 ? width1 : width2;
		int maxH = height1 > height2 ? height1 : height2;
		// 黑色
		Color c = new Color(0, 0, 0);
		// 白色
		Color b = new Color(255, 255, 255);
		for (int x = 0; x < maxW; x++) {
			for (int y = 0; y < maxH; y++) {
				int c1 = b.getRGB(), c2 = b.getRGB();
				if (x < width1 && y < height1) {
					c1 = src.getRGB(x, y);
					if (c1 == c.getRGB()) {
						totals++;
					}
				}
				if (x < width2 && y < height2) {
					c2 = dst.getRGB(x, y);
					if (c2 == c.getRGB()) {
						totals++;
					}
				}
				if (c1 != b.getRGB() && c1 == c2) {
					same++;
				}
			}
		}
		return same * 1.0 / totals;
	}

	/**
	 * 对图片进行等比缩放
	 * 
	 * @param bim 原图片
	 * @param sc 缩放比例
	 * @return
	 */
	public static BufferedImage scaleImg(BufferedImage bim, double sc) {
		int width = (int) (bim.getWidth() * sc);
		int height = (int) (bim.getHeight() * sc);
		BufferedImage bm = new BufferedImage(width, height, bim.getType());
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int tempX = (int) (x / sc);
				int tempY = (int) (y / sc);
				int rgb = bim.getRGB(tempX, tempY);
				bm.setRGB(x, y, rgb);
			}
		}
		return bm;
	}

	/**
	 * 对图片进行缩放
	 * 
	 * @param bim 原图片
	 * @param scX x轴缩放比例
	 * @param scY y轴缩放比例
	 * @return
	 */
	public static BufferedImage scaleImg(BufferedImage bim, double scX,
			double scY) {
		int width = (int) (bim.getWidth() * scX);
		int height = (int) (bim.getHeight() * scY);
		BufferedImage bm = new BufferedImage(width, height, bim.getType());
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int tempX = (int) (x / scX);
				int tempY = (int) (y / scY);
				int rgb = bim.getRGB(tempX, tempY);
				bm.setRGB(x, y, rgb);
			}
		}
		return bm;
	}

	/**
	 * 缩放到指定的大小
	 * 
	 * @param bim 原图片
	 * @param scX 宽度
	 * @param scY 高度
	 * @return
	 */
	public static BufferedImage scaleImg(BufferedImage bim, int scX, int scY) {
		double xs = 1.0 * scX / bim.getWidth();
		double ys = 1.0 * scY / bim.getHeight();
		return scaleImg(bim, xs, ys);
	}
}
