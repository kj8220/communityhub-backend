package com.example.communityhub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.communityhub.enums.PostType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String content;

    private String subredditName;

    private PostType postType;

    private List<String> pollOptions;
}