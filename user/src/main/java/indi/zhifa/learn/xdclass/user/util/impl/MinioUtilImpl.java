package indi.zhifa.learn.xdclass.user.util.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import indi.zhifa.learn.xdclass.user.config.MinioConfig;
import indi.zhifa.learn.xdclass.user.util.IMinioUtil;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import io.minio.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class MinioUtilImpl implements IMinioUtil {

    private final MinioClient mMinioClient;
    private final MinioConfig mMinioConfig;

    static final String URI_DELIMITER = "/";

    protected String getFilePath(List<String> pFolder, String pName, String pPrefix){
        String uuidName = UUID.randomUUID().toString().replace("-", "");
        String path = String.join(URI_DELIMITER,pFolder) + URI_DELIMITER+uuidName+"_"+pName+"."+pPrefix;
        //String realPath = URLUtil.encode(path);
        return path;
    }

    @Override
    public String putFile(List<String> pFolder, MultipartFile pFile) {
        String name = pFile.getOriginalFilename();
        String fileName = FileUtil.mainName(name);
        String prefix = FileUtil.extName(name);
        String path = null;
        try{
            path = putFile(pFolder,fileName,prefix,pFile.getInputStream(),pFile.getSize());
        }catch (Exception ex){
            log.error("获取流时发生错误，错误信息是 "+ex.toString());
        }
        return path;
    }

    @Override
    public String putFile(List<String> pFolder, String pName, String pPrefix, InputStream pInputStream, long pSize) {
        String path = getFilePath(pFolder,pName,pPrefix);
        try{
            mMinioClient.putObject(PutObjectArgs.builder()
                    .stream(pInputStream, pSize,PutObjectArgs.MIN_MULTIPART_SIZE)
                    .object(path)
                    .bucket(mMinioConfig.getBucketName())
                    .build());
        }catch (Exception ex){
            throw new ServiceException("存储数据时发生错误，错误信息是 "+ex.toString());
        }
        return path;
    }


    @Override
    public GetObjectResponse getFile(String pPath) {
        GetObjectResponse getObjectResponse = null;
        try{
            getObjectResponse = mMinioClient.getObject(GetObjectArgs.builder()
                    .bucket(mMinioConfig.getBucketName())
                    .object(pPath)
                    .build());
        }catch (Exception ex){
            throw new ServiceException("下载文件发生错误，错误信息是 "+ ex.toString());
        }
        return getObjectResponse;
    }

    @Override
    public void deleteFile(String pPath) {
        try {
            mMinioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(mMinioConfig.getBucketName())
                    .object(pPath)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
