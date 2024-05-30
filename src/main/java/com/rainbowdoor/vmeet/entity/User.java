package com.rainbowdoor.vmeet.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String password;
    private String phone;
}
