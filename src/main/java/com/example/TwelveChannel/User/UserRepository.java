package com.example.TwelveChannel.User;

import com.example.TwelveChannel.User.Form.LoginForm;
import com.example.TwelveChannel.User.Form.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public UserEntity findByIdUser(LoginForm loginForm) {
        var param = new MapSqlParameterSource();
        param.addValue("login_id", loginForm.getLogin_id());
        param.addValue("password", loginForm.getPassword());
        var list = jdbcTemplate.query("SELECT * " +
                "FROM users " +
                "WHERE login_id = :login_id AND password = :password "
                , param, new DataClassRowMapper<>(UserEntity.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int insertUser(SignUpForm signUpForm){
        var param = new MapSqlParameterSource();
        param.addValue("login_id", signUpForm.getLogin_id());
        param.addValue("password", signUpForm.getPassword());
        return jdbcTemplate.update("INSERT INTO users " +
                "(login_id, password )" +
                "VALUES(:login_id,:password)", param);
    }

    @Override
    public int deleteUser(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        return jdbcTemplate.update("Delete From users " +
                "WHERE id = :id ",param);
    }
}
