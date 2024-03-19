package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.SecretaryMapper;
import org.example.mapper.StudentMapper;
import org.example.mapper.UserSystemMapper;
import org.example.pojo.Secretary;
import org.example.pojo.Student;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class SecretaryController {
    @Autowired  //自动接线注解
    private SecretaryMapper secretaryMapper;//可以关联，找到数据，之后mapper扎到数据库

    @Autowired
    private UserSystemMapper userMapper;

    private Gson gson =new Gson();
    @GetMapping("/findSecretary")//路由，访问/find页面时，调用以下的函数
    public String test(){
        List<Secretary> secretaryList = secretaryMapper.selectList(null);  //查询
        return gson.toJson(secretaryList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteSecretary")
    public void deleteSecretary(@RequestBody Secretary secretary) {
        System.out.println("cuowu");
        QueryWrapper<Secretary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("secretary_no",secretary.getSecretaryNo());
        secretaryMapper.delete(queryWrapper);
//        secretaryMapper.deleteById(secretary);  //传入的是实体参数
    }

    @PostMapping("/insertSecretary")
    public void insertSecretary(@RequestBody Secretary secretary){//RequestBody注解会自动把json改成实体类
        secretaryMapper.insert(secretary);  //传入的是实体参数
    }

    @PostMapping("/updateSecretary")
    public void updateSecretary(@RequestBody Secretary secretary){
        QueryWrapper<Secretary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("secretary_no",secretary.getSecretaryNo());
        secretaryMapper.update(secretary,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }


    @GetMapping("/findSecretaryName")
    public String findSecretaryName() {
        List<Secretary> secretaryList = secretaryMapper.selectList(
                new QueryWrapper<Secretary>().select("name")
        );

        List<String> nameList = new ArrayList<>();
        for (Secretary secretary : secretaryList) {
            nameList.add(secretary.getName());
        }

        return gson.toJson(nameList);
    }


    @PostMapping("/searchSecretaryByName")
    public String searchSecretaryByName(@RequestBody HashMap<String,String> data) {
        String name=data.get("name");
        QueryWrapper<Secretary> queryWrapper = new QueryWrapper<>();//包装类，包装SQL语句
        queryWrapper.like("name",name);//select * from secretary where name like '%小%'
        List<Secretary> secretaryList = secretaryMapper.selectList(queryWrapper);//映射到数据库，开始执行queryWrapper内语句，这里会返回结果
        return gson.toJson(secretaryList);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user){
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.setEntity(user);
        User user_select= userMapper.selectOne(userQueryWrapper);
        if(user_select==null){
            return "0";
        }
        return "1";
    }

    @PostMapping("/register")
    public void register(@RequestBody User user){
        userMapper.insert(user);
    }

}

