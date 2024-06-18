package com.example.TwelveChannel.User.Form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {
    @NotEmpty(message="ログインIDは必須です")
    private String login_id;

    @NotEmpty(message="パスワードは必須です")
    private String password;
}
