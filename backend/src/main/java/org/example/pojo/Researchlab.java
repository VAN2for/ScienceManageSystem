package org.example.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("research_lab")
@Data
public class Researchlab {
    private String labName;
    private String labDescription;
    private String directorid;
    private String secretaryname;
}
