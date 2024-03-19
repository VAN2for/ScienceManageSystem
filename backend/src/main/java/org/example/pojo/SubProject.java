package org.example.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class SubProject {
    private String subprojectId;
    private String projectNo;
    private String subprojectLeader;
    private Date completionDate;
    private double availableFunding;
    private String technicalIndicators;
}
