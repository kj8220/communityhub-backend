package com.example.communityhub.controller;

import com.example.communityhub.dto.CreatePostRequest;
import com.example.communityhub.dto.GetPostResponse;
import com.example.communityhub.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody @Valid CreatePostRequest request,
                                             Authentication authentication) {
        postService.createPost(request, authentication.getName());
        return ResponseEntity.ok("Post created successfully");
    }
    
    @GetMapping
    public ResponseEntity<List<GetPostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

}
