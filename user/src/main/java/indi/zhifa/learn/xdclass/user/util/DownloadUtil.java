package indi.zhifa.learn.xdclass.user.util;

import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.FilterInputStream;
import java.io.OutputStream;

public class DownloadUtil {
    public static void download(String pFileName, FilterInputStream pInputStream){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.setHeader("Content-Disposition", "attachment;fileName=" + pFileName);
        response.setContentType("application/octet-stream");
        int maxLen = 1024 * 1024;
        byte[] buff = new byte[maxLen];
        int rc = 0;
        try {
            OutputStream outputStream = response.getOutputStream();
            while ((rc = pInputStream.read(buff, 0, maxLen)) > 0) {
                outputStream.write(buff, 0, rc);
            }
        } catch (Exception ex) {
            throw new ServiceException("输出文件时发生错误，错误信息是 " + ex.toString());
        }
    }
}
