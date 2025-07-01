package com.example.communityhub.repository;

import com.example.communityhub.entity.Post;
import com.example.communityhub.entity.User;
import com.example.communityhub.entity.Vote;
import com.example.communityhub.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByPostAndUserAndVoteType(Post post, User user, VoteType voteType);
}
