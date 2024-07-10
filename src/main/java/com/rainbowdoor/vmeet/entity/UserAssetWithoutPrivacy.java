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
}
