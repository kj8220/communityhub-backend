package com.example.communityhub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetPostResponse {
    private Long id;
    private String title;
    private String content;
    private String url;
    private int voteCount;
    private String username;
    private String subredditName;
    private LocalDateTime created;
}
