package pl.pelipe.emailmicroservice.email;

import lombok.Data;

@Data
public class SendGridResponseError {
    private String field;
    private String message;
    private String help;
}
