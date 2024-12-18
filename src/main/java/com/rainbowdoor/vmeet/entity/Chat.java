package com.rainbowdoor.vmeet.entity;

import lombok.Data;

@Data
public class Chat {
    private int id;
    private int from_uid;
    private int to_uid;
    private String content;
    private boolean visibility;
    private String created_time;
}
