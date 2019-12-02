import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

import imagereader.IImageProcessor;

/**
 * 简单的图像处理
 * 继承JAVA中存在用于过滤图像的专用类RGBImageFilter
 */
public class ImplementImageProcessor implements IImageProcessor{
    private static final int SIXTEEN = 16;
    private static final int EIGHT = 8;
    
    private static final double GR = 0.299;
    private static final double GG = 0.587;
    private static final double GB = 0.114;
    // 红色通道
    private static final int FILTERRED = 0xffff0000;
    // 绿色通道
    private static final int FILTERGREEN = 0xff00ff00;
    // 蓝色通道
    private static final int FILTERBLUE = 0xff0000ff;
    
    private static final int RED = 0x00ff0000;
    private static final int GREEN = 0x0000ff00;
    private static final int BLUE = 0x000000ff;
    
    
    // 红色通道
    class RedFilter extends RGBImageFilter {
        public RedFilter() {
            canFilterIndexColorModel = true;
        }
        
        @Override
        public int filterRGB(int arg0, int arg1, int arg2) {
            return (arg2 & FILTERRED);
        }
    }
    
    // 提取并且显示彩色图像红色通道
    @Override
    public Image showChanelR(Image arg0) {
    	RedFilter red = new RedFilter();
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(arg0.getSource(),red));
    }
    
    // 绿色通道
    class GreenFilter extends RGBImageFilter {
        public GreenFilter() {
            canFilterIndexColorModel = true;
        }
        @Override
        public int filterRGB(int arg0, int arg1, int arg2) {
            return (arg2 & FILTERGREEN);
        }
    }
    
    // 提取并且显示彩色图像绿色通道
    @Override
    public Image showChanelG(Image arg0) {
    	GreenFilter green = new GreenFilter();
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(arg0.getSource(),green));
    }
    
    // 蓝色通道
    class BlueFilter extends RGBImageFilter {
        public BlueFilter() {
            canFilterIndexColorModel = true;
        }

        @Override
        public int filterRGB(int arg0, int arg1, int arg2) {
            return (arg2 & FILTERBLUE);
        }
    }
    
    // 提取并且显示彩色图像蓝色通道

    @Override
    public Image showChanelB(Image arg0) {
        BlueFilter blue = new BlueFilter();
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(arg0.getSource(),blue));
    }

    // 灰度图
    class GrayFilter extends RGBImageFilter {
        public GrayFilter() {
            canFilterIndexColorModel = true;
        }

        @Override
        public int filterRGB(int arg0, int arg1, int arg2) {
            int red = (arg2 & RED) >> SIXTEEN;
            int green = (arg2 & GREEN) >> EIGHT;        
            int blue = (arg2 & BLUE);
            int gray = (int)((double)(GR * red) + (double)(GG * green) + (double)(GB * blue));
            return (arg2 & 0xff000000)+(gray<<SIXTEEN)+(gray<<EIGHT)+gray;
        }
    }

    // 把读取彩色图像转换成灰度图像
    @Override
    public Image showGray(Image arg0) {
    	GrayFilter gray = new GrayFilter();
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(arg0.getSource(),gray));
    }
}
