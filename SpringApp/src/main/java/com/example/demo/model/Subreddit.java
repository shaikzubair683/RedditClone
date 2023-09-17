package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "subreddit name cannot be blank")
    private String name;

    @NotBlank(message = "subreddit needs to have description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    private Instant dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
