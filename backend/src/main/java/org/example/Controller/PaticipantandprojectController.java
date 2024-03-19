package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.PaticipantandprojectMapper;
import org.example.pojo.Paticipantandproject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class PaticipantandprojectController {
    @Autowired  //自动接线注解
    private PaticipantandprojectMapper paticipantandprojectMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findPaticipantAndProject")//路由，访问/find页面时，调用以下的函数
    public String findPaticipantAndProject(){
        List<Paticipantandproject> formList = paticipantandprojectMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deletePaticipantAndProject")
    public void deletePaticipantAndProject(@RequestBody Paticipantandproject paticipantandproject) {
        QueryWrapper<Paticipantandproject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", paticipantandproject.getProjectId())
                .eq("participant_name", paticipantandproject.getParticipantName());
        paticipantandprojectMapper.delete(queryWrapper);
    }

    @PostMapping("/insertPaticipantAndProject")
    public void insertPaticipantAndProject(@RequestBody Paticipantandproject paticipantandproject){//RequestBody注解会自动把json改成实体类
        paticipantandprojectMapper.insert(paticipantandproject);  //传入的是实体参数
    }

    @PostMapping("/updatePaticipantAndProject")
    public void updatePaticipantAndProject(@RequestBody Paticipantandproject paticipantandproject){
        QueryWrapper<Paticipantandproject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", paticipantandproject.getProjectId());
        paticipantandprojectMapper.update(paticipantandproject,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }

    @GetMapping("/getPaticipantName")
    public String getPaticipantName() {
        List<Paticipantandproject> FormList = paticipantandprojectMapper.selectList(
                new QueryWrapper<Paticipantandproject>().select("participant_name")
        );

        List<String> nameList = new ArrayList<>();
        for (Paticipantandproject paticipantandproject : FormList) {
            nameList.add(paticipantandproject.getParticipantName());
        }

        return gson.toJson(nameList);
    }

    @GetMapping("/getProjectId")
    public String getProjectId() {
        List<Paticipantandproject> FormList = paticipantandprojectMapper.selectList(
                new QueryWrapper<Paticipantandproject>().select("project_id")
        );

        List<String> nameList = new ArrayList<>();
        for (Paticipantandproject paticipantandproject : FormList) {
            nameList.add(paticipantandproject.getProjectId());
        }

        return gson.toJson(nameList);
    }

}




