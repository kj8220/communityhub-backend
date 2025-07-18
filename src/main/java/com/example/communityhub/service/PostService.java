package com.example.communityhub.service;

import com.example.communityhub.dto.CreatePostRequest;
import com.example.communityhub.dto.GetPostResponse;
import com.example.communityhub.entity.PollOption;
import com.example.communityhub.entity.Post;
import com.example.communityhub.entity.Subreddit;
import com.example.communityhub.entity.User;
import com.example.communityhub.enums.PostType;
import com.example.communityhub.exception.DuplicatePostException;
import com.example.communityhub.repository.PostRepository;
import com.example.communityhub.repository.SubredditRepository;
import com.example.communityhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;
    private final FileStorageService fileStorageService;


    @Transactional
    public void createPost(CreatePostRequest request, MultipartFile file, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subreddit subreddit = subredditRepository.findByName(request.getSubredditName())
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        Post.PostBuilder postBuilder = Post.builder()
                .title(request.getTitle())
                .created(LocalDateTime.now())
                .voteCount(0)
                .user(user)
                .subreddit(subreddit)
                .postType(request.getPostType());

        switch (request.getPostType()) {
            case IMAGE:
            case VIDEO:
                if (file == null || file.isEmpty()) {
                    throw new IllegalArgumentException("Image/Video file is required for this post type.");
                }

                String fileUrl = fileStorageService.store(file);
                postBuilder.url(fileUrl);
                postBuilder.content(request.getContent()); 
                break;

            case POLL:
                if (request.getPollOptions() == null || request.getPollOptions().size() < 2) {
                    throw new IllegalArgumentException("A poll must have at least two options.");
                }

                postBuilder.content(request.getContent());
                break;

            case TEXT:
            default:
                if (request.getContent() == null || request.getContent().isBlank()) {
                    throw new IllegalArgumentException("Content is required for a text post.");
                }
                postBuilder.content(request.getContent());
                break;
        }

        Post post = postBuilder.build();

        if (request.getPostType() == PostType.POLL) {
            List<PollOption> options = request.getPollOptions().stream()
                    .map(optionText -> {
                        PollOption pollOption = new PollOption();
                        pollOption.setText(optionText);
                        pollOption.setPost(post); 
                        return pollOption;
                    }).collect(Collectors.toList());
            post.setPollOptions(options);
        }

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
