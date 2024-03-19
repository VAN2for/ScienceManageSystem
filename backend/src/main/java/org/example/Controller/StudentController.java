package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.StudentMapper;
import org.example.mapper.UserSystemMapper;
import org.example.pojo.Student;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class StudentController {
    @Autowired  //自动接线注解
    private StudentMapper studentMapper;//可以关联，找到数据，之后mapper扎到数据库

    @Autowired
    private UserSystemMapper userMapper;

    private Gson gson =new Gson();
    @GetMapping("/find")//路由，访问/find页面时，调用以下的函数
    public String test(){
        List<Student> studentList = studentMapper.selectList(null);  //查询
        return gson.toJson(studentList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/delete")
    public void deleteStudent(@RequestBody Student student) {
//        String student = "{\"id\":12,\"number\":\"112\",\"name\":\"小新\",\"age\":7,\"chi\":59,\"eng\":59,\"math\":59}";
//        Student student1 = gson.fromJson(student, Student.class);//从json变成实体类
        studentMapper.deleteById(student);  //传入的是实体参数
    }

    @PostMapping("/insert")
    public void insertStudent(@RequestBody Student student1){//RequestBody注解会自动把json改成实体类
        studentMapper.insert(student1);  //传入的是实体参数
    }

    @PostMapping("/update")
    public void updateStudent(@RequestBody Student student){

        studentMapper.updateById(student);  //传入的是实体参数
    }

}
