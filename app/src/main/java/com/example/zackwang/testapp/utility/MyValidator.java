package com.example.zackwang.testapp.utility;

import com.example.zackwang.testapp.entity.User;

/**
 * Created by Zack Wang on 2015/10/10.
 */
public class MyValidator {
    private final String USERNAME_EMPTY_ERROR = "Username cannot be empty.";
    private final String PASSWORD_EMPTY_ERROR = "Password cannot be empty.";
    private final String PASSWORD_LENGTH_ERROR = "Password should between 3 and 12 characters";
    private final String EMAIL_EMPTY_ERROR = "Email cannot be empty";

    public String validateLoginForm(User user) {
        String errorMsg;
        if (user.getUsername().equals("") || user.getUsername() == null) {
            errorMsg = USERNAME_EMPTY_ERROR;
            return errorMsg;
        } else if (user.getPassword().equals("") || user.getPassword() == null) {
            errorMsg = PASSWORD_EMPTY_ERROR;
            return errorMsg;
        } else if (user.getPassword().length() < 3 || user.getPassword().length() > 12) {
            errorMsg = PASSWORD_LENGTH_ERROR;
            return errorMsg;
        } else
            return null;
    }

    public String validateRegisterForm(User user) {
        String errorMsg = validateLoginForm(user);
        if (errorMsg != null) {
            return errorMsg;
        } else if (user.getEmail().equals("") || user.getEmail() == null) {
            return EMAIL_EMPTY_ERROR;
        } else {
            return null;
        }
    }
}
