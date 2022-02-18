package indi.zhifa.learn.xdclass.user.util;

import cn.hutool.core.img.ImgUtil;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageUtil {
    public static byte[] resizeImage(InputStream pIn, int pWidth, int pHeight){
        BufferedImage bufImg = null;
        try{
            bufImg = ImgUtil.read(pIn);
        }catch (Exception ex){
            throw new ServiceException("读取图片时发生错误，错误信息是 "+ex.toString());
        }
        int orgWidth = bufImg.getWidth();
        int orgHeight = bufImg.getHeight();

        double radio = (double)pHeight / pWidth;
        double orgRadio = (double)orgHeight / orgWidth;

        double scale = 1;
        if(orgRadio > radio){
            // 高了
            scale = (double)pWidth / orgWidth;
        }else{
            // 宽了
            scale = (double)pHeight / orgHeight;
        }

        int newWidth = (int)(orgWidth * scale);
        int newHeight = (int)(orgHeight * scale);

        Image scaledImg = bufImg.getScaledInstance(newWidth, newHeight, 0);
        BufferedImage zoomImg = new BufferedImage(pWidth, pHeight, BufferedImage.TYPE_INT_RGB);
        zoomImg.getGraphics().drawImage(scaledImg,
                0,0,pWidth,pHeight,
                (newWidth - pWidth)/2, (newHeight - pHeight)/2,
                (newWidth + pWidth)/2,(newHeight + pHeight)/2,
                null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(pWidth * pHeight * 32);
        try{
            ImageIO.write(zoomImg,"jpg",outputStream);
        }catch (Exception ex){
            throw new ServiceException("输出图像时发生错误，错位信息是 "+ ex.toString());
        }

        return outputStream.toByteArray();

    }
}
