package com.base.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.base.http.ErrType;
import com.base.http.ResultMap;
import com.base.service.FileUploadService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final String basePath = "D:/Assets/haha";
    private static final String separator = "/";

    @Value("${server.domainName}")
    private String domainName;

    @Override
    public ResultMap singleUpload(MultipartFile file, String path) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            // 创建httpget.
            HttpPost httppost = new HttpPost("http://119.23.213.112:8080/testFileUpload/single");

            //setConnectTimeout：设置连接超时时间，单位毫秒。setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//            File file1 = new File(path);
//            FileUtils.copyInputStreamToFile(file.getInputStream(), file1);
            multipartEntityBuilder.addBinaryBody("file", file.getInputStream());
            org.apache.http.HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);

            // 执行post请求.
            CloseableHttpResponse response = httpclient.execute(httppost);

            try {
                System.out.println("get response");
                // 获取响应实体
                org.apache.http.HttpEntity entity = response.getEntity();
                //System.out.println("--------------------------------------");
                // 打印响应状态
                //System.out.println(response.getStatusLine());
                System.out.println(entity.toString());
                if (entity != null) {
                    JSONObject object = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                    System.out.println(object);
                    String data = object.getString("data");
                    return new ResultMap(ErrType.SUCCESS, data);
                }
                //System.out.println("------------------------------------");
            } finally {
                response.close();

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResultMap(ErrType.SUCCESS);


//        if (file.isEmpty()) {
//            BaseException.throwSystemException(BaseExceptionEnums.FILE_EMPTY);
//
//        }
//        File targetFile=null;
//        String url="";//返回存储路径
//        //获取文件夹路径
//        File file1 =new File(basePath);
//        //如果文件夹不存在则创建
//        if(!file1 .exists()  && !file1 .isDirectory()){
//            file1 .mkdirs();
//        }
//        //文件名字加上随机数
//        String fileName = file.getOriginalFilename();
//        int index = fileName.lastIndexOf(".");
//        fileName = fileName.substring(0, index) + RandomStringUtils.randomAlphabetic(6) + fileName.substring(index);
//        //将图片存入文件夹
//        targetFile = new File(file1, fileName);
//        try {
//            //将上传的文件写到服务器上指定的文件。
//            file.transferTo(targetFile);
//            url=basePath + "/" + fileName;
//            return new ResultMap(ErrType.SUCCESS, url);
//        } catch (Exception e) {
//            e.printStackTrace();
//            BaseException.throwSystemException(BaseExceptionEnums.FILE_UPLOAD_FAIL);
//        }


//        String url = StringUtils.EMPTY;
//        try {
//            url = FileUploadUtil.writeFile(file.getBytes(), String.format("%s/%s/", basePath, path),
//                    file.getOriginalFilename());
//            System.out.println("url" + url);
//
//        } catch (IOException e) {
//            BaseException.throwSystemException(BaseExceptionEnums.FILE_UPLOAD_FAIL);
//        }
//
//        return new ResultMap(ErrType.SUCCESS,getFullUrl(url));
    }

    @Override
    public String getFullUrl(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }

        return domainName + filePath;
    }
}
