package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.SubProjectMapper;
import org.example.pojo.SubProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class SubProjectController {
    @Autowired  //自动接线注解
    private SubProjectMapper subProjectMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findSubProject")//路由，访问/find页面时，调用以下的函数
    public String findSubProject(){
        List<SubProject> formList = subProjectMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteSubProject")
    public void deleteSubProject(@RequestBody SubProject subProject) {
        QueryWrapper<SubProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subproject_id",subProject.getSubprojectId());
        subProjectMapper.delete(queryWrapper);
    }

    @PostMapping("/insertSubProject")
    public void insertSubProject(@RequestBody SubProject subProject){//RequestBody注解会自动把json改成实体类
        subProjectMapper.insert(subProject);  //传入的是实体参数
    }

    @PostMapping("/updateSubProject")
    public void updateSubProject(@RequestBody SubProject subProject){
        QueryWrapper<SubProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subproject_id",subProject.getSubprojectId());//转换SQL语言
        subProjectMapper.update(subProject,queryWrapper);
    }



}




