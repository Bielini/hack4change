package com.capgemini.hack4change.ai.controller;

import com.capgemini.hack4change.ai.service.HobbyService;
import com.capgemini.hack4change.ai.service.OpenAIService;
import com.capgemini.hack4change.ai.model.GenerateHobbiesRequest;
import com.capgemini.hack4change.ai.model.HobbyDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("api")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OpenAIController {

    OpenAIService openAIService;
    HobbyService hobbyService;

    @PostMapping("/hobbies")
    public List<HobbyDto> generateImages(@RequestBody GenerateHobbiesRequest request)
    {
        return openAIService.getSuggestedHobbies(request);
    }

    @PostMapping("/users/{userId}/hobby")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveHobbies(List<HobbyDto> hobbies, @PathVariable Integer userId){
        hobbyService.saveHobbies(hobbies, userId);
    }

}
