/**
 *
 */
package com.giants.common.file;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @author vencent.lu
 *
 */
public class ImageUtil {

    /**
     * 按比例缩放缓冲图像
     * @param srcBufferedImage 源缓冲图像
     * @param scale 缩放比例
     * @return BufferedImage
     */
    public final static BufferedImage scale(BufferedImage srcBufferedImage,
                                            double scale) {
        int width = (int) Math.round(srcBufferedImage.getWidth() * scale);
        int height = (int) Math.round(srcBufferedImage.getHeight() * scale);
        Image image = srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage destBufferedImage = new BufferedImage(width, height, srcBufferedImage.getType());
        Graphics graphics = destBufferedImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return destBufferedImage;
    }

    /**
     * 根据图片输入流生成缩放缓冲图像
     * @param srcInputStreamImage 图片输入流
     * @param scale 缩放比例
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage scale(InputStream srcInputStreamImage,
                                            double scale) throws IOException {
        return scale(ImageIO.read(srcInputStreamImage), scale);
    }

    /**
     * 根据图片输入流生成缩放缓冲图像
     * @param srcImageFile 源图片文件
     * @param scale 缩放比例
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage scale(File srcImageFile, double scale)
            throws IOException {
        return scale(ImageIO.read(srcImageFile), scale);
    }

    /**
     * 根据图片输入流生成缩放缓冲图像
     * @param srcImageFileName 源图片名称
     * @param scale 缩放比例
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage scale(String srcImageFileName,
                                            double scale) throws IOException {
        return scale(new File(srcImageFileName), scale);
    }

    /**
     * 按比例缩放图像文件
     * @param srcImageFile 源图片文件
     * @param destImageFile 目标图片文件
     * @param scale 缩放比例
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void scale(File srcImageFile, File destImageFile,
                                   double scale, String imageFormat) throws IOException {
        ImageIO.write(scale(ImageIO.read(srcImageFile), scale), imageFormat,
                destImageFile);
    }

    /**
     * 根据图片输入流生成缩放图片
     * @param srcInputStreamImage 图片输入流
     * @param destImageFile 目标图片文件
     * @param scale 缩放比例
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void scale(InputStream srcInputStreamImage,
                                   File destImageFile, double scale, String imageFormat)
            throws IOException {
        ImageIO.write(scale(srcInputStreamImage, scale), imageFormat,
                destImageFile);
    }

    /**
     * 根据图片输入流生成缩放图片
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFile 目标图片文件
     * @param scale 缩放比例
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void scale(BufferedImage srcBufferedImage,
                                   File destImageFile, double scale, String imageFormat)
            throws IOException {
        ImageIO.write(scale(srcBufferedImage, scale), imageFormat,
                destImageFile);
    }

    /**
     * 根据图片输入流生成缩放图片
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFileName 目标图片文件名
     * @param scale 缩放比例
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void scale(BufferedImage srcBufferedImage,
                                   String destImageFileName, double scale, String imageFormat)
            throws IOException {
        scale(srcBufferedImage, new File(destImageFileName), scale, imageFormat);
    }

    /**
     * 根据文件名称缩放图像文件
     * @param srcImageFileName 源图片文件名称
     * @param destImageFileName 目标图片文件名称
     * @param scale 缩放比例
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void scale(String srcImageFileName,
                                   String destImageFileName, double scale, String imageFormat)
            throws IOException {
        scale(new File(srcImageFileName), new File(destImageFileName), scale,
                imageFormat);
    }

    /**
     * 根据图片输入流生成缩放图片
     * @param srcInputStreamImage 图片输入流
     * @param destImageFileName 目标图片文件
     * @param scale 缩放比例
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void scale(InputStream srcInputStreamImage,
                                   String destImageFileName, double scale, String imageFormat)
            throws IOException {
        scale(srcInputStreamImage, new File(destImageFileName), scale,
                imageFormat);
    }

    /**
     * 按指定宽高缩放缓冲图像
     * @param srcBufferedImage 源缓冲图像
     * @param width 宽
     * @param height 高
     * @param padding 是否补白
     * @return BufferedImage
     */
    public final static BufferedImage zoom(BufferedImage srcBufferedImage,
                                           int width, int height, boolean padding) {
        int imgWidth = srcBufferedImage.getWidth();
        int imgHeight = srcBufferedImage.getHeight();
        if (imgWidth > width || imgHeight > height) {
            if (Double.valueOf(imgHeight) / imgWidth >= Double.valueOf(height)
                    / width) {
                imgWidth = (int) Math.round(Double.valueOf(height) / imgHeight
                        * imgWidth);
                imgHeight = height;
            } else {
                imgHeight = (int) Math.round(Double.valueOf(width) / imgWidth
                        * imgHeight);
                imgWidth = width;
            }
        }
        if (padding) {
            BufferedImage destBufferedImage = new BufferedImage(width, height,
                    srcBufferedImage.getType());
            Graphics2D g = destBufferedImage.createGraphics();
            g.setBackground(Color.WHITE);
            g.clearRect(0, 0, width, height);
            g.drawImage(srcBufferedImage.getScaledInstance(imgWidth, imgHeight,
                    Image.SCALE_SMOOTH), (width - imgWidth) / 2,
                    (height - imgHeight) / 2, imgWidth, imgHeight, null);
            g.dispose();
            return destBufferedImage;
        } else {
            return srcBufferedImage;
        }
    }

    /**
     * 根据图片输入流按指定宽高缩放缓冲图像
     * @param srcInputStreamImage 源图片流
     * @param width 宽
     * @param height 高
     * @param padding 是否补白
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage zoom(InputStream srcInputStreamImage,
                                           int width, int height, boolean padding) throws IOException {
        return zoom(ImageIO.read(srcInputStreamImage), width, height, padding);
    }

    /**
     * 根据图片输入流按指定宽高缩放缓冲图像
     * @param srcImageFile 源图片文件
     * @param width 宽
     * @param height 高
     * @param padding 是否补白
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage zoom(File srcImageFile, int width,
                                           int height, boolean padding) throws IOException {
        return zoom(ImageIO.read(srcImageFile), width, height, padding);
    }

    /**
     * 根据图片输入流按指定宽高缩放缓冲图像
     * @param srcImageFileName 源图片名称
     * @param width 宽
     * @param height 高
     * @param padding 是否补白
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage zoom(String srcImageFileName, int width,
                                           int height, boolean padding) throws IOException {
        return zoom(new File(srcImageFileName), width, height, padding);
    }

    /**
     * 根据宽高缩放图像文件
     * @param srcImageFile 源图片文件
     * @param destImageFile 目标图片文件
     * @param width 宽
     * @param height 高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @param padding 是否补白
     * @throws IOException IO异常
     */
    public final static void zoom(File srcImageFile, File destImageFile,
                                  int width, int height, String imageFormat, boolean padding)
            throws IOException {
        ImageIO.write(zoom(ImageIO.read(srcImageFile), width, height, padding),
                imageFormat, destImageFile);
    }

    /**
     * 根据宽高缩放图像文件
     * @param srcInputStreamImage 源图片流
     * @param destImageFile 目标图片文件
     * @param width 宽
     * @param height 高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @param padding 是否补白
     * @throws IOException IO异常
     */
    public final static void zoom(InputStream srcInputStreamImage,
                                  File destImageFile, int width, int height, String imageFormat,
                                  boolean padding) throws IOException {
        ImageIO.write(
                zoom(ImageIO.read(srcInputStreamImage), width, height, padding),
                imageFormat, destImageFile);
    }

    /**
     * 根据宽高缩放图像文件
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFile 目标图片文件
     * @param width 宽
     * @param height 高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @param padding 是否补白
     * @throws IOException IO异常
     */
    public final static void zoom(BufferedImage srcBufferedImage,
                                  File destImageFile, int width, int height, String imageFormat,
                                  boolean padding) throws IOException {
        ImageIO.write(zoom(srcBufferedImage, width, height, padding),
                imageFormat, destImageFile);
    }

    /**
     * 根据宽高缩放图像文件
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFileName 目标图片名
     * @param width 宽
     * @param height 高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @param padding 是否补白
     * @throws IOException IO异常
     */
    public final static void zoom(BufferedImage srcBufferedImage,
                                  String destImageFileName, int width, int height,
                                  String imageFormat, boolean padding) throws IOException {
        zoom(srcBufferedImage, new File(destImageFileName), width, height,
                imageFormat, padding);
    }

    /**
     * 根据宽高缩放图像文件
     * @param srcImageFileName 源图片名
     * @param destImageFileName 目标图片名
     * @param width 宽
     * @param height 高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @param padding 是否补白
     * @throws IOException IO异常
     */
    public final static void zoom(String srcImageFileName,
                                  String destImageFileName, int width, int height,
                                  String imageFormat, boolean padding) throws IOException {
        zoom(new File(srcImageFileName), new File(destImageFileName), width,
                height, imageFormat, padding);
    }

    /**
     * 根据宽高缩放图像文件
     * @param srcInputStreamImage 源图片流
     * @param destImageFileName 目标图片名
     * @param width 宽
     * @param height 高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @param padding 是否补白
     * @throws IOException IO异常
     */
    public final static void zoom(InputStream srcInputStreamImage,
                                  String destImageFileName, int width, int height,
                                  String imageFormat, boolean padding) throws IOException {
        zoom(srcInputStreamImage, new File(destImageFileName), width, height,
                imageFormat, padding);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcBufferedImage 源缓冲图像
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @return BufferedImage
     */
    public final static BufferedImage cropping(BufferedImage srcBufferedImage,
                                               int x, int y, int width, int height) {
        Image img = Toolkit.getDefaultToolkit().createImage(
                new FilteredImageSource(srcBufferedImage.getSource(),
                        new CropImageFilter(x, y, width, height)));
        BufferedImage destBufferedImage = new BufferedImage(width, height,
                srcBufferedImage.getType());
        Graphics2D g = destBufferedImage.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, width, height);
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        return destBufferedImage;
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcInputStreamImage 源图片流
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage cropping(InputStream srcInputStreamImage,
                                               int x, int y, int width, int height) throws IOException {
        return cropping(ImageIO.read(srcInputStreamImage), x, y, width, height);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcImageFile 源图片文件
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage cropping(File srcImageFile, int x, int y,
                                               int width, int height) throws IOException {
        return cropping(ImageIO.read(srcImageFile), x, y, width, height);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcImageFileName 源图片文件名
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage cropping(String srcImageFileName, int x,
                                               int y, int width, int height) throws IOException {
        return cropping(new File(srcImageFileName), x, y, width, height);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcImageFile 源图片文件
     * @param destImageFile 目标图片文件
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void cropping(File srcImageFile, File destImageFile,
                                      int x, int y, int width, int height, String imageFormat)
            throws IOException {
        ImageIO.write(
                cropping(ImageIO.read(srcImageFile), x, y, width, height),
                imageFormat, destImageFile);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcInputStreamImage 源图片流
     * @param destImageFile 目标图片文件
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void cropping(InputStream srcInputStreamImage,
                                      File destImageFile, int x, int y, int width, int height,
                                      String imageFormat) throws IOException {
        ImageIO.write(cropping(srcInputStreamImage, x, y, width, height),
                imageFormat, destImageFile);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFile 目标图片文件
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void cropping(BufferedImage srcBufferedImage,
                                      File destImageFile, int x, int y, int width, int height,
                                      String imageFormat) throws IOException {
        ImageIO.write(cropping(srcBufferedImage, x, y, width, height),
                imageFormat, destImageFile);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFileName 目标图片文件名
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void cropping(BufferedImage srcBufferedImage,
                                      String destImageFileName, int x, int y, int width, int height,
                                      String imageFormat) throws IOException {
        cropping(srcBufferedImage, new File(destImageFileName), x, y, width,
                height, imageFormat);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcImageFileName 源图片名称
     * @param destImageFileName 目标图片名称
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void cropping(String srcImageFileName,
                                      String destImageFileName, int x, int y, int width, int height,
                                      String imageFormat) throws IOException {
        cropping(new File(srcImageFileName), new File(destImageFileName), x, y,
                width, height, imageFormat);
    }

    /**
     * 根据坐标、高、宽切割图片
     * @param srcInputStreamImage 源图片流
     * @param destImageFileName 目标图片名称
     * @param x 切割起点横坐标
     * @param y 切割起点纵坐标
     * @param width 切割图片宽
     * @param height 切割图片高
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void cropping(InputStream srcInputStreamImage,
                                      String destImageFileName, int x, int y, int width, int height,
                                      String imageFormat) throws IOException {
        cropping(srcInputStreamImage, new File(destImageFileName), x, y, width,
                height, imageFormat);
    }

    /**
     * 图像类型转换：GIF转JPG、GIF转PNG、PNG转JPG、PNG转GIF(X)、BMP转PNG
     * @param srcImageFile 源图像文件
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @param destImageFile 目标图像文件
     * @throws IOException IO异常
     */
    public final static void convert(File srcImageFile, String imageFormat,
                                     File destImageFile) throws IOException {
        ImageIO.write(ImageIO.read(srcImageFile), imageFormat, destImageFile);
    }

    /**
     * 图像类型转换：GIF转JPG、GIF转PNG、PNG转JPG、PNG转GIF(X)、BMP转PNG
     * @param srcImageFileName 源图像文件名称
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @param destImageFileName 目标图像文件名称
     * @throws IOException IO异常
     */
    public final static void convert(String srcImageFileName,
                                     String imageFormat, String destImageFileName) throws IOException {
        convert(new File(srcImageFileName), imageFormat, new File(
                destImageFileName));
    }

    /**
     * 彩色转为黑白
     * @param srcBufferedImage 源缓冲图像
     * @return BufferedImage
     */
    public final static BufferedImage gray(BufferedImage srcBufferedImage) {
        ColorConvertOp op = new ColorConvertOp(
                ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return op.filter(srcBufferedImage, null);
    }

    /**
     * 彩色转为黑白
     * @param srcInputStreamImage 源图像流
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage gray(InputStream srcInputStreamImage)
            throws IOException {
        return gray(ImageIO.read(srcInputStreamImage));
    }

    /**
     * 彩色转为黑白
     * @param srcImageFile 源图片文件
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage gray(File srcImageFile)
            throws IOException {
        return gray(ImageIO.read(srcImageFile));
    }

    /**
     * 彩色转为黑白
     * @param srcImageFileName 源图片名称
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage gray(String srcImageFileName)
            throws IOException {
        return gray(new File(srcImageFileName));
    }

    /**
     * 彩色转为黑白
     * @param srcImageFile 源图像文件
     * @param destImageFile 目标图像文件
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void gray(File srcImageFile, File destImageFile,
                                  String imageFormat) throws IOException {
        ImageIO.write(gray(ImageIO.read(srcImageFile)), imageFormat,
                destImageFile);
    }

    /**
     * 彩色转为黑白
     * @param srcInputStreamImage 源图像流
     * @param destImageFile 目标图像文件
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void gray(InputStream srcInputStreamImage,
                                  File destImageFile, String imageFormat) throws IOException {
        ImageIO.write(gray(ImageIO.read(srcInputStreamImage)), imageFormat,
                destImageFile);
    }

    /**
     * 彩色转为黑白
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFile 目标图像文件
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void gray(BufferedImage srcBufferedImage,
                                  File destImageFile, String imageFormat) throws IOException {
        ImageIO.write(gray(srcBufferedImage), imageFormat, destImageFile);
    }

    /**
     * 彩色转为黑白
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFileName 目标文件名
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void gray(BufferedImage srcBufferedImage,
                                  String destImageFileName, String imageFormat) throws IOException {
        gray(srcBufferedImage, new File(destImageFileName), imageFormat);
    }

    /**
     * 彩色转为黑白
     * @param srcImageFileName 源图像文件名
     * @param destImageFileName 目标图像文件名
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void gray(String srcImageFileName,
                                  String destImageFileName, String imageFormat) throws IOException {
        gray(new File(srcImageFileName), new File(destImageFileName),
                imageFormat);
    }

    /**
     * 彩色转为黑白
     * @param srcInputStreamImage 源图像流
     * @param destImageFileName 目标图像文件名
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void gray(InputStream srcInputStreamImage,
                                  String destImageFileName, String imageFormat) throws IOException {
        gray(srcInputStreamImage, new File(destImageFileName), imageFormat);
    }

    /**
     * 给图片添加文字水印
     * @param srcBufferedImage 源缓冲图像
     * @param text 水印文字
     * @param fontName 水印的字体名称 如: 宋体、楷书  等
     * @param fontStyle 水印的字体样式 参考 java.awt.Font 中的常量
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     */
    public final static BufferedImage watermarkText(
            BufferedImage srcBufferedImage, String text, String fontName,
            int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
        Graphics2D g = srcBufferedImage.createGraphics();
        g.setColor(color);
        g.setFont(new Font(fontName, fontStyle, fontSize));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        g.drawString(text, (srcBufferedImage.getWidth() - getLength(text)
                * fontSize)
                / 2 + x, (srcBufferedImage.getHeight() - fontSize) / 2 + y);
        g.dispose();
        return srcBufferedImage;
    }

    /**
     * 给图片添加文字水印
     * @param srcInputStreamImage 源图像流
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkText(
            InputStream srcInputStreamImage, String text, String fontName,
            int fontStyle, Color color, int fontSize, int x, int y, float alpha)
            throws IOException {
        return watermarkText(ImageIO.read(srcInputStreamImage), text, fontName,
                fontStyle, color, fontSize, x, y, alpha);
    }

    /**
     * 给图片添加文字水印
     * @param srcImageFile 源图片文件
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkText(File srcImageFile,
                                                    String text, String fontName, int fontStyle, Color color,
                                                    int fontSize, int x, int y, float alpha) throws IOException {
        return watermarkText(ImageIO.read(srcImageFile), text, fontName,
                fontStyle, color, fontSize, x, y, alpha);
    }

    /**
     * 给图片添加文字水印
     * @param srcImageFileName 源图片文件名
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkText(String srcImageFileName,
                                                    String text, String fontName, int fontStyle, Color color,
                                                    int fontSize, int x, int y, float alpha) throws IOException {
        return watermarkText(new File(srcImageFileName), text, fontName,
                fontStyle, color, fontSize, x, y, alpha);
    }

    /**
     * 给图片添加文字水印
     * @param srcImageFile 源图像文件
     * @param destImageFile 目标图像文件
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkText(File srcImageFile,
                                           File destImageFile, String text, String fontName, int fontStyle,
                                           Color color, int fontSize, int x, int y, float alpha,
                                           String imageFormat) throws IOException {
        ImageIO.write(
                watermarkText(ImageIO.read(srcImageFile), text, fontName,
                        fontStyle, color, fontSize, x, y, alpha), imageFormat,
                destImageFile);
    }

    /**
     * 给图片添加文字水印
     * @param srcInputStreamImage 源图像流
     * @param destImageFile 目标图像文件
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkText(InputStream srcInputStreamImage,
                                           File destImageFile, String text, String fontName, int fontStyle,
                                           Color color, int fontSize, int x, int y, float alpha,
                                           String imageFormat) throws IOException {
        ImageIO.write(
                watermarkText(ImageIO.read(srcInputStreamImage), text,
                        fontName, fontStyle, color, fontSize, x, y, alpha),
                imageFormat, destImageFile);
    }

    /**
     * 给图片添加文字水印
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFile 目标图像文件
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkText(BufferedImage srcBufferedImage,
                                           File destImageFile, String text, String fontName, int fontStyle,
                                           Color color, int fontSize, int x, int y, float alpha,
                                           String imageFormat) throws IOException {
        ImageIO.write(
                watermarkText(srcBufferedImage, text, fontName, fontStyle,
                        color, fontSize, x, y, alpha), imageFormat,
                destImageFile);
    }

    /**
     * 给图片添加文字水印
     * @param srcBufferedImage 源缓冲图像
     * @param destImageFileName 目标图片文件名
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkText(BufferedImage srcBufferedImage,
                                           String destImageFileName, String text, String fontName,
                                           int fontStyle, Color color, int fontSize, int x, int y,
                                           float alpha, String imageFormat) throws IOException {
        watermarkText(srcBufferedImage, new File(destImageFileName), text,
                fontName, fontStyle, color, fontSize, x, y, alpha, imageFormat);
    }

    /**
     * 给图片添加文字水印
     * @param srcImageFileName 源图像文件名
     * @param destImageFileName 目标图像文件名
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkText(String srcImageFileName,
                                           String destImageFileName, String text, String fontName,
                                           int fontStyle, Color color, int fontSize, int x, int y,
                                           float alpha, String imageFormat) throws IOException {
        watermarkText(new File(srcImageFileName), new File(destImageFileName),
                text, fontName, fontStyle, color, fontSize, x, y, alpha,
                imageFormat);
    }

    /**
     * 给图片添加文字水印
     * @param srcInputStreamImage 源图像流
     * @param destImageFileName 目标图像文件名
     * @param text 水印文字
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkText(InputStream srcInputStreamImage,
                                           String destImageFileName, String text, String fontName,
                                           int fontStyle, Color color, int fontSize, int x, int y,
                                           float alpha, String imageFormat) throws IOException {
        watermarkText(srcInputStreamImage, new File(destImageFileName), text,
                fontName, fontStyle, color, fontSize, x, y, alpha, imageFormat);
    }

    /**
     * 给图片添加图片水印
     * @param srcBufferedImage 源缓冲图像
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkImage(
            BufferedImage srcBufferedImage, File image, int x, int y,
            float alpha) throws IOException {
        Graphics2D g = srcBufferedImage.createGraphics();
        BufferedImage waterImage = ImageIO.read(image);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        g.drawImage(
                waterImage,
                (srcBufferedImage.getWidth() - waterImage.getWidth()) / 2 + x,
                (srcBufferedImage.getHeight() - waterImage.getHeight()) / 2 + y,
                waterImage.getWidth(), waterImage.getHeight(), null);
        g.dispose();
        return srcBufferedImage;
    }

    /**
     * 给图片添加图片水印
     * @param srcBufferedImage 源缓冲图像
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkImage(
            BufferedImage srcBufferedImage, String imageFileName, int x, int y,
            float alpha) throws IOException {
        return watermarkImage(srcBufferedImage, new File(imageFileName), x, y,
                alpha);
    }

    /**
     * 给图片添加图片水印
     * @param srcInputStreamImage 源图片流
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkImage(
            InputStream srcInputStreamImage, File image, int x, int y,
            float alpha) throws IOException {
        return watermarkImage(ImageIO.read(srcInputStreamImage), image, x, y,
                alpha);
    }

    /**
     * 给图片添加图片水印
     * @param srcImageFile 源图片文件
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkImage(File srcImageFile,
                                                     File image, int x, int y, float alpha) throws IOException {
        return watermarkImage(ImageIO.read(srcImageFile), image, x, y, alpha);
    }

    /**
     * 给图片添加图片水印
     * @param srcImageFileName 源图片文件名
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkImage(String srcImageFileName,
                                                     File image, int x, int y, float alpha) throws IOException {
        return watermarkImage(new File(srcImageFileName), image, x, y, alpha);
    }

    /**
     * 给图片添加图片水印
     * @param srcInputStreamImage 源图片流
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkImage(
            InputStream srcInputStreamImage, String imageFileName, int x,
            int y, float alpha) throws IOException {
        return watermarkImage(ImageIO.read(srcInputStreamImage), imageFileName,
                x, y, alpha);
    }

    /**
     * 给图片添加图片水印
     * @param srcImageFile 源图片文件
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkImage(File srcImageFile,
                                                     String imageFileName, int x, int y, float alpha) throws IOException {
        return watermarkImage(ImageIO.read(srcImageFile), imageFileName, x, y, alpha);
    }

    /**
     * 给图片添加图片水印
     * @param srcImageFileName 源图片文件名
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @return BufferedImage
     * @throws IOException IO异常
     */
    public final static BufferedImage watermarkImage(String srcImageFileName,
                                                     String imageFileName, int x, int y, float alpha) throws IOException {
        return watermarkImage(new File(srcImageFileName), imageFileName, x, y,
                alpha);
    }

    /**
     * 给图片添加图片水印
     * @param srcImageFile 源图像文件
     * @param destImageFile 目标图像文件
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(File srcImageFile,
                                            File destImageFile, File image, int x, int y, float alpha,
                                            String imageFormat) throws IOException {
        ImageIO.write(
                watermarkImage(ImageIO.read(srcImageFile), image, x, y, alpha),
                imageFormat, destImageFile);
    }

    /**
     * 给图片添加图片水印
     * @param srcImageFile 源图像文件
     * @param destImageFile 目标图像文件
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(File srcImageFile,
                                            File destImageFile, String imageFileName, int x, int y, float alpha,
                                            String imageFormat) throws IOException {
        ImageIO.write(
                watermarkImage(ImageIO.read(srcImageFile), imageFileName, x, y, alpha),
                imageFormat, destImageFile);
    }

    /**
     * 给图片添加图片水印
     * @param srcInputStreamImage 源图像流
     * @param destImageFile 目标图像文件
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(InputStream srcInputStreamImage,
                                            File destImageFile, File image, int x, int y, float alpha,
                                            String imageFormat) throws IOException {
        ImageIO.write(
                watermarkImage(ImageIO.read(srcInputStreamImage), image, x, y,
                        alpha), imageFormat, destImageFile);
    }

    /**
     * 给图片添加图片水印
     * @param srcBufferedImage 源图像缓冲图片
     * @param destImageFile 目标图像文件
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(BufferedImage srcBufferedImage,
                                            File destImageFile, File image, int x, int y, float alpha,
                                            String imageFormat) throws IOException {
        ImageIO.write(watermarkImage(srcBufferedImage, image, x, y, alpha),
                imageFormat, destImageFile);
    }

    /**
     * 给图片添加图片水印
     * @param srcBufferedImage 源图像缓冲图片
     * @param destImageFileName 目标图像文件名
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(BufferedImage srcBufferedImage,
                                            String destImageFileName, File image, int x, int y, float alpha,
                                            String imageFormat) throws IOException {
        watermarkImage(srcBufferedImage, new File(destImageFileName), image, x,
                y, alpha, imageFormat);
    }

    /**
     * 给图片添加图片水印
     * @param srcInputStreamImage 源图像流
     * @param destImageFile 目标图像文件
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(InputStream srcInputStreamImage,
                                            File destImageFile, String imageFileName, int x, int y,
                                            float alpha, String imageFormat) throws IOException {
        ImageIO.write(
                watermarkImage(ImageIO.read(srcInputStreamImage),
                        imageFileName, x, y, alpha), imageFormat, destImageFile);
    }

    /**
     * 给图片添加图片水印
     * @param srcBufferedImage 源图像缓冲图片
     * @param destImageFile 目标图像文件
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(BufferedImage srcBufferedImage,
                                            File destImageFile, String imageFileName, int x, int y, float alpha,
                                            String imageFormat) throws IOException {
        ImageIO.write(watermarkImage(srcBufferedImage, imageFileName, x, y, alpha),
                imageFormat, destImageFile);
    }

    /**
     * 给图片添加图片水印
     * @param srcBufferedImage 源图像缓冲图片
     * @param destImageFileName 目标图像文件名
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(BufferedImage srcBufferedImage,
                                            String destImageFileName, String imageFileName, int x, int y,
                                            float alpha, String imageFormat) throws IOException {
        watermarkImage(srcBufferedImage, new File(destImageFileName),
                imageFileName, x, y, alpha, imageFormat);
    }

    /**
     * 给图片添加图片水印
     * @param srcImageFileName 源图片名
     * @param destImageFileName 目标图片名
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(String srcImageFileName,
                                            String destImageFileName, File image, int x, int y, float alpha,
                                            String imageFormat) throws IOException {
        watermarkImage(new File(srcImageFileName), new File(destImageFileName),
                image, x, y, alpha, imageFormat);
    }

    /**
     * 给图片添加图片水印
     * @param srcImageFileName 源图片名
     * @param destImageFileName 目标图片名
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(String srcImageFileName,
                                            String destImageFileName, String imageFileName, int x, int y,
                                            float alpha, String imageFormat) throws IOException {
        watermarkImage(new File(srcImageFileName), new File(destImageFileName),
                imageFileName, x, y, alpha, imageFormat);
    }

    /**
     * 给图片添加图片水印
     * @param srcInputStreamImage 源图像流
     * @param destImageFileName 目标图片名
     * @param image 水印图片文件
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(InputStream srcInputStreamImage,
                                            String destImageFileName, File image, int x, int y, float alpha,
                                            String imageFormat) throws IOException {
        watermarkImage(srcInputStreamImage, new File(destImageFileName), image,
                x, y, alpha, imageFormat);
    }

    /**
     * 给图片添加图片水印
     * @param srcInputStreamImage 源图像流
     * @param destImageFileName 目标图片名
     * @param imageFileName 水印图片文件名
     * @param x 横坐标修正值
     * @param y 纵坐标修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param imageFormat 图片压缩格式 如：GIF、JPG、JPEG、BMP、PNG、PSD
     * @throws IOException IO异常
     */
    public final static void watermarkImage(InputStream srcInputStreamImage,
                                            String destImageFileName, String imageFileName, int x, int y,
                                            float alpha, String imageFormat) throws IOException {
        watermarkImage(srcInputStreamImage, new File(destImageFileName),
                imageFileName, x, y, alpha, imageFormat);
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text
     * @return
     */
    private static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

}
