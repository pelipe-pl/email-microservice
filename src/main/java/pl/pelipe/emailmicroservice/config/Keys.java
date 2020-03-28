package pl.pelipe.emailmicroservice.config;

public class Keys {

    public static final String LOG_TOKEN_NOT_EXISTING = "Not existing token info checking invoked. Provided token value: %s";
    public static final String LOG_TOKEN_CREATED = "New token created for: %s ";
    public static final String LOG_TOKEN_USAGE_INFO = "Token with id %s used for email send.";
    public static final String LOG_TOKEN_INVALID = "Invalid token value %s used for email send. Token is invalid, expired or reached daily limit.";
    public static final String LOG_TOKEN_USAGE_LIMIT_REACHED = "Daily usage limit of %s has been reached for token id %s";

    public static final String LOG_USER_NOT_FOUND = "User with username %s not found.";
    public static final String LOG_USER_REGISTRATION = "New user %s has registered with user id: %s.";
    public static final String LOG_USER_UPDATE = "User data for %s has been updated.";
    public static final String LOG_USER_LOGGED_IN = "User %s has logged in.";

    public static final String LOG_SCHEDULER_USER_STATS_UPDATE = "User stats successfully updated by scheduler";
    public static final String LOG_SCHEDULER_TOKEN_STATS_UPDATE = "Token stats successfully updated by scheduler";

    public static final String LOG_SENDGRID_EMAIL_SENDING_INFO = "Trying to send email to: %s";
    public static final String LOG_SENDGRID_RESPONSE_CODE = "SendGrid response code: %s";
    public static final String LOG_SENDGRID_RESPONSE_BODY = "SendGrid response body: %s";
    public static final String LOG_SENDGRID_RESPONSE_HEADERS = "SendGrid response headers: %s";
    public static final String LOG_SENDGRID_FAIL = "Failed to send email from: %s, to: %s, subject: %s";

    public static final String EMAIL_SUBJECT_NEW_TOKEN_NOTIFY = "New token has been generated for you";
    public static final String EMAIL_CONTENT_NEW_TOKEN_NOTIFY = "Your new token is: %s";

    public static final String REST_EMAIL_SUCCESS_MSG = "Your e-mail has been successfully processed.";
    public static final String REST_EMAIL_INVALID_TOKEN_MSG = "Your token is invalid, expired or reached your daily limit.";
}
