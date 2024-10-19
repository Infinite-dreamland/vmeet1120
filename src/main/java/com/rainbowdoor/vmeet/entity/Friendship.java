package com.rainbowdoor.vmeet.entity;

import lombok.Data;

@Data
public class Friendship {
    private int id;
    private int uid1;
    private int uid2;
    private String status;
    private String created_time;
    private String last_modified_time;
}
