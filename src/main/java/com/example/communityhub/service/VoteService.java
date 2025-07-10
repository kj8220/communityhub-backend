package com.example.communityhub.service;

import com.example.communityhub.dto.VoteRequest;
import com.example.communityhub.entity.Post;
import com.example.communityhub.entity.User;
import com.example.communityhub.entity.Vote;
import com.example.communityhub.enums.VoteType;
import com.example.communityhub.exception.*;
import com.example.communityhub.repository.PostRepository;
import com.example.communityhub.repository.UserRepository;
import com.example.communityhub.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void vote(VoteRequest voteRequest, String username) {
        Post post = postRepository.findById(voteRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean alreadyVoted = voteRepository.existsByPostAndUserAndVoteType(post, user, voteRequest.getVoteType());
        if (alreadyVoted) {
            throw new AlreadyVotedException("You already " + voteRequest.getVoteType().name().toLowerCase() + "d this post");
        }

        Vote vote = Vote.builder()
                .voteType(voteRequest.getVoteType())
                .post(post)
                .user(user)
                .created(LocalDateTime.now())
                .build();

        voteRepository.save(vote);

        int voteChange = voteRequest.getVoteType().getDirection();
        post.setVoteCount(post.getVoteCount() + voteChange);
        postRepository.save(post);
    }
}
