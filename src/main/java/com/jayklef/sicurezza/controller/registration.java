package com.jayklef.sicurezza.controller;

import com.jayklef.sicurezza.service.RegistrationRequest;
import com.jayklef.sicurezza.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("registration")
public class registration {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("")
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

}
