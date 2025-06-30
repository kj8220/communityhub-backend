package com.example.communityhub.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubredditResponse {
    private Long id;
    private String name;
    private String description;
    private int numberOfPosts;
    private LocalDateTime created;
}
