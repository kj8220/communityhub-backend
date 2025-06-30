package com.example.communityhub.controller;

import com.example.communityhub.dto.CreateSubredditRequest;
import com.example.communityhub.dto.SubredditResponse;
import com.example.communityhub.service.SubredditService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddits")
@RequiredArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<String> createSubreddit(@RequestBody @Valid CreateSubredditRequest request,
                                                  Authentication authentication) {
        subredditService.createSubreddit(request, authentication.getName());
        return ResponseEntity.ok("Subreddit created successfully");
    }

    @GetMapping
    public ResponseEntity<List<SubredditResponse>> getAllSubreddits() {
        return ResponseEntity.ok(subredditService.getAllSubreddits());
    }

    @GetMapping("/{name}")
    public ResponseEntity<SubredditResponse> getSubreddit(@PathVariable String name) {
        return ResponseEntity.ok(subredditService.getSubredditByName(name));
    }
}
