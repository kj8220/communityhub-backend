package com.example.communityhub.dto;

import com.example.communityhub.enums.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {

    @NotNull
    private VoteType voteType;

    @NotNull
    private Long postId;
}
