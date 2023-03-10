package com.aaa.group_three.controller;

import com.aaa.util.Result;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * @Author: 江Sir
 * @Date: 2023/02/09 17:37:50
 * @Description:
 * @Since version:  2022.10
 */
@RestController
@RequestMapping("file")
public class Sso {
    @Value("${com.aliyun.AccessKey_ID}")
    private String AccessKey_ID;
    @Value("${com.aliyun.AccessKey_Secret}")
    private String AccessKey_Secret;

    @Value("${com.aliyun.endPoint}")
    private String endPoint;
    @Value("${com.aliyun.bucketName}")
    private String bucketName;
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        //获取原始文件名
        String filename = file.getOriginalFilename();
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endPoint = this.endPoint;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = this.AccessKey_ID;
        String accessKeySecret = this.AccessKey_Secret;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = this.bucketName;
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String suffix = filename.substring(filename.lastIndexOf("."));
        String objectName = UUID.randomUUID() + filename;
//        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        String src = "https://" + bucketName + "." + endPoint + "/" + objectName;
        Map map = new HashMap();
        map.put("src", src);
        try {
            // 上传字符串。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (Exception ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return new Result(src);
    }
}
