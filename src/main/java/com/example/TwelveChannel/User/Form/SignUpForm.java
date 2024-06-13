package com.example.TwelveChannel.User.Form;

import lombok.Data;

@Data
public class SignUpForm {
    private String login_id;
    private String password;
    private String passwordCheck;
}
