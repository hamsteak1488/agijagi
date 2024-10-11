package com.password926.agijagi.story.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.story.controller.dto.CreateStoryRequest;
import com.password926.agijagi.story.entity.StoryPageDetail;
import com.password926.agijagi.story.service.StoryService;
import com.password926.agijagi.story.entity.StoryDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@RequestMapping("/stories")
@RestController
public class StoryController {

    private final StoryService storyService;

    @Authenticate
    @PostMapping
    public ResponseEntity<Map<String, Long>> createStory(
            LoginMember member,
            CreateStoryRequest createStoryRequest
    ) {
        return ResponseEntity.ok().body(storyService.createStory(member.getId(), createStoryRequest));
    }

    @Authenticate
    @GetMapping
    public ResponseEntity<List<StoryDetail>> getAllStory(
            LoginMember member,
            @RequestParam("childId") long childId
    ) {
        return ResponseEntity.ok().body(storyService.getAllStory(member.getId(), childId));
    }

    @Authenticate
    @GetMapping("/{storyId}")
    public ResponseEntity<StoryDetail> getStory(
            LoginMember member,
            @PathVariable long storyId
    ) {
        return ResponseEntity.ok().body(storyService.getStory(member.getId(), storyId));
    }

    @Authenticate
    @GetMapping("/{storyId}/pages")
    public ResponseEntity<List<StoryPageDetail>> getStoryAllPage(
            LoginMember member,
            @PathVariable long storyId
    ) {
        return ResponseEntity.ok().body(storyService.getStoryAllPage(member.getId(), storyId));
    }

    @Authenticate
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(
            LoginMember member,
            @PathVariable long storyId
    ) {
        storyService.deleteStory(member.getId(), storyId);
        return ResponseEntity.ok().build();
    }
}
