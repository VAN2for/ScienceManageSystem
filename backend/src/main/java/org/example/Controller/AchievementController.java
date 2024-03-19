package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.AchievementMapper;
import org.example.pojo.Achievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class AchievementController {
    @Autowired  //自动接线注解
    private AchievementMapper achievementMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findAchievement")//路由，访问/find页面时，调用以下的函数
    public String findAchievement(){
        List<Achievement> formList = achievementMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteAchievement")
    public void deleteClient(@RequestBody Achievement achievement) {
        QueryWrapper<Achievement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("achievement_name",achievement.getAchievementName());
        achievementMapper.delete(queryWrapper);
    }

    @PostMapping("/insertAchievement")
    public void insertClient(@RequestBody Achievement achievement){//RequestBody注解会自动把json改成实体类
        achievementMapper.insert(achievement);  //传入的是实体参数
    }

    @PostMapping("/updateAchievement")
    public void updateClient(@RequestBody Achievement achievement){
        QueryWrapper<Achievement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("achievement_name",achievement.getAchievementName());//转换SQL语言
        achievementMapper.update(achievement,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }



}
