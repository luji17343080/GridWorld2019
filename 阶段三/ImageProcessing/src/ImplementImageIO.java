import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

import imagereader.IImageIO;

/**
 * 读取与保存位图
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ImplementImageIO implements IImageIO{
	private static final int EIGHT = 8;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FF = 0xff;
	private static final int COLORCHANNAL = 255;
	// 将字节数组转换为Int
	public int bytesToInt(byte[] bytesArray) {
    	int result = 0;
    	int length = bytesArray.length;
    	for (int i = 0; i < length; i++) {
    		result |= ((int)bytesArray[i] & FF) << i * EIGHT;
    	}
    	return result; 
    }
    // 利用二进制流读取Bitmap位图文件
    @Override
    public Image myRead(String filepath) {
        Image image;
        try {
        	File infile = new File(filepath);
            FileInputStream fi = new FileInputStream(infile);
            
            // 位图头和位图信息总共 54 字节
            byte fourBytes[] = new byte[FOUR];
            byte twoBytes[] = new byte[TWO];
            // 字节 #0-13 保存位图头
            // 字节 #14-17 保存影像区块大小
            fi.skip(18);
            
            // 字节 #18-21 保存位图宽度（以像素个数表示）
            fi.read(fourBytes);
            int width = bytesToInt(fourBytes);
            
            // 字节 #22-25 保存位图高度（以像素个数表示）。
            fi.read(fourBytes);
            int height = bytesToInt(fourBytes);
           
            //  字节 #28-29 保存每个像素的位数，它是图像的颜色深度。常用值是1、4、8（灰阶）和24（彩色）。
            fi.skip(2);
            fi.read(twoBytes);
           
            // 字节 #34-37    保存图像大小。这是原始位图数据的大小，不要与文件大小混淆
            fi.skip(4);
            fi.read(fourBytes);
            int imageSize = bytesToInt(fourBytes);

            // 字节 #38-53 像素对齐
            // 若像素使用的字节不是4的倍数，则用空白填充(彩图为24位，为4的倍数，不需要填充)
            fi.skip(16);
            	// 填充的位数
                int paddingSize = ((imageSize / height) - width * THREE);
                if (paddingSize == FOUR) {
                	paddingSize = 0;
                }
                // 像素数组：存放所有像素
                int pixels[] = new int[height * width];
                // 存放每个像素的RGB值
                byte rgb[] = new byte[imageSize];
                
                fi.read(rgb);
                int index = 0;
                for (int j = 0; j < height; j++) {
                    for (int i = 0; i < width; i++) {
                        int t = width * (height - j - 1) + i;
                        // 像素的RGB值
                        pixels[t] = (COLORCHANNAL & FF) << 3 * EIGHT
                                | (((int)rgb[index + TWO] & FF) << 2 * EIGHT)
                                | (((int)rgb[index + 1] & FF) << EIGHT)
                                | (int)rgb[index] & FF;
                        index += THREE;
                    }
                    index += paddingSize;
                }
                // 从像素数组转换为java.awt.Image
                image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(  
                           width, height, pixels, 0, width)); 
            fi.close();
            return image;
            
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    
    // 调用Java的API把处理完的图像保存为BMP格式图像
    @Override
    public Image myWrite(Image image, String filepath){
        try {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            File imageFile = new File(filepath);
            BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            bufImage.getGraphics().drawImage(image, 0, 0, width, height, null);
            ImageIO.write(bufImage, "bmp", imageFile);
            return image;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
}
