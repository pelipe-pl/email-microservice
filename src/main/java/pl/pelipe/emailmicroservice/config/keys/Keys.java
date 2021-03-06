package pl.pelipe.emailmicroservice.config.keys;

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
    public static final String LOG_USER_LOGGED_OUT = "User %s has logged out.";
    public static final String LOG_USER_AUTHENTICATION_FAILED = "User authentication failed. Username: %s. Reason: %s. Address: %s";

    public static final String LOG_SCHEDULER_USER_STATS_UPDATE = "User stats successfully updated by scheduler";
    public static final String LOG_SCHEDULER_TOKEN_STATS_UPDATE = "Token stats successfully updated by scheduler";

    public static final String LOG_SENDGRID_EMAIL_SENDING_INFO = "Trying to send email to: %s";
    public static final String LOG_SENDGRID_RESPONSE_CODE = "SendGrid response code: %s";
    public static final String LOG_SENDGRID_RESPONSE_HEADERS = "SendGrid response headers: %s";
    public static final String LOG_SENDGRID_FAIL = "Failed to send email from: %s, to: %s";

    public static final String LOG_EMAIL_RESEND_NO_PENDING_EMAILS = "No pending emails for resend.";
    public static final String LOG_EMAIL_RESEND_START = "Found %s pending emails. Trying to resend...";
    public static final String LOG_EMAIL_RESEND_FINISHED = "Email resend service finished.";
    public static final String LOG_EMAIL_RESEND_STATS = "Succeeded: %s, failed: %s, processed %s";

    public static final String EMAIL_SUBJECT_NEW_TOKEN_NOTIFY = "New token has been generated for you";
    public static final String EMAIL_CONTENT_NEW_TOKEN_NOTIFY = "Your new token is: %s";

    public static final String REST_EMAIL_SUCCESS_MSG = "Your e-mail has been successfully processed.";
    public static final String REST_EMAIL_INVALID_TOKEN_MSG = "Your token is invalid, expired or reached your daily limit.";

    public static final String USER_VALIDATION_ERROR_PASSWORDS_DONT_MATCH = "Passwords do not match.";
    public static final String USER_VALIDATION_ERROR_PASSWORD_LENGTH = "Password must be %s-%s characters long.";
    public static final String USER_VALIDATION_ERROR_PASSWORD_NOT_EMPTY = "Password cannot be empty.";
    public static final String USER_VALIDATION_ERROR_USERNAME_LENGTH = "Username must be %s-%s characters long.";
    public static final String USER_VALIDATION_ERROR_USERNAME_TAKEN = "User %s already exists.";
    public static final String USER_VALIDATION_ERROR_USERNAME_NOT_EMPTY = "Username cannot be empty.";

    public static final String PASSWORD_RESET_EMAIL_CONTENT = "You have requested for new password. Following link is valid for 24 hours. " +
            "To reset your password, please click: \n";
    public static final String PASSWORD_RESET_EMAIL_SUBJECT = "Password reset request.";
    public static final String PASSWORD_RESET_EMAIL_URL = "password.reset.url";
    public static final String PASSWORD_RESET_EMAIL_SENDER_NAME = "password.reset.email.sender.name";
    public static final String PASSWORD_RESET_EMAIL_SENDER_ADDRESS = "password.reset.email.sender.address";
}
