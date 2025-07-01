package com.example.communityhub.service;

import com.example.communityhub.dto.CreatePostRequest;
import com.example.communityhub.dto.GetPostResponse;
import com.example.communityhub.entity.Post;
import com.example.communityhub.entity.Subreddit;
import com.example.communityhub.entity.User;
import com.example.communityhub.exception.DuplicatePostException;
import com.example.communityhub.repository.PostRepository;
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

        boolean exists = postRepository.existsByTitleAndContentAndUserAndSubreddit(
                request.getTitle(),
                request.getContent(),
                user,
                subreddit
        );

        if (exists) {
            throw new DuplicatePostException("You already posted the same content in this subreddit");
        }

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
    
    @Transactional(readOnly = true)
    public List<GetPostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(post ->
            GetPostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .url(post.getUrl())
                    .voteCount(post.getVoteCount())
                    .username(post.getUser().getUsername())
                    .subredditName(post.getSubreddit().getName())
                    .created(post.getCreated())
                    .build()
        ).collect(Collectors.toList());
    }
}
