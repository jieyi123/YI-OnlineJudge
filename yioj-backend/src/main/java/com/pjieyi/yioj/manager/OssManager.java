package com.pjieyi.yioj.manager;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.pjieyi.yioj.config.OssClientConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * Cos 对象存储操作
 *
 */
@Component
public class OssManager {

    @Resource
    private OssClientConfig ossClientConfig;

    @Resource
    private OSS ossClient;

    /**
     * 上传对象
     *
     * @param filePath 文件路径
     * @param inputStream 输入流
     * @return
     */
    public PutObjectResult putObject(String filePath, InputStream inputStream) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossClientConfig.getBucket(), filePath, inputStream);
        return ossClient.putObject(putObjectRequest);
    }

}
