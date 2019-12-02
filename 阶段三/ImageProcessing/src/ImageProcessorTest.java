import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;


public class ImageProcessorTest {
	// 测试myRead函数
	@Test
	public void testMyRead() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/1.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/1.bmp");
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(image, 0, 0, width, height, null);
        // 测宽、高以及像素图
		assertEquals(goal.getWidth(null),image.getWidth(null));
		assertEquals(goal.getHeight(null),image.getHeight(null));
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}
	
	// 测试第一张图片蓝色通道图的宽高和像素值
	@Test
	public void test2() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/goal/1_blue_goal.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/1.bmp");
		ImplementImageProcessor processor = new ImplementImageProcessor(); 
		Image blue = processor.showChanelB(image);
		int width = blue.getWidth(null);
		int height = blue.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(blue, 0, 0, width, height, null);
        // 测宽、高以及像素图
        assertEquals(goal.getWidth(null),blue.getWidth(null));
		assertEquals(goal.getHeight(null),blue.getHeight(null));
		
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}
	
	// 测试第一张图片的灰度图片
	@Test
	public void test3() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/goal/1_gray_goal.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/1.bmp");
		ImplementImageProcessor processor = new ImplementImageProcessor(); 
		Image gray = processor.showGray(image);
		int width = gray.getWidth(null);
		int height = gray.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(gray, 0, 0, width, height, null);
        // 测宽、高以及像素图
        assertEquals(goal.getWidth(null),gray.getWidth(null));
		assertEquals(goal.getHeight(null),gray.getHeight(null));
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}

	// 测试第一张图片绿色通道图的宽高和像素值
	@Test
	public void test4() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/goal/1_green_goal.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/1.bmp");
		ImplementImageProcessor processor = new ImplementImageProcessor(); 
		Image green = processor.showChanelG(image);
		int width = green.getWidth(null);
		int height = green.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(green, 0, 0, width, height, null);
        // 测宽、高以及像素图
        assertEquals(goal.getWidth(null),green.getWidth(null));
		assertEquals(goal.getHeight(null),green.getHeight(null));
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}
	
	// 测试第一张图片红色通道图的宽高和像素值
	@Test
	public void test5() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/goal/1_red_goal.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/1.bmp");
		ImplementImageProcessor processor = new ImplementImageProcessor(); 
		Image red = processor.showChanelR(image);
		int width = red.getWidth(null);
		int height = red.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(red, 0, 0, width, height, null);
        // 测宽、高以及像素图
        assertEquals(goal.getWidth(null),red.getWidth(null));
		assertEquals(goal.getHeight(null),red.getHeight(null));
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}
	
	// 测试第二张图片蓝色通道图的宽高和像素值
	@Test
	public void test6() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/goal/2_blue_goal.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/2.bmp");
		ImplementImageProcessor processor = new ImplementImageProcessor(); 
		Image blue = processor.showChanelB(image);
		int width = blue.getWidth(null);
		int height = blue.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(blue, 0, 0, width, height, null);
        // 测宽、高以及像素图
        assertEquals(goal.getWidth(null),blue.getWidth(null));
		assertEquals(goal.getHeight(null),blue.getHeight(null));
		
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}
	
	// 第二张图片的灰度图片
	@Test
	public void test7() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/goal/2_gray_goal.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/2.bmp");
		ImplementImageProcessor processor = new ImplementImageProcessor(); 
		Image gray = processor.showGray(image);
		int width = gray.getWidth(null);
		int height = gray.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(gray, 0, 0, width, height, null);
        // 测宽、高以及像素图
        assertEquals(goal.getWidth(null),gray.getWidth(null));
		assertEquals(goal.getHeight(null),gray.getHeight(null));
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}
	
	// 测试第二张图片绿色通道图的宽高和像素值
	@Test
	public void test8() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/goal/2_green_goal.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/2.bmp");
		ImplementImageProcessor processor = new ImplementImageProcessor(); 
		Image green = processor.showChanelG(image);
		int width = green.getWidth(null);
		int height = green.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(green, 0, 0, width, height, null);
        // 测宽、高以及像素图
        assertEquals(goal.getWidth(null),green.getWidth(null));
		assertEquals(goal.getHeight(null),green.getHeight(null));
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}

	// 测试第二张图片红色通道图的宽高和像素值
	@Test
	public void test9() throws IOException {
		FileInputStream in = new FileInputStream("bmptest/goal/2_red_goal.bmp");
		BufferedImage goal = ImageIO.read(in);
		
		ImplementImageIO imageioer = new ImplementImageIO();
		Image image= imageioer.myRead("bmptest/2.bmp");
		ImplementImageProcessor processor = new ImplementImageProcessor(); 
		Image red = processor.showChanelR(image);
		int width = red.getWidth(null);
		int height = red.getHeight(null);
		BufferedImage test = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        test.getGraphics().drawImage(red, 0, 0, width, height, null);
        // 测宽、高以及像素图
        assertEquals(goal.getWidth(null),red.getWidth(null));
		assertEquals(goal.getHeight(null),red.getHeight(null));
		for (int i = 0; i < goal.getWidth(null); i++) {
			for (int j = 0; j < goal.getHeight(null); j++) {
				assertEquals(goal.getRGB(i, j), test.getRGB(i, j));
			}
		}
	}
}
