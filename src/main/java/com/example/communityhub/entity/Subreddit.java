package com.example.communityhub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.example.communityhub.enums.SubredditType;

@Entity
@Table(name = "subreddit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "subreddit", cascade = CascadeType.ALL)
    private List<Post> posts;
    
    @Enumerated(EnumType.STRING) 
    private SubredditType subredditType;
}
