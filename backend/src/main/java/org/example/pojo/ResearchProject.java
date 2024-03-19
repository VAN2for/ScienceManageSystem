package org.example.pojo;

import lombok.Data;

import java.util.Date;

@Data

public class ResearchProject {
    private String projectNo;
    private String projectLeader;
    private String projectName;
    private String researchContent;
    private double totalFunding;
    private Date startDate;
    private Date completionDate;
    private String clientName;
    private String monitorName;
}
