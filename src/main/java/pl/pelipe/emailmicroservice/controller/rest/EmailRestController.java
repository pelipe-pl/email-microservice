package pl.pelipe.emailmicroservice.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pelipe.emailmicroservice.email.EmailService;
import pl.pelipe.emailmicroservice.email.Payload;
import pl.pelipe.emailmicroservice.email.ResponseStatus;

import javax.validation.Valid;

import static pl.pelipe.emailmicroservice.config.keys.Keys.REST_EMAIL_INVALID_TOKEN_MSG;
import static pl.pelipe.emailmicroservice.config.keys.Keys.REST_EMAIL_SUCCESS_MSG;
import static pl.pelipe.emailmicroservice.email.ResponseStatus.*;

@RestController
public class EmailRestController {

    private final EmailService emailService;

    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/rest/send")
    @ResponseBody
    public ResponseEntity<String> sendEmail(@Valid @RequestBody Payload payload) {

        ResponseStatus result = emailService.send(payload);
        if (result == OK) return new ResponseEntity<>(REST_EMAIL_SUCCESS_MSG, HttpStatus.OK);
        if (result == TOKEN_ERROR) return new ResponseEntity<>(REST_EMAIL_INVALID_TOKEN_MSG, HttpStatus.UNAUTHORIZED);
        if (result == FAILED) return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}