package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.mapper.FeedbackMapper;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "接受反馈")
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @ApiOperation("接受反馈")
    @PostMapping("/add")
    public Result getFeedback(@RequestParam  String content) {
        return feedbackMapper.addFeedback(content) == 1 ? Result.ok().message("接受反馈成功") : Result.error().message("接受反馈失败，插入反馈数量不等于1");
    }
}
