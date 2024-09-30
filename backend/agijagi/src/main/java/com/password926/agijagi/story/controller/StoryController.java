package com.password926.agijagi.story.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.story.controller.dto.CreateStoryRequest;
import com.password926.agijagi.story.entity.Story;
import com.password926.agijagi.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/stories")
@RestController
public class StoryController {

    private final StoryService storyService;

    @Authenticate
    @PostMapping
    public ResponseEntity<Void> createStory(
            LoginMember member,
            CreateStoryRequest createStoryRequest
    ) {
        storyService.createStory(member.getId(), createStoryRequest);
        return ResponseEntity.ok().build();
    }

//    @Authenticate
//    @GetMapping
//    public ResponseEntity<List<Story>> getAllStory(
//            LoginMember member,
//            @RequestParam long childId
//    ) {
//        return ResponseEntity.ok().body(storyService.getAllStory(member.getId(), childId));
//    }
//
//    @Authenticate
//    @GetMapping("/{storyId}")
//    public ResponseEntity<Story> getStory(
//            LoginMember member,
//            @PathVariable long storyId
//    ) {
//        return ResponseEntity.ok().body(storyService.getStory(member.getId(), storyId));
//    }
//
//    @Authenticate
//    @DeleteMapping("/{storyId}")
//    public ResponseEntity<Void> deleteStory(
//            LoginMember member,
//            @PathVariable long storyId
//    ) {
//        storyService.deleteStory(member.getId(), storyId);
//        return ResponseEntity.ok().build();
//    }

}
