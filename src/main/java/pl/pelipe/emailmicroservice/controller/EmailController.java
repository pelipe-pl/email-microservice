package pl.pelipe.emailmicroservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelipe.emailmicroservice.email.EmailBody;
import pl.pelipe.emailmicroservice.email.EmailService;

import javax.validation.Valid;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send/{token}")
    @ResponseBody
    public ResponseEntity<String> sendEmail(@PathVariable String token, @Valid @RequestBody EmailBody emailBody) {

        boolean result = emailService.send(token, emailBody);
        if (result) return new ResponseEntity<>("Your e-mail has been successfully processed.", HttpStatus.OK);
        else return new ResponseEntity<>("Your token is invalid, expired or reached your daily limit.",HttpStatus.UNAUTHORIZED);
    }
}