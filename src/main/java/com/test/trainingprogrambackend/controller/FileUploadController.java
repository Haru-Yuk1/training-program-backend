package com.test.trainingprogrambackend.controller;


import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import com.test.trainingprogrambackend.util.JwtUtils;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StudentMapper studentMapper;

    @ApiOperation("上传图片")
    @PostMapping("/upload/image")
    public Result upload(MultipartFile image, @RequestHeader("Authorization") String token)throws IOException {
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

        //获取token
        String StudentIdCard= JwtUtils.getClaimsByToken(token).getSubject();
        Student student=studentMapper.findByIdCard(StudentIdCard);

        int success =studentMapper.updateImageUrl(path, student.getStudentid());
        if(success==1){
            return Result.ok().data("path",path).message("图片上传成功");
        }
        return Result.error().message("图片上传失败");


    }
    @ApiOperation("获取上传的图片")
    @GetMapping("/images/{imageName}")
    public Result getImage(@PathVariable String imageName) throws IOException {
        File file = new File(uploadPath + imageName);
        Resource resource = new UrlResource(file.toURI());

        return Result.ok().data("resource",resource).message("上传图片成功");
    }
}
