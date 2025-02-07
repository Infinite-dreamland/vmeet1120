package com.rainbowdoor.vmeet.entity;

import lombok.Data;

@Data
public class UserAssetWithoutPrivacy {
    private String username;
    private int id;
    private String name;
    private String type;
    private String created_time;
    private String last_modified_time;
    private float price;
    private String thumbimages;
    private String description;
    private int num_views;
    private int num_buys;
}
