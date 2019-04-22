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
    public ResponseEntity<?> sendEmail(@PathVariable String token, @Valid @RequestBody EmailBody emailBody) {

        int result = emailService.send(token, emailBody);
        if (result == 0) return new ResponseEntity(HttpStatus.OK);
        if (result == 1) return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        if (result == 2) return new ResponseEntity(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}