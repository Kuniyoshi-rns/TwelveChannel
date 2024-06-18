package com.example.TwelveChannel.User.Form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpForm {
    @NotEmpty(message="ログインIDは必須です")
    private String login_id;

    @NotEmpty(message="パスワードは必須です")
    private String password;

    @NotEmpty(message="パスワード（確認）は必須です")
    private String passwordCheck;
}
