package com.base.service;

import com.base.http.ResultMap;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    /**
     * 单文件上传
     *
     * @param file 要上传的文件
     * @return 文件保存的路劲
     */
    ResultMap singleUpload(MultipartFile file, String path);

    /**
     * 获取可直接访问的url
     */
    String getFullUrl(String filePath);
}
