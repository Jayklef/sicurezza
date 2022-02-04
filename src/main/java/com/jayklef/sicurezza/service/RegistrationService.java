package com.jayklef.sicurezza.service;

import com.jayklef.sicurezza.appuser.AppUserRole;
import com.jayklef.sicurezza.model.AppUser;
import com.jayklef.sicurezza.model.ConfirmationToken;
import com.jayklef.sicurezza.repository.ConfirmationTokenRepository;
import com.jayklef.sicurezza.repository.EmailSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSender emailSender;


    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail){
            throw new IllegalStateException("Email is not valid");
        }
         String token = appUserService.signUpUser(new AppUser(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.ADMIN
        )

        );
        String Link = "http://localhost:8004/regoster/confirm?token" + token;
        emailSender.send(request.getEmail(), buildEmail(request.getFirstname(), Link));
        return token;
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.getToken(token);
        appUserService.enableAppUser((confirmationToken.getAppUser().getEmail()));


        return "Confirmed";
    }

    private String buildEmail(String name, String Link){
        return "Email body";
    }
}


