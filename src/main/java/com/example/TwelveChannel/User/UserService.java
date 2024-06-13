package com.example.TwelveChannel.User;

import com.example.TwelveChannel.User.Form.LoginForm;
import com.example.TwelveChannel.User.Form.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService implements IUserService{
    @Autowired
    IUserRepository userRepository;
    @Override
    public UserEntity findByIdUser(LoginForm loginForm){
        return userRepository.findByIdUser(loginForm);
    }
    @Override
    public int insertUser(SignUpForm signUpForm) {
        try {
            return userRepository.insertUser(signUpForm);
        } catch (SQLException e) {
            return 0;
        }catch (DuplicateKeyException e){
            return -1;
        }
    }
    @Override
    public int deleteUser(int id){
        return userRepository.deleteUser(id);
    }

}
