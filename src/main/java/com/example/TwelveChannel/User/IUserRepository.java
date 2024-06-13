package com.example.TwelveChannel.User;

import com.example.TwelveChannel.User.Form.LoginForm;
import com.example.TwelveChannel.User.Form.SignUpForm;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLException;

public interface IUserRepository {
    UserEntity findByIdUser(LoginForm loginForm);
    int insertUser(SignUpForm signUpForm) throws SQLException, DuplicateKeyException;

    int deleteUser(int id);
}
