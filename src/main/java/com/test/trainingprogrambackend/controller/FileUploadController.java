package com.test.trainingprogrambackend.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@CrossOrigin(maxAge = 30000)
@Api(tags = "文件上传")
public class FileUploadController {


    @Value("${upload.path}")
    private String uploadPath;

    @ApiOperation("上传图片")
    @PostMapping("/upload/image")
    public String upload(MultipartFile image)throws IOException {
        // 获取图片的原始名称
        System.out.println(image.getOriginalFilename());
        //设置图片新名称
        String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        //String imageName = image.getOriginalFilename();
        System.out.println(imageName);
        //设置文件
        File file = new File(uploadPath+imageName);
        image.transferTo(file);
        System.out.println(uploadPath+imageName);
        //图片后端地址
        String path = "/images/"+imageName;

        System.out.println(path);
        return path;

    }
    @ApiOperation("获取上传的图片")
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        File file = new File(uploadPath + imageName);
        Resource resource = new UrlResource(file.toURI());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
