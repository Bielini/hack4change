package com.capgemini.hack4change.ai.model;

import com.capgemini.hack4change.domain.model.Hobby;
import com.capgemini.hack4change.domain.model.Step;
import com.capgemini.hack4change.domain.model.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class HobbyDto {
    private String name;
    private String description;
    private List<String> steps;

    public static Hobby toEntity(HobbyDto dto, User user) {
        Hobby entity = Hobby.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .steps(dto.getSteps().stream().map(step -> {
                    Step newStep = Step.builder()
                            .description(step)
                            .build();
                    return newStep;
                }).collect(Collectors.toList()))
                .user(user)
                .build();
        return entity;
    }
}
