package com.example.TwelveChannel.User.Form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignUpForm {
//    @NotEmpty(message="ログインIDは必須です")
    @Length(min = 8, max = 50)
    private String login_id;

//    @NotEmpty(message="パスワードは必須です")
    @Length(min = 8, max = 50)
    private String password;

//    @NotEmpty(message="パスワード（確認）は必須です")
    private String passwordCheck;
}
