package pl.pelipe.emailmicroservice.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static pl.pelipe.emailmicroservice.config.keys.Keys.*;

@Component
public class UserValidator implements Validator {

    private UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserEntity user = (UserEntity) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", USER_VALIDATION_ERROR_USERNAME_NOT_EMPTY);
        if (user.getUsername().length() < 6 || user.getUsername().length() > 40) {
            errors.rejectValue("username", String.format(USER_VALIDATION_ERROR_USERNAME_LENGTH, 6, 10));
        }
        if (userService.getByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", String.format(USER_VALIDATION_ERROR_USERNAME_TAKEN, ((UserEntity) o).getUsername()));
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", USER_VALIDATION_ERROR_PASSWORD_NOT_EMPTY);
        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
            errors.rejectValue("password", String.format(USER_VALIDATION_ERROR_PASSWORD_LENGTH, 4, 32));
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", USER_VALIDATION_ERROR_PASSWORDS_DONT_MATCH);
        }
    }
}