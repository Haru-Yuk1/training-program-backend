package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.entity.StudentStatistics;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    // 后台数据获取操作
        // 获取学生数据
    @Select("select " +
    "sum(case when status=1 then 1 else 0 end) as ActivateNum," +
       "sum(case when isFinishSelect=1 then 1 else 0 end) as chooseNum," +
          "sum(case when dorStatus!=0 then 1 else 0 end) as dorFinishNum, " +
            "sum(case when isFinishLog=1 then 1 else 0 end) as logFinishNum, " +
                "sum(case when isFinishLog=1 && dorStatus=1 && isFinishSelect=1 then 1 else 0 end) as registerFinishNum, " +
                "count(*) as totalCount " + "from student")
    StudentStatistics findAllStatistics();

        // 统计申请住宿的人数
    @Select("SELECT COUNT(*) AS applyDorm FROM student WHERE dorStatus = 1")
    int countApplyDorm();

        // 统计不申请住宿的人数
    @Select("SELECT COUNT(*) AS notApplyDorm FROM student WHERE dorStatus = 2")
    int countNotApplyDorm();

        // 统计各班级完成注册人数
    @Select("SELECT classes, COUNT(*) AS registeredCount " +
            "FROM student " +
            "WHERE status = 1 AND isFinishSelect = 1 AND dorStatus IN (1, 2) AND isFinishLog = 1 " +
            "GROUP BY classes")
    List<Map<String, Object>> countRegisteredNumByClass();

        // 统计各专业实际人数
    @Select("SELECT major, COUNT(*) AS studentCount " +
            "FROM student " +
            "GROUP BY major")
    List<Map<String, Object>> countStudentsByMajor();

        // 统计各学院实际人数
    @Select("SELECT deptName, COUNT(*) AS studentCount " +
            "FROM student " +
            "GROUP BY deptName")
    List<Map<String, Object>> countStudentsByDept();

    // 后台学生操作
    // 注意插入的是姓名、民族、性别、生日、身份证号码、地址、专业、班级、学号、所属学院
    @Insert("INSERT INTO student (name, nation, gender, birthday, idCard, address, major, classes, studentid, deptName) " +
            "VALUES (#{name}, #{nation}, #{gender}, #{birthday}, #{idCard}, #{address}, #{major}, #{classes}, #{studentid}, #{deptName})")
    int insertStudent(Student student);

    @Delete("DELETE FROM student WHERE studentid = #{studentid}")
    int deleteStudent(@Param("studentid") String studentid);

    // 注意不能更新学生宿舍，只能更新学生的信息，可以更改学生密码
    @UpdateProvider(type = SqlProvider.class, method = "updateStudent")
    int updateStudent(@Param("student") Student student);

    class SqlProvider {
        public String updateStudent(@Param("student") Student student) {
            return new SQL() {{
                UPDATE("student");
                if (student.getName() != null && !student.getName().isEmpty()) {
                    SET("name = #{student.name}");
                }
                if (student.getNation() != null && !student.getNation().isEmpty()) {
                    SET("nation = #{student.nation}");
                }
                if (student.getGender() != null && !student.getGender().isEmpty()) {
                    SET("gender = #{student.gender}");
                }
                if (student.getBirthday() != null && !student.getBirthday().isEmpty()) {
                    SET("birthday = #{student.birthday}");
                }
                if (student.getPhone() != null && !student.getPhone().isEmpty()) {
                    SET("phone = #{student.phone}");
                }
                if (student.getEmail() != null && !student.getEmail().isEmpty()) {
                    SET("email = #{student.email}");
                }
                if (student.getIdCard() != null && !student.getIdCard().isEmpty()) {
                    SET("idCard = #{student.idCard}");
                }
                if (student.getAddress() != null && !student.getAddress().isEmpty()) {
                    SET("address = #{student.address}");
                }
                if (student.getMajor() != null && !student.getMajor().isEmpty()) {
                    SET("major = #{student.major}");
                }
                if (student.getClasses() != null && !student.getClasses().isEmpty()) {
                    SET("classes = #{student.classes}");
                }
                if (student.getStudentid() != null && !student.getStudentid().isEmpty()) {
                    SET("studentid = #{student.studentid}");
                }
                if (student.getPassword() != null && !student.getPassword().isEmpty()) {
                    SET("password = #{student.password}");
                }
                if (student.getDeptName() != null && !student.getDeptName().isEmpty()) {
                    SET("deptName = #{student.deptName}");
                }
                WHERE("studentid = #{student.originalStudentId}");
            }}.toString();
        }
    }
    //获取所有学生
    @Select("select * from student")
    List<Student> findAll();

    //查询除了密码外的其他信息
    @Select("select id, name, nation, gender, birthday, phone, email, idCard, address, major, classes, studentid, dorName, deptName, status, isFinishSelect, dorStatus, isFinishLog, imageUrl from student")
    List<Student> query();

    //通过学生名字获取学生信息
    @Select("select * from student where name=#{name}")
    Student findByName(String name);

    @Select("select classes, dorStatus, studentid, dorName, gender from student where dorStatus=1 and dorName IS NULL")
    List<Student> findForDormitory();

    @Select("select * from student where dorName=#{dorName}")
    List<Student> findAllByDorName();

    @Select("select dorName from student where studentid=#{studentid}")
    String findDorNameById(String studentid);

    @Update("update student set dorName = #{dorName} where studentid = #{studentid}")
    int updateDorNameById(@Param("studentid") String studentid, @Param("dorName") String dorName);

    @Update("update student set dorStatus = #{dorStatus} where studentid = #{studentid}")
    int updateDorStatusById(@Param("studentid") String studentid, @Param("dorStatus") int dorStatus);

    @Update("update student set preference = #{preference} where studentid = #{studentid}")
    int updatePreferenceById(@Param("studentid") String studentid, @Param("preference") String preference);
    // 登录操作

    //通过idCard找学生
    @Select("select * from student where idCard=#{idCard}")
    Student findByIdCard(@Param("idCard") String idCard);
    @Select("select * from student where studentid=#{studentid}")
    Student findByStudentid(String studentid);

    //通过idCard找到学生激活账户
    @Update("update student set password=#{password},email=#{email},status=#{status} where idCard=#{idCard}")
    int registerByEmail(@Param("idCard") String idCard,@Param("email") String email, @Param("password") String password, @Param("status") int status);

    //用手机注册

    //通过email找学生
    @Select("select * from student where email=#{email}")
    Student selectByEmail(String email);

    //通过phone找学生
    @Select("select * from student where phone=#{phone}")
    Student selectByPhone(String phone);

    @Update("update student set password=#{password},phone=#{phone},status=#{status} where idCard=#{idCard}")
    int registerByPhone(@Param("idCard") String idCard,@Param("phone") String phone, @Param("password") String password, @Param("status") int status);

    @Select("select * from student where phone=#{phone} and password=#{password}")
    Student loginByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from student where email=#{email} and password=#{password}")
    Student loginByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Select("select * from student where phone=#{phone}")
    Student loginByPhoneAndCode(@Param("phone") String phone);

    @Select("select * from student where email=#{email}")
    Student loginByEmailAndCode(@Param("email") String email);

    // 忘记密码
    //查询
    @Select("select * from student where phone=#{phone}")
    Student findByPhone(@Param("phone") String phone);

    @Select("select * from student where email=#{email}")
    Student findByEmail(@Param("email") String email);
    // 忘记密码 通过手机
    @Update("update student set password=#{password} where phone=#{phone}")
    int updatePasswordByPhone(@Param("phone") String phone, @Param("password") String password);

    //忘记密码 通过邮箱
    @Update("update student set password=#{password} where email=#{email}")
    int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    @Update("update student set imageUrl=#{imageUrl} where studentid=#{studentid}")
    int updateImageUrl(@Param("imageUrl") String imageUrl,String studentid);


    //更新学生表
    @Update("update student set email=#{email},address=#{address},isFinishLog=1 where idCard=#{idCard}")
    int updateStudentInfo(String email,String address,String idCard);

    //更新学生选课完成状态
    @Update("update student set isFinishSelect=1 where idCard=#{idCard}")
    int updateIsFinishSelect(String idCard);


}
