package com.example.communityhub.controller;

import com.example.communityhub.dto.VoteRequest;
import com.example.communityhub.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<String> vote(@Valid @RequestBody VoteRequest request, Authentication auth) {
        voteService.vote(request, auth.getName());
        return ResponseEntity.ok("Vote recorded successfully");
    }
}
