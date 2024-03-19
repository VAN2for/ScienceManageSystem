
package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.QualityMonitorMapper;
import org.example.pojo.Client;
import org.example.pojo.QualityMonitor;
import org.example.pojo.Researchlab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class QualityMonitorController {
    @Autowired  //自动接线注解
    private QualityMonitorMapper qualityMonitorMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findQualityMonitor")//路由，访问/find页面时，调用以下的函数
    public String findQualityMonitor(){
        List<QualityMonitor> formList = qualityMonitorMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteQualityMonitor")
    public void deleteQualityMonitor(@RequestBody QualityMonitor qualityMonitor) {
        QueryWrapper<QualityMonitor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("monitor_name",qualityMonitor.getMonitorName());
        qualityMonitorMapper.delete(queryWrapper);
    }

    @PostMapping("/insertQualityMonitor")
    public void insertQualityMonitor(@RequestBody QualityMonitor qualityMonitor){//RequestBody注解会自动把json改成实体类
        qualityMonitorMapper.insert(qualityMonitor);  //传入的是实体参数
    }

    @PostMapping("/updateQualityMonitor")
    public void updateQualityMonitor(@RequestBody QualityMonitor qualityMonitor){
        QueryWrapper<QualityMonitor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("monitor_name",qualityMonitor.getMonitorName());//转换SQL语言
        qualityMonitorMapper.update(qualityMonitor,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }

    @GetMapping("/findQualityMonitorName")
    public String findQualityMonitorName() {
        List<QualityMonitor> FormList = qualityMonitorMapper.selectList(
                new QueryWrapper<QualityMonitor>().select("monitor_name")
        );

        List<String> nameList = new ArrayList<>();
        for (QualityMonitor qualityMonitor : FormList) {
            nameList.add(qualityMonitor.getMonitorName());
        }

        return gson.toJson(nameList);
    }

    @PostMapping("/searchMonitorName")
    public String searchMonitorName(@RequestBody HashMap<String,String> data) {
        String name=data.get("name");
        QueryWrapper<QualityMonitor> queryWrapper = new QueryWrapper<>();//包装类，包装SQL语句
        queryWrapper.like("monitor_name",name);//select * from secretary where name like '%小%'
        List<QualityMonitor> secretaryList = qualityMonitorMapper.selectList(queryWrapper);//映射到数据库，开始执行queryWrapper内语句，这里会返回结果
        return gson.toJson(secretaryList);
    }

}


