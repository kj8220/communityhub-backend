package com.example.communityhub.controller;

import com.example.communityhub.dto.CreatePostRequest;
import com.example.communityhub.dto.GetPostResponse;
import com.example.communityhub.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createPost(@RequestPart("postRequest") @Valid CreatePostRequest request,
                                             @RequestPart(value = "file", required = false) MultipartFile file,
                                             Authentication authentication) {
        postService.createPost(request, file, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }
    @GetMapping
    public ResponseEntity<List<GetPostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

}
