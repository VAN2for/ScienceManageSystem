package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.PartnerMapper;
import org.example.pojo.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//mybatis增删改查
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins={"*","null"})  //解决跨域问题的注释
@SuppressWarnings("all")  //去掉所有警告
@RestController   //添加路由的规则
public class PartnerController {
    @Autowired  //自动接线注解
    private PartnerMapper partnerMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findPartner")//路由，访问/find页面时，调用以下的函数
    public String findPartner(){
        List<Partner> formList = partnerMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deletePartner")
    public void deletePartner(@RequestBody Partner partner) {
        QueryWrapper<Partner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("partner_id",partner.getPartnerId());
        partnerMapper.delete(queryWrapper);
    }

    @PostMapping("/insertPartner")
    public void insertPartner(@RequestBody Partner partner){//RequestBody注解会自动把json改成实体类
        partnerMapper.insert(partner);  //传入的是实体参数
    }

    @PostMapping("/updatePartner")
    public void updatePartner(@RequestBody Partner partner){
        QueryWrapper<Partner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("partner_id",partner.getPartnerId());//转换SQL语言
        partnerMapper.update(partner,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }





}


