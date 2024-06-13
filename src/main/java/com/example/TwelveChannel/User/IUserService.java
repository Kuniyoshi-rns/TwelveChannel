package com.example.TwelveChannel.User;

import com.example.TwelveChannel.User.Form.LoginForm;
import com.example.TwelveChannel.User.Form.SignUpForm;

public interface IUserService {
    UserEntity findByIdUser(LoginForm loginForm);
    int insertUser(SignUpForm signUpForm);

    int deleteUser(int id);
}
