package com.alibou.security.ai;

import lombok.Data;

import java.util.List;

@Data
public class Hobby {
    private String name;
    private String description;
    private List<String> steps;
}
