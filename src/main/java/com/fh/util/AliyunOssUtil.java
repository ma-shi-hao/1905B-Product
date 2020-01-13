package com.fh.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

public class AliyunOssUtil {
                                           //oss-cn-beijing.aliyuncs.com
    private static String endpoint ="http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId ="LTAI4FrkiS6CaU3cNdiDAKN2";
    private static String accessKeySecret ="3pPYP7q7mUhoZ7kqHu3ovUeoa1i2VQ";
    private static String bucketName = "mashihao";

    public static String uploadOSSFree(MultipartFile uploadFile, String picturePath) throws IOException {
        //创建OSSClient实列
        OSS  ossClient=new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        //上传picturePath+uploadFile.getOriginalFilename()
         ossClient.putObject(bucketName,picturePath+uploadFile.getOriginalFilename(),new ByteArrayInputStream(uploadFile.getBytes()));
         //关闭client
        ossClient.shutdown();
        //设置过期时间
        Date expiration=new Date(new Date().getTime()+ 3600l * 1000 * 24 * 365 * 10);
        //取出文件上传路径
        String url = ossClient.generatePresignedUrl(bucketName, picturePath + uploadFile.getOriginalFilename(), expiration).toString();
        return url;
    }
}
