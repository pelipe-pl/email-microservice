package pl.pelipe.emailmicroservice.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pelipe.emailmicroservice.email.EmailService;
import pl.pelipe.emailmicroservice.email.Payload;

import javax.validation.Valid;

import static pl.pelipe.emailmicroservice.config.keys.Keys.REST_EMAIL_INVALID_TOKEN_MSG;
import static pl.pelipe.emailmicroservice.config.keys.Keys.REST_EMAIL_SUCCESS_MSG;

@RestController
public class EmailRestController {

    private final EmailService emailService;

    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/rest/send")
    @ResponseBody
    public ResponseEntity<String> sendEmail(@Valid @RequestBody Payload payload) {

        boolean result = emailService.send(payload.getTokenValue(), payload.getEmailBody());
        if (result) return new ResponseEntity<>(REST_EMAIL_SUCCESS_MSG, HttpStatus.OK);
        else return new ResponseEntity<>(REST_EMAIL_INVALID_TOKEN_MSG, HttpStatus.UNAUTHORIZED);
    }
}