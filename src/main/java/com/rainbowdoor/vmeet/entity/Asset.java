package com.rainbowdoor.vmeet.entity;

import lombok.Data;

@Data
public class Asset {
    private int id;
    private String name;
    private int uid;
    private String type;
    private String privacy;
    private String created_time;
    private String last_modified_time;
}
