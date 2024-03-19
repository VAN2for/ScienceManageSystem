package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.ResearchProjectMapper;
import org.example.pojo.ResearchProject;
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
public class ResearchProjectController {
    @Autowired  //自动接线注解
    private ResearchProjectMapper researchProjectMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findResearchProject")//路由，访问/find页面时，调用以下的函数
    public String findResearchProject(){
        List<ResearchProject> formList = researchProjectMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteResearchProject")
    public void deleteResearchProject(@RequestBody ResearchProject researchProject) {
        QueryWrapper<ResearchProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_no",researchProject.getProjectNo());
        researchProjectMapper.delete(queryWrapper);
    }

    @PostMapping("/insertResearchProject")
    public void insertResearchProject(@RequestBody ResearchProject researchProject){//RequestBody注解会自动把json改成实体类
        researchProjectMapper.insert(researchProject);  //传入的是实体参数
    }

    @PostMapping("/updateResearchProject")
    public void updateResearchProject(@RequestBody ResearchProject researchProject){
        QueryWrapper<ResearchProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_no",researchProject.getProjectNo());//转换SQL语言
        researchProjectMapper.update(researchProject,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }

    @GetMapping("/findResearchProjectName")
    public String findResearchProjectName() {
        List<ResearchProject> FormList = researchProjectMapper.selectList(
                new QueryWrapper<ResearchProject>().select("project_no")
        );

        List<String> nameList = new ArrayList<>();
        for (ResearchProject researchProject : FormList) {
            nameList.add(researchProject.getProjectNo());
        }

        return gson.toJson(nameList);
    }

    @PostMapping("/searchProjectByName")
    public String searchProjectByName(@RequestBody HashMap<String,String> data) {
        String name=data.get("name");
        QueryWrapper<ResearchProject> queryWrapper = new QueryWrapper<>();//包装类，包装SQL语句
        queryWrapper.like("project_name",name);//select * from secretary where name like '%小%'
        List<ResearchProject> secretaryList = researchProjectMapper.selectList(queryWrapper);//映射到数据库，开始执行queryWrapper内语句，这里会返回结果
        return gson.toJson(secretaryList);
    }

}




