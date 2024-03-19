package org.example.pojo;

import lombok.Data;
//实体类
@Data  //get set tostring方法注解
public class Student {
    private long id;
    private String number;
    private String name;
    private Integer age;
    private Integer chi;
    private Integer eng;
    private Integer math;

}
