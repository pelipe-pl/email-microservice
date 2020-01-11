package pl.pelipe.emailmicroservice.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pelipe.emailmicroservice.email.EmailBody;
import pl.pelipe.emailmicroservice.email.EmailService;

import javax.validation.Valid;

import static pl.pelipe.emailmicroservice.config.Keys.REST_EMAIL_INVALID_TOKEN_MSG;
import static pl.pelipe.emailmicroservice.config.Keys.REST_EMAIL_SUCCESS_MSG;

@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/rest/send/{token}")
    @ResponseBody
    public ResponseEntity<String> sendEmail(@PathVariable String token, @Valid @RequestBody EmailBody emailBody) {

        boolean result = emailService.send(token, emailBody);
        if (result) return new ResponseEntity<>(REST_EMAIL_SUCCESS_MSG, HttpStatus.OK);
        else return new ResponseEntity<>(REST_EMAIL_INVALID_TOKEN_MSG, HttpStatus.UNAUTHORIZED);
    }
}