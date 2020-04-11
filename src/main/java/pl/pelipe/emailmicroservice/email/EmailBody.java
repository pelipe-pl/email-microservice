package pl.pelipe.emailmicroservice.email;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmailBody {
    
    @NotNull
    private String senderName;
    @NotNull
    private String fromAddress;
    @NotNull
    private String toAddress;
    @NotNull
    private String subject;
    @NotNull
    private String content;
}