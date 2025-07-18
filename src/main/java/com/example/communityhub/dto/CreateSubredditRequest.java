package com.example.communityhub.dto;

import com.example.communityhub.enums.SubredditType;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubredditRequest {

    @NotBlank(message = "Subreddit name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;
    
    private SubredditType subredditType; 
    
}
