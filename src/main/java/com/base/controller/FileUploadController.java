package com.base.controller;//package com.watchtv.controller;

import com.base.http.ResultMap;
import com.base.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//
@Api("图片上传接口")
@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;


    @ApiOperation(value = "图片上传", notes = "图片上传", consumes = "application/json")
    @PostMapping(value = "/picture")
    public ResultMap uploadPicture(@RequestParam("file") MultipartFile file) {
        String path = "picture";
        return fileUploadService.singleUpload(file,path);
    }
}
