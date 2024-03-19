package org.example.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.example.mapper.ClientMapper;
import org.example.pojo.Client;
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
public class ClientController {
    @Autowired  //自动接线注解
    private ClientMapper clientMapper;//可以关联，找到数据，之后mapper扎到数据库


    private Gson gson =new Gson();
    @GetMapping("/findClient")//路由，访问/find页面时，调用以下的函数
    public String findClient(){
        List<Client> formList = clientMapper.selectList(null);  //查询
        return gson.toJson(formList); //把对象转换成json格式（  “{xx:123,bb:234}”    )
    }

    @PostMapping("/deleteClient")
    public void deleteClient(@RequestBody Client client) {
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_name",client.getClientName());
        clientMapper.delete(queryWrapper);
    }

    @PostMapping("/insertClient")
    public void insertClient(@RequestBody Client client){//RequestBody注解会自动把json改成实体类
        clientMapper.insert(client);  //传入的是实体参数
    }

    @PostMapping("/updateClient")
    public void updateClient(@RequestBody Client client){
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_name",client.getClientName());//转换SQL语言
        clientMapper.update(client,queryWrapper);
//        secretaryMapper.updateById(secretary);  //传入的是实体参数
    }

    @GetMapping("/findClientName")
    public String findClientName() {
        List<Client> FormList = clientMapper.selectList(
                new QueryWrapper<Client>().select("client_name")
        );

        List<String> nameList = new ArrayList<>();
        for (Client client : FormList) {
            nameList.add(client.getClientName());
        }

        return gson.toJson(nameList);
    }
    @PostMapping("/searchClientByName")
    public String searchClientByName(@RequestBody HashMap<String,String> data) {
        String name=data.get("name");
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();//包装类，包装SQL语句
        queryWrapper.like("client_name",name);//select * from secretary where name like '%小%'
        List<Client> secretaryList = clientMapper.selectList(queryWrapper);//映射到数据库，开始执行queryWrapper内语句，这里会返回结果
        return gson.toJson(secretaryList);
    }
}



