package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.ResearcherMapper;
import org.example.pojo.Researcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class ResearcherController {
    @Autowired  //自动接线注解
    private ResearcherMapper researcherMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findResearcher")//路由，访问/find页面时，调用以下的函数
    public String findResearcher(){
        List<Researcher> formList = researcherMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteResearcher")
    public void deleteResearcher(@RequestBody Researcher researcher) {
        QueryWrapper<Researcher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("researcher_no",researcher.getResearcherNo());
        researcherMapper.delete(queryWrapper);
    }

    @PostMapping("/insertResearcher")
    public void insertResearcher(@RequestBody Researcher director){//RequestBody注解会自动把json改成实体类
        researcherMapper.insert(director);  //传入的是实体参数
    }

    @PostMapping("/updateResearcher")
    public void updateResearcher(@RequestBody Researcher researcher){
        QueryWrapper<Researcher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("researcher_no",researcher.getResearcherNo());//转换SQL语言
        researcherMapper.update(researcher,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }


    @GetMapping("/findResearcherName")
    public String findResearcherName() {
        List<Researcher> researcherList = researcherMapper.selectList(
                new QueryWrapper<Researcher>().select("name")
        );

        List<String> nameList = new ArrayList<>();
        for (Researcher researcher : researcherList) {
            nameList.add(researcher.getName());
        }
        return gson.toJson(nameList);
    }


}

