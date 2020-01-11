package pl.pelipe.emailmicroservice.config;

public class Keys {

    public static final String LOG_TOKEN_NOT_EXISTING = "Not existing token info checking invoked. Provided token value: %s";
    public static final String LOG_TOKEN_CREATED = "New token created for: %s ";

    public static final String LOG_SENDGRID_EMAIL_SENDING_INFO = "Trying to send email with SendGrid to: %s";
    public static final String LOG_SENDGRID_RESPONSE_CODE = "SendGrid response code: %s";
    public static final String LOG_SENDGRID_RESPONSE_BODY = "SendGrid response body: %s";
    public static final String LOG_SENDGRID_RESPONSE_HEADERS = "SendGrid response headers: %s";
    public static final String LOG_SENDGRID_FAIL = "Failed to send email with SendGrid. From: %s, To: %s, Subject: %s";

    public static final String EMAIL_SUBJECT_NEW_TOKEN_NOTIFY = "New token has been generated for you";
    public static final String EMAIL_CONTENT_NEW_TOKEN_NOTIFY = "Your new token is: %s";

    public static final String REST_EMAIL_SUCCESS_MSG = "Your e-mail has been successfully processed.";
    public static final String REST_EMAIL_INVALID_TOKEN_MSG = "Your token is invalid, expired or reached your daily limit.";
}
