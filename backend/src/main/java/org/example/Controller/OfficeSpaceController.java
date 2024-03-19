package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.OfficeSpaceMapper;
import org.example.pojo.OfficeSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class OfficeSpaceController {
    @Autowired  //自动接线注解
    private OfficeSpaceMapper officeSpaceMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findOfficeSpace")//路由，访问/find页面时，调用以下的函数
    public String findOfficeSpace(){
        List<OfficeSpace> formList = officeSpaceMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteOfficeSpace")
    public void deleteOfficeSpace(@RequestBody OfficeSpace officeSpace) {
        QueryWrapper<OfficeSpace> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("space_id",officeSpace.getSpaceId());
        officeSpaceMapper.delete(queryWrapper);
    }

    @PostMapping("/insertOfficeSpace")
    public void insertOfficeSpace(@RequestBody OfficeSpace officeSpace){//RequestBody注解会自动把json改成实体类
        officeSpaceMapper.insert(officeSpace);  //传入的是实体参数
    }

    @PostMapping("/updateOfficeSpace")
    public void updateOfficeSpace(@RequestBody OfficeSpace officeSpace){
        QueryWrapper<OfficeSpace> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("space_id",officeSpace.getSpaceId());//转换SQL语言
        officeSpaceMapper.update(officeSpace,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }

}


