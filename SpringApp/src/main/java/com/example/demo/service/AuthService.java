package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.exception.SpringRedditException;
import com.example.demo.model.NotificationEmail;
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public Long signup(RegisterRequest registerRequest){
        User user =User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .dateCreated(Instant.now())
                .enabled(false)
                .build();
        log.info("gave user its values");
        userRepository.save(user);
        log.info("saved user in database");
        String token =generateVerificationToken(user);
        log.info("verification token created");
//        mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),"Please click on this link to activate"+
//                "http://localhost:8080/api/auth/accountverification/"+token));
        log.info("mail sent");
        return user.getUserId();
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
       Optional<VerificationToken> verificationToken= verificationTokenRepository.findByToken(token);
       verificationToken.orElseThrow(()-> new SpringRedditException("invalid token"));

       User user = verificationToken.get().getUser();
       fetchAndEnable(user);

    }

    @Transactional
    private void fetchAndEnable(User user){
        userRepository.updateEnabled(user.getUserId());
    }
}
