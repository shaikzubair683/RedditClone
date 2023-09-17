package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "user needs to have a username")
    private String username;

    @NotBlank(message = "user needs to have a password")
    private String password;

    @NotEmpty(message = "email cannot be blank")
    @Email
    private String email;

    private Instant dateCreated;
    private boolean enabled;



}
