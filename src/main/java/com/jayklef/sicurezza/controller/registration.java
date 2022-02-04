package com.jayklef.sicurezza.controller;

import com.jayklef.sicurezza.model.ConfirmationToken;
import com.jayklef.sicurezza.service.ConfirmationTokenService;
import com.jayklef.sicurezza.service.RegistrationRequest;
import com.jayklef.sicurezza.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("registration")
@AllArgsConstructor
public class registration {

    @Autowired
    private RegistrationService registrationService;

    private ConfirmationTokenService confirmationTokenService;

    @PostMapping("")
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
