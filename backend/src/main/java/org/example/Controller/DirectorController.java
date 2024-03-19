package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.DirectorMapper;
import org.example.pojo.Director;
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
public class DirectorController {
    @Autowired  //自动接线注解
    private DirectorMapper directorMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findDirector")//路由，访问/find页面时，调用以下的函数
    public String test(){
        List<Director> secretaryList = directorMapper.selectList(null);  //查询
        return gson.toJson(secretaryList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteDirector")
    public void deleteDirector(@RequestBody Director director) {
        QueryWrapper<Director> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("director_no",director.getDirectorNo());
        directorMapper.delete(queryWrapper);
    }

    @PostMapping("/insertDirector")
    public void insertDirector(@RequestBody Director director){//RequestBody注解会自动把json改成实体类
        directorMapper.insert(director);  //传入的是实体参数
    }

    @PostMapping("/updateDirector")
    public void updateDirector(@RequestBody Director director){
        QueryWrapper<Director> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("director_no",director.getDirectorNo());
        directorMapper.update(director,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }


    @GetMapping("/findDirectorName")
    public String findDirectorName() {
        List<Director> directorList = directorMapper.selectList(
                new QueryWrapper<Director>().select("director_no")
        );

        List<String> nameList = new ArrayList<>();
        for (Director director : directorList) {
            nameList.add(director.getDirectorNo());
        }

        return gson.toJson(nameList);
    }

    @PostMapping("/searchDirectorByName")
    public String searchDirectorByName(@RequestBody HashMap<String,String> data) {
        String name=data.get("name");
        QueryWrapper<Director> queryWrapper = new QueryWrapper<>();//包装类，包装SQL语句
        queryWrapper.like("director_no",name);//select * from secretary where name like '%小%'
        List<Director> secretaryList = directorMapper.selectList(queryWrapper);//映射到数据库，开始执行queryWrapper内语句，这里会返回结果
        return gson.toJson(secretaryList);
    }
}

