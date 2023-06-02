package com.capgemini.hack4change.ai.service;

import com.capgemini.hack4change.ai.model.HobbyDto;
import com.capgemini.hack4change.domain.model.User;
import com.capgemini.hack4change.domain.repository.HobbyRepository;
import com.capgemini.hack4change.domain.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HobbyService {

    private final HobbyRepository repository;
    private final UserRepository userRepository;

    public List<HobbyDto> convertOutputIntoHobbies(String output){
        output = output.substring(output.indexOf("\"content\":\"")+11, output.indexOf("\"},\"finis"));
        output = output.substring(output.indexOf("{'hobbies':")+12, output.lastIndexOf("']}]}")+4);
        output = output.replace("'", "\"");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(output, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("Error occurred during object mapping", e);
            return Collections.emptyList();
        }
    }

    public void saveHobbies(List<HobbyDto> hobbies, Integer userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        hobbies.stream().map(hobby -> HobbyDto.toEntity(hobby, user)).forEach(repository::save);
    }

}
