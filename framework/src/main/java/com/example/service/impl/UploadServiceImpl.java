package com.example.service.impl;

import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.service.UploadService;
import com.example.utils.PathUtils;
import com.example.vo.ResponseResult;

import com.google.gson.Gson;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@Data
@ConfigurationProperties(prefix ="oss" )
public class UploadServiceImpl implements UploadService {

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        if (!(originalFilename.endsWith(".png")||originalFilename.endsWith(".jpg")
        ||originalFilename.endsWith(".JPG")||originalFilename.endsWith(".PNG"))){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //通过上传到oss
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url=uploadOss(img,filePath);


        return ResponseResult.okResult(url);
    }


    private String accessKey;
    private String secretKey;
    private String bucket;
    private String httpDir;
    private String uploadOss(MultipartFile imgFile, String filePath) {
        try {
            // 初始化用户身份信息(secretId, secretKey)
            COSCredentials cred = new BasicCOSCredentials(accessKey,secretKey);
            // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
            ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
            // 生成cos客户端
            COSClient cosClient = new COSClient(cred, clientConfig);

            String bucketName = bucket;
            String key = filePath;
            File temp=File.createTempFile("temp",null);;
            imgFile.transferTo(temp);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, temp);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return httpDir+key;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
