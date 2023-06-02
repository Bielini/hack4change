package com.alibou.security.ai;

import lombok.Data;

@Data
public class GenerateHobbiesRequest {
    private String input;
    private int hobbies;
    private int difficulty;
}
