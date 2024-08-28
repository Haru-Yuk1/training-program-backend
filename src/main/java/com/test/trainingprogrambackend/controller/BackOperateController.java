package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.DormitoryService;
import com.test.trainingprogrambackend.entity.*;
import com.test.trainingprogrambackend.mapper.*;
import com.test.trainingprogrambackend.util.JwtUtils;
import com.test.trainingprogrambackend.util.MD5Util;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "后台管理系统消息管理、学生管理、用户管理操作")
@RequestMapping("/backOperate")
public class BackOperateController {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TakesMapper takesMapper;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private DormitoryService dormitoryService;

    //消息操作
    @ApiOperation("注意不需要传送 date 和 id，传递 title 和 content")
    @PostMapping("/message/insert")
    public Result insertMessage(@RequestBody Message message, @RequestHeader("Authorization") String token){
        String userid = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userid);
        if((user.getRole().equals("通知管理员") || user.getRole().equals("系统管理员"))&& user.getStatus().equals("正常")){
            //设置发布日期为当前系统日期
            Date date = new Date();
            message.setPublishDate(date);
            return messageMapper.insert(message) == 1 ? Result.ok().message("插入成功") : Result.error().message("插入失败，插入消息数量不等于1");
        }
        else{
            return Result.error().message("用户不是通知或系统管理员或用户状态不是正常");
        }
    }


    @ApiOperation("传递 title 即可删除")
    @PostMapping("/message/delete")
    public Result deleteMessage(@RequestParam String title, @RequestHeader("Authorization") String token){
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        if((user.getRole().equals("通知管理员") || user.getRole().equals("系统管理员")) && user.getStatus().equals("正常")){
            return messageMapper.delete(title) == 1 ? Result.ok().message("删除成功") : Result.error().message("删除失败，删除消息数量不等于1");
        }
        else{
            return Result.error().message("用户不是通知或系统管理员或用户状态不是正常");
        }
    }

    @ApiOperation("不需要 id，传递新标题的 title 和 content 以及 原标题的 originalTitle")
    @PostMapping("/message/update")
    public Result updateMessage(@RequestBody Message message, @RequestHeader("Authorization") String token){
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        if((user.getRole().equals("通知管理员") || user.getRole().equals("系统管理员")) && user.getStatus().equals("正常")){
            //设置发布日期为当前系统日期
            Date date = new Date();
            message.setPublishDate(date);
            return messageMapper.update(message) == 1 ? Result.ok().message("更新成功") : Result.error().message("更新失败，更新消息数量不等于1");
        }
        else{
            return Result.error().message("用户不是通知或系统管理员或用户状态不是正常");
        }
    }

    @ApiOperation("查询所有消息")
    @GetMapping("/message/query")
    public List<Message> queryMessage(){
        return messageMapper.query();
    }

    //学生操作
    @ApiOperation("查询所有学生所有信息")
    @GetMapping("/student/query")
    public List<Student> queryStudent(){
        List<Student> students=studentMapper.findAll();
        for(Student s:students){
            s.setPassword(MD5Util.encrypt(s.getPassword()));
        }
        return students;

    }

    @ApiOperation("查看学生密码")
    @GetMapping("/student/queryPassword")
    public boolean queryStudentPassword(@RequestHeader("Authorization") String token){
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        return (user.getRole().equals("学生管理员") || user.getRole().equals("系统管理员")) && user.getStatus().equals("正常");
    }

    @ApiOperation("传递 studentid 进行删除")
    @PostMapping("/student/delete")
    public Result deleteStudent(@RequestParam String studentid, @RequestHeader("Authorization") String token){
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        if((user.getRole().equals("学生管理员") || user.getRole().equals("系统管理员"))&& user.getStatus().equals("正常"))
            return studentMapper.deleteStudent(studentid) == 1 ? Result.ok().message("删除成功") : Result.error().message("删除失败，删除学生数量不等于1");
        else
            return Result.error().message("用户不是学生或系统管理员或用户状态不是正常");
    }

    @ApiOperation("不需要 id，可以更新学生的信息包括插入所需要的信息和电话、邮箱、密码，另外需要传入原学生的 originalStudentid")
    @PostMapping("/student/update")
    public Result updateStudent(@RequestBody Student student, @RequestHeader("Authorization") String token){
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        if((user.getRole().equals("学生管理员") || user.getRole().equals("系统管理员"))&& user.getStatus().equals("正常"))
            return studentMapper.updateStudent(student) == 1 ? Result.ok().message("更新成功") : Result.error().message("更新失败，更新学生数量不等于1");
        else
            return Result.error().message("用户不是学生或系统管理员或用户状态不是正常");
    }

    @ApiOperation("注意不需要传送 id，传递新学生的所有信息（包括姓名、民族、性别、生日、身份证号码、地址、专业、班级、学号、所属学院）")
    @PostMapping("/student/insert")
    public Result insertStudent(@RequestBody Student student, @RequestHeader("Authorization") String token){
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        if((user.getRole().equals("学生管理员") || user.getRole().equals("系统管理员")) && user.getStatus().equals("正常"))
            return studentMapper.insertStudent(student) == 1 ? Result.ok().message("插入成功") : Result.error().message("插入失败，插入学生数量不等于1");
        else
            return Result.error().message("用户不是学生或系统管理员或用户状态不是正常");
    }

    //用户操作
    @ApiOperation("查询所有用户")
    @GetMapping("/user/query")
    public List<User> queryUser(){
        return userMapper.query();
    }

    @ApiOperation("传递 userId 进行删除")
    @PostMapping("/user/delete")
    public Result deleteUser(@RequestParam String userId, @RequestHeader("Authorization") String token){
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if(userToken.getRole().equals("系统管理员") && userToken.getStatus().equals("正常"))
            return userMapper.delete(userId) == 1 ? Result.ok().message("删除成功") : Result.error().message("删除失败，删除用户数量不等于1");
        else
            return Result.error().message("用户不是系统管理员或用户状态不是正常");
    }

    @ApiOperation("不需要 id，传递新用户的所有信息以及原用户的 originalUserId")
    @PostMapping("/user/update")
    public Result updateUser(@RequestBody User user, @RequestHeader("Authorization") String token){
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if(userToken.getRole().equals("系统管理员") && userToken.getStatus().equals("正常")){
            Date date = new Date();
            user.setCreateDate(date);
            return userMapper.update(user) == 1 ? Result.ok().message("更新成功") : Result.error().message("更新失败，更新用户数量不等于1");
        }
        else{
            return Result.error().message("用户不是系统管理员或用户状态不是正常");
        }
    }

    @ApiOperation("注意不需要传送 id 和 date")
    @PostMapping("/user/insert")
    public Result insertUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if (userToken.getRole().equals("系统管理员") && userToken.getStatus().equals("正常")) {
            Date date = new Date();
            user.setCreateDate(date);
            return userMapper.insert(user) == 1 ? Result.ok().message("插入成功") : Result.error().message("插入失败，插入用户数量不等于1");
        }
        else {
            return Result.error().message("用户不是系统管理员或用户状态不是正常");
        }
    }


    //登录操作
    @ApiOperation("管理员登录")
    @PostMapping("/loginUser")
    public Result loginUser(@RequestParam String userId,@RequestParam String password) {
        String encryptedPassword= MD5Util.encrypt(password);


        int isFind=userMapper.selectByUserid(userId);
        if(isFind==0){
            return Result.error().message("未找到该用户");
        }
        User user=userMapper.LoginUser(userId,encryptedPassword);
        if (user==null){
            return Result.error().message("密码错误");
        }

        String token = JwtUtils.generateToken(userId);

        return Result.ok().data("token",token).message("登录成功");
    }


    //课程操作
    @ApiOperation("增加课程")
    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody Course course, @RequestHeader("Authorization") String token) {
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if((userToken.getRole().equals("系统管理员") || userToken.getRole().equals("课程管理员")) && userToken.getStatus().equals("正常")){
            Department department=takesMapper.departByDeptName(course.getDeptName());
            if(department==null){
                return Result.error().message("未找到对应学院");
            }
            int success=takesMapper.insertCourse(course.getCode(),course.getName(),course.getCredit(), course.getType(), course.getDeptName());
            if (success==1) {
                return Result.ok().message("课程增加成功");
            }
            return Result.error().message("课程增加失败");
        }
        else{
            return Result.error().message("用户不是课程或系统管理员或用户状态不是正常");
        }
    }

    @ApiOperation("删除课程")
    @PostMapping("/deleteCourse")
    public Result deleteCourse(@RequestBody Course course, @RequestHeader("Authorization") String token) {
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if((userToken.getRole().equals("系统管理员") || userToken.getRole().equals("课程管理员")) && userToken.getStatus().equals("正常")){
            if (takesMapper.courseByCode(course.getCode())==null){
                return Result.error().message("未找到该课程");
            }
            int success=takesMapper.deleteCourse(course.getCode());
            if (success==1) {
                return Result.ok().message("课程删除成功");
            }
            return Result.error().message("课程删除失败");
        }
        else{
            return Result.error().message("用户不是课程或系统管理员或用户状态不是正常");
        }
    }

    @ApiOperation("更改课程")
    @PostMapping("/updateCourse")
    public Result updateCourse(@RequestBody Course course, @RequestHeader("Authorization") String token) {
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if((userToken.getRole().equals("系统管理员") || userToken.getRole().equals("课程管理员")) && userToken.getStatus().equals("正常")){
            if (takesMapper.courseByCode(course.getCode())==null){
                return Result.error().message("未找到该课程");
            }
            if(takesMapper.departByDeptName(course.getDeptName())==null){
                return Result.error().message("未找到对应学院");
            }
            int success=takesMapper.updateCourse(course.getCode(), course.getName(),course.getCredit(), course.getType(),course.getDeptName());
            if (success==1) {
                return Result.ok().message("更新成功");
            }
            return Result.error().message("更新失败");
        }
        else{
            return Result.error().message("用户不是课程或系统管理员或用户状态不是正常");
        }
    }


    @ApiOperation("增加课程班级")
    @PostMapping("/addCourseClass")
    public Result addCourseClass(@RequestBody CourseClass courseClass, @RequestHeader("Authorization") String token) {
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if((userToken.getRole().equals("系统管理员") || userToken.getRole().equals("课程管理员")) && userToken.getStatus().equals("正常")){
            if (takesMapper.courseByCode(courseClass.getCode())==null){
                return Result.error().message("错误课程编号");
            }
            if(takesMapper.findClassNumber(courseClass.getClassNumber())!=null){
                return Result.error().message("已有该班级");
            }
            int success=takesMapper.insertCourseClass(courseClass.getClassNumber(),courseClass.getTeacherName(),
                    courseClass.getCapacity(),courseClass.getSelectedNumber(),courseClass.getIsFull(),courseClass.getCode());
            if (success==1) {
                return Result.ok().message("课程班级增加成功");
            }
            return Result.error().message("课程班级增加失败");
        }
        else{
            return Result.error().message("用户不是课程或系统管理员或用户状态不是正常");
        }
    }

    @ApiOperation("删除课程班级")
    @PostMapping("/deleteCourseClass")
    public Result deleteCourseClass(@RequestBody CourseClass courseClass, @RequestHeader("Authorization") String token) {
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if((userToken.getRole().equals("系统管理员") || userToken.getRole().equals("课程管理员")) && userToken.getStatus().equals("正常")){
            if(takesMapper.findClassNumber(courseClass.getClassNumber())==null){
                return Result.error().message("未找到课程班级");
            }
            int success=takesMapper.deleteCourseClass(courseClass.getClassNumber());
            if (success==1) {
                return Result.ok().message("课程班级删除成功");
            }
            return Result.error().message("课程班级删除失败");
        }
        else{
            return Result.error().message("用户不是课程或系统管理员或用户状态不是正常");
        }
    }

    @ApiOperation("更改课程班级")
    @PostMapping("/updateCourseClass")
    public Result updateCourseClass(@RequestBody CourseClass courseClass, @RequestHeader("Authorization") String token) {
        String userIdToken = JwtUtils.getClaimsByToken(token).getSubject();
        User userToken = userMapper.queryRoleAndStatus(userIdToken);
        if((userToken.getRole().equals("系统管理员") || userToken.getRole().equals("课程管理员")) && userToken.getStatus().equals("正常")){
            if(takesMapper.findClassNumber(courseClass.getClassNumber())==null){
                return Result.error().message("未找到课程班级");
            }
            if (takesMapper.courseByCode(courseClass.getCode())==null){
                return Result.error().message("错误课程编号");
            }
            int success=takesMapper.updateByclassNumber(courseClass.getTeacherName(),courseClass.getCapacity(),courseClass.getSelectedNumber(),courseClass.getIsFull(),courseClass.getClassNumber());
            if (success==1) {
                return Result.ok().message("更改成功");
            }
            return Result.error().message("更改失败");
        }
        else{
            return Result.error().message("用户不是课程或系统管理员或用户状态不是正常");
        }
    }

    //宿舍操作
    // 后台管理系统接口
    @ApiOperation("后台分配宿舍并返回分配结果")
    @GetMapping("/dor/assignDor")
    public Result assignDor(@RequestHeader("Authorization") String token){
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        if((user.getRole().equals("宿舍管理员") || user.getRole().equals("系统管理员")) && user.getStatus().equals("正常")){
            List<Dormitory> list = dormitoryService.assignDor();
            if(list != null){
                return Result.ok().data("宿舍信息", list).message("分配成功");
            }
            else{
                return Result.error().message("分配失败，宿舍信息为空");
            }
        }
        else{
            return Result.error().message("用户不是宿舍或系统管理员，或者用户状态不是正常");
        }
    }

    @ApiOperation("后台获取对应班级未满宿舍信息")
    @GetMapping("/dor/getNotFullDorByClasses")
    public List<Dormitory> getNotFullDorByClasses(String classes){
        return dormitoryService.findNotFullDorByClasses(classes);
    }

    @ApiOperation("后台刷新宿舍信息")
    @GetMapping("/dor/refreshDor")
    public List<Dormitory> refreshDor(){
        return dormitoryService.refreshDor();
    }

    @ApiOperation("后台调整学生至非满宿舍")
    @PostMapping("/dor/adjustByNotFullDor")
    public Result adjustByNotNullDor(@RequestParam String studentid, @RequestParam String dorName, @RequestHeader("Authorization") String token){
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        if((user.getRole().equals("宿舍管理员") || user.getRole().equals("系统管理员")) && user.getStatus().equals("正常")){
            return dormitoryService.adjustDorByNotFullDor(studentid, dorName) == 1 ? Result.ok().message("调整成功") : Result.error().message("调整失败");
        }
        else{
            return Result.error().message("用户不是宿舍或系统管理员，或者用户状态不是正常");
        }
    }


    @ApiOperation("后台交换学生宿舍")
    @PostMapping("/dor/adjustByExchange")
    public Result adjustByExchange(@RequestParam String studentid1, @RequestParam String studentid2, @RequestHeader("Authorization") String token) {
        String userId = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.queryRoleAndStatus(userId);
        if((user.getRole().equals("宿舍管理员") || user.getRole().equals("系统管理员")) && user.getStatus().equals("正常")){
            return dormitoryService.adjustDorByExchange(studentid1, studentid2) == 1 ? Result.ok().message("交换成功") : Result.error().message("交换失败");
        }
        else{
            return Result.error().message("用户不是宿舍或系统管理员，或者用户状态不是正常");
        }
    }

    //呈现反馈
    @ApiOperation("显示学生客户端传来的反馈")
    @GetMapping("/showFeedback")
    public List<Feedback> showFeedback() {
        return feedbackMapper.getAllFeedbacks();
    }
}
