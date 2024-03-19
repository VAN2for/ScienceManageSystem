package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.ProjectpaticipantMapper;
import org.example.pojo.Projectpaticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class ProjectPaticipantController {
    @Autowired  //自动接线注解
    private ProjectpaticipantMapper projectpaticipantMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findPaticipant")//路由，访问/find页面时，调用以下的函数
    public String findPaticipant(){
        List<Projectpaticipant> formList = projectpaticipantMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deletePaticipant")
    public void deletePaticipant(@RequestBody Projectpaticipant projectpaticipant) {
        QueryWrapper<Projectpaticipant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paticipant_name",projectpaticipant.getPaticipantName());
        projectpaticipantMapper.delete(queryWrapper);
    }

    @PostMapping("/insertPaticipant")
    public void insertPaticipant(@RequestBody Projectpaticipant projectpaticipant){//RequestBody注解会自动把json改成实体类
        projectpaticipantMapper.insert(projectpaticipant);  //传入的是实体参数
    }

    @PostMapping("/updatePaticipant")
    public void updatePaticipant(@RequestBody Projectpaticipant projectpaticipant){
        QueryWrapper<Projectpaticipant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paticipant_name",projectpaticipant.getPaticipantName());//转换SQL语言
        projectpaticipantMapper.update(projectpaticipant,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }

    @GetMapping("/findPaticipantName")
    public String findPaticipantName() {
        List<Projectpaticipant> FormList = projectpaticipantMapper.selectList(
                new QueryWrapper<Projectpaticipant>().select("paticipant_name")
        );

        List<String> nameList = new ArrayList<>();
        for (Projectpaticipant projectpaticipant : FormList) {
            nameList.add(projectpaticipant.getPaticipantName());
        }

        return gson.toJson(nameList);
    }

}




