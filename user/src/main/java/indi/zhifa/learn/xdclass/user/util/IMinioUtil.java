package indi.zhifa.learn.xdclass.user.util;

import io.minio.GetObjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface IMinioUtil {
    /**
     * 上传文件，文件信息以pFile 为准
     *
     * @param pFolder 目录
     * @param pFile 文件对象
     * @return 最终存储的路径
     */
    String putFile(List<String> pFolder, MultipartFile pFile);

    /**
     * 上传文件
     *
     * @param pFolder 目录
     * @param pName 文件名
     * @param pInputStream 输入流
     * @param pSize 大小
     * @return 最终存储的路径
     */
    String putFile(List<String> pFolder, String pName, String pPrefix, InputStream pInputStream, long pSize);

    /**
     * 获取文件
     *
     * @param pPath 文件路径
     * @return 文件响应流对象
     */
    GetObjectResponse getFile(String pPath);

    /**
     * 删除文件
     * @param pPath 文件路径
     */
    void deleteFile(String pPath);
}
