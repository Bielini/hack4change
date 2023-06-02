package com.capgemini.hack4change.ai.model;

import lombok.Data;

@Data
public class GenerateHobbiesRequest {
    private String input;
    private int hobbies;
    private int difficulty;
}
