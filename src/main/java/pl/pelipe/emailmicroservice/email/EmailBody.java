package pl.pelipe.emailmicroservice.email;

import javax.validation.constraints.NotNull;

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

    public EmailBody(String senderName, String fromAddress, String toAddress, String subject, String content) {
        this.senderName = senderName;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EmailBody{" +
                "senderName='" + senderName + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}