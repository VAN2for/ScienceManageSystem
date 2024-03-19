package org.example.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class Secretary {
    private String secretaryNo;
    private String name;
    private String gender;
    private int age;
    private Date hireDate;
    private String duty;
}
