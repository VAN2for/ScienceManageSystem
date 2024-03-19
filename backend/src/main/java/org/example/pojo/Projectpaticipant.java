package org.example.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Projectpaticipant {
        private String paticipantName;
        private double workHour;
        private Date joinTime;
        private double money;
}
