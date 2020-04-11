package pl.pelipe.emailmicroservice.email;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Payload {

    @NotNull
    private String tokenValue;

    @NotNull
    private EmailBody emailBody;

}
