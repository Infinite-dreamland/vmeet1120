package com.rainbowdoor.vmeet.entity;

import lombok.Data;

@Data
public class UserToken {
    private int id;
    private int uid;
    private String token;
}
