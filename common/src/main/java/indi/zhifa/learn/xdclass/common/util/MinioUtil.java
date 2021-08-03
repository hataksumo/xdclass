package indi.zhifa.learn.xdclass.common.util;

import cn.hutool.core.lang.UUID;
import com.citc.planing.excel.common.exception.ServiceException;
import com.citc.planing.excel.config.MinioConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hatak
 */
@Component
public class MinioUtil {

    private final MinioClient mMinioClient;
    private final MinioConfig mMinioConfig;

    public MinioUtil(MinioClient pMinioClient,
                     MinioConfig pMinioConfig){
        mMinioClient = pMinioClient;
        mMinioConfig = pMinioConfig;
    }

    /**
     * url分隔符
     */
    public static final String URI_DELIMITER = "/";


    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    public String putObject (MultipartFile multipartFile) {
        return putObject(new MultipartFile[] {multipartFile}).get(0);
    }

    public String putObject(String[] pFolder, String pOrgFileName, InputStream pOutputStream, long pSize){
        // UUID重命名
        String fileName = UUID.randomUUID().toString().replace("-", "")
                + "." + getSuffix(pOrgFileName);

        String[] folders = pFolder;

        String finalPath = new StringBuilder(String.join(URI_DELIMITER, folders))
                .append(URI_DELIMITER)
                .append(fileName).toString();

        try{
            mMinioClient.putObject(PutObjectArgs.builder()
                    .stream(pOutputStream, pSize,PutObjectArgs.MIN_MULTIPART_SIZE)
                    .object(finalPath)
                    //.contentType("")
                    .bucket(mMinioConfig.getBucketName())
                    .build());
            return finalPath;
        }catch (Exception ex){
            throw new ServiceException("存储数据时发生错误，错误信息是 "+ex.toString());
        }
    }

    public String putObject(String[] pFolder,String pOrgFileName, MultipartFile multipartFile){
        try{
            String fileName = UUID.randomUUID().toString().replace("-", "")
                    + "." + getSuffix(pOrgFileName);

            // 年/月/日/file
            String finalPath = new StringBuilder(String.join(URI_DELIMITER, pFolder))
                    .append(URI_DELIMITER)
                    .append(fileName).toString();

            mMinioClient.putObject(PutObjectArgs.builder()
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(),PutObjectArgs.MIN_MULTIPART_SIZE)
                    .object(finalPath)
                    .contentType(multipartFile.getContentType())
                    .bucket(mMinioConfig.getBucketName())
                    .build());
            return finalPath;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     * @param multipartFiles
     * @return
     */
    public List<String> putObject(MultipartFile ...multipartFiles) {
        try {

            List<String> retVal = new LinkedList<>();

            String[] folders = getDateFolder();

            for (MultipartFile multipartFile : multipartFiles) {

                // UUID重命名
                String fileName = UUID.randomUUID().toString().replace("-", "")
                        + "." + getSuffix(multipartFile.getOriginalFilename());

                // 年/月/日/file
                String finalPath = new StringBuilder(String.join(URI_DELIMITER, folders))
                        .append(URI_DELIMITER)
                        .append(fileName).toString();

                mMinioClient.putObject(PutObjectArgs.builder()
                        .stream(multipartFile.getInputStream(), multipartFile.getSize(),PutObjectArgs.MIN_MULTIPART_SIZE)
                        .object(finalPath)
                        .contentType(multipartFile.getContentType())
                        .bucket(mMinioConfig.getBucketName())
                        .build());
                retVal.add(fileName);
            }
            return retVal;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制文件
     * @param source
     * @param target
     */
    public void copy(String source, String target) {

        try {
            mMinioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(mMinioConfig.getBucketName())
                    .object(target)
                    .source(CopySource.builder()
                            .bucket(mMinioConfig.getBucketName())
                            .object(source)
                            .build())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除文件
     * @param object
     */
    public void delete (String object) {
        try {
            mMinioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(mMinioConfig.getBucketName())
                    .object(object)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    protected static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);
            if (!suffix.isEmpty()) {
                return suffix;
            }
        }
        throw new IllegalArgumentException("非法文件名称："  + fileName);
    }

    public GetObjectResponse getData(String pPath){
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

    /**
     * 获取年月日[2020, 09, 01]
     * @return
     */
    protected static String[] getDateFolder() {
        String[] retVal = new String[3];

        LocalDate localDate = LocalDate.now();
        retVal[0] = localDate.getYear() + "";

        int month = localDate.getMonthValue();
        retVal[1] = month < 10 ? "0" + month : month + "";

        int day = localDate.getDayOfMonth();
        retVal[2] = day < 10 ? "0" + day : day + "";

        return retVal;
    }

}
