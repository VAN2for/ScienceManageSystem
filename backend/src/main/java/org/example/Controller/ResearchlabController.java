package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.ResearchlabMapper;
import org.example.pojo.Researchlab;
import org.example.pojo.Secretary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class ResearchlabController {
    @Autowired  //自动接线注解
    private ResearchlabMapper researchlabMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findResearchLab")//路由，访问/find页面时，调用以下的函数
    public String findResearchLab(){
        List<Researchlab> formList = researchlabMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteResearchLab")
    public void deleteResearchLab(@RequestBody Researchlab researchlab) {
        QueryWrapper<Researchlab> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lab_name",researchlab.getLabName());
        researchlabMapper.delete(queryWrapper);
    }

    @PostMapping("/insertResearchLab")
    public void insertDirector(@RequestBody Researchlab researchlab){//RequestBody注解会自动把json改成实体类
        researchlabMapper.insert(researchlab);  //传入的是实体参数
    }

    @PostMapping("/updateResearchLab")
    public void updateResearchLab(@RequestBody Researchlab researchlab){
        QueryWrapper<Researchlab> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lab_name",researchlab.getLabName());
        researchlabMapper.update(researchlab,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }


    @GetMapping("/findResearchLabName")
    public String findDirectorName() {
        List<Researchlab> FormList = researchlabMapper.selectList(
                new QueryWrapper<Researchlab>().select("lab_name")
        );

        List<String> nameList = new ArrayList<>();
        for (Researchlab researchlab : FormList) {
            nameList.add(researchlab.getLabName());
        }

        return gson.toJson(nameList);
    }
    @PostMapping("/searchLabByName")
    public String searchSecretaryByName(@RequestBody HashMap<String,String> data) {
        String name=data.get("name");
        QueryWrapper<Researchlab> queryWrapper = new QueryWrapper<>();//包装类，包装SQL语句
        queryWrapper.like("lab_name",name);//select * from secretary where name like '%小%'
        List<Researchlab> labList = researchlabMapper.selectList(queryWrapper);//映射到数据库，开始执行queryWrapper内语句，这里会返回结果
        return gson.toJson(labList);
    }


}


