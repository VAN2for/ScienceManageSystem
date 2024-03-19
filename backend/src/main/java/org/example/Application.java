package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//启动类
@SpringBootApplication
@MapperScan("org.example.mapper")  //扫描接口 让启动程序直到映射接口在mapper文件夹里
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}