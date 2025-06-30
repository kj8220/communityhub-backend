package com.example.communityhub.service;

import com.example.communityhub.dto.CreatePostRequest;
import com.example.communityhub.entity.Post;
import com.example.communityhub.entity.Subreddit;
import com.example.communityhub.entity.User;
import com.example.communityhub.repository.PostRepository;
import com.example.communityhub.repository.SubredditRepository;
import com.example.communityhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;

    @Transactional
    public void createPost(CreatePostRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subreddit subreddit = subredditRepository.findByName(request.getSubredditName())
                .orElseGet(() -> subredditRepository.findByName("general")
                        .orElseThrow(() -> new RuntimeException("Default subreddit not found")));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .url(request.getUrl())
                .created(LocalDateTime.now())
                .voteCount(0)
                .user(user)
                .subreddit(subreddit)
                .build();

        postRepository.save(post);
    }
}
