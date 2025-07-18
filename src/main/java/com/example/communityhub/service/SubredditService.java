package com.example.communityhub.service;

import com.example.communityhub.dto.CreateSubredditRequest;
import com.example.communityhub.dto.SubredditResponse;
import com.example.communityhub.entity.Subreddit;
import com.example.communityhub.entity.User;
import com.example.communityhub.repository.SubredditRepository;
import com.example.communityhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;

    public void createSubreddit(CreateSubredditRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subreddit subreddit = Subreddit.builder()
                .name(request.getName())
                .description(request.getDescription())
                .created(LocalDateTime.now())
                .user(user)
                .subredditType(request.getSubredditType())
                .build();

        subredditRepository.save(subreddit);
    }

    @Transactional(readOnly = true)
    public List<SubredditResponse> getAllSubreddits() {
        return subredditRepository.findAll().stream().map(subreddit ->
                SubredditResponse.builder()
                        .id(subreddit.getId())
                        .name(subreddit.getName())
                        .description(subreddit.getDescription())
                        .numberOfPosts(subreddit.getPosts() != null ? subreddit.getPosts().size() : 0)
                        .created(subreddit.getCreated())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditResponse getSubredditByName(String name) {
        Subreddit subreddit = subredditRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        return SubredditResponse.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts() != null ? subreddit.getPosts().size() : 0)
                .created(subreddit.getCreated())
                .build();
    }
}
