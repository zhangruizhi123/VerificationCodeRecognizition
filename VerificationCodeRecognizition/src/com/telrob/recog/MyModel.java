package com.telrob.recog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 
 * @author 张瑞志
 * 
 *         创建时间:2017年7月26日 下午5:43:29 
 *         根据已知字体创建字体模板
 */
public class MyModel {
	private static String str = " 0 1 2 3 4 5 6 7 8 9 a b c d e f g h i j k l m n o p q r s t u v w x y z A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";

	public static void main(String[] args) throws Exception{

		// 是一位的并且要是bmp的
		BufferedImage img = new BufferedImage(1000, 30,
				BufferedImage.TYPE_BYTE_BINARY);
		Graphics g = img.getGraphics();
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, 1000, 30);

		g.setColor(new Color(0, 0, 0));
		Font font = new Font(Font.DIALOG, Font.ITALIC, 18);
		g.setFont(font);
		g.drawString(str, 0, 20);
		//剪切模板
		List<BufferedImage> listMod=CutFont.cutImages(img);
		BufferedImage temp;
		for(int i=0;i<listMod.size();i++){
			temp=listMod.get(i);
			//对模板进行缩放
			temp=ImageUtils.scaleImg(temp, 16, 16);
			//保存模板图像
			ImageIO.write(temp, "bmp", new File("mod/m_"+i+".bmp"));
		}
	}

}
