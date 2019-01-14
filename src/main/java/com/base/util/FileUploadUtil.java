package com.base.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUploadUtil {

    public static JSONObject success(String url) {

        JSONObject urlObject = new JSONObject();
        urlObject.put("url", url);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("msg", "success");
        jsonObject.put("data", urlObject);

        return jsonObject;
    }

    public static JSONObject fail() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "500");
        jsonObject.put("msg", "文件上传失败");

        return jsonObject;
    }

    public static String writeFile(byte[] bytes, String filePath, String fileName) throws IOException {
        //文件名字加上随机数
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index) + RandomStringUtils.randomAlphabetic(6) + fileName.substring(index);

        String filePathName = filePath + fileName;
        Path target = Paths.get(filePathName);

        getPath(filePath);

        if (!Files.exists(target)) {
            Files.createFile(target);
        }

        Files.write(target, bytes);

        return filePathName;
    }

    private static void getPath(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

}