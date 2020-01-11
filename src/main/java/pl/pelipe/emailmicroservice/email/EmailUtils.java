package pl.pelipe.emailmicroservice.email;

public class EmailUtils {

    public static String anonymize(String email) {
        return "****" + email.substring(4);
    }
}