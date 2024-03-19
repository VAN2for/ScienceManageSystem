package org.example.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class Achievement {
    private String achievementName;
    private String projectId;
    private String contributer;
    private Date achieveTime;
    private int ranking;
    private String classification;
}
