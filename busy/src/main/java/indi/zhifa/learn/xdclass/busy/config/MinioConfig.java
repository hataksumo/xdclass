package indi.zhifa.learn.xdclass.busy.config;

import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hatak
 */
@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    String endpoint;
    @Getter
    @Value("${minio.bucketName}")
    String bucketName;
    @Value("${minio.accessKey}")
    String accessKey;
    @Value("${minio.secretKey}")
    String secretKey;

    @Bean
    public MinioClient getMinioClient(){
        MinioClient minioClient =MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey).build();
        try{
            minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        }catch (Exception ex){
            throw new ServiceException("没有找到名为【"+bucketName+"】的bucketName");
        }

        return minioClient;
    }
}
