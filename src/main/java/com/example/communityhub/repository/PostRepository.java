package com.example.communityhub.repository;

import com.example.communityhub.entity.Post;
import com.example.communityhub.entity.Subreddit;
import com.example.communityhub.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
	@Query("SELECT COUNT(p) > 0 FROM Post p WHERE p.title = :title AND p.content = :content AND p.user = :user AND p.subreddit = :subreddit")
	boolean existsByTitleAndContentAndUserAndSubreddit(String title, String content, User user, Subreddit subreddit);

}
