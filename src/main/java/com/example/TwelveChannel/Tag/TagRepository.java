package com.example.TwelveChannel.Tag;

import com.example.TwelveChannel.Thread.ThreadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepository implements ITagRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<String> userTag(int user_id){
        var param=new MapSqlParameterSource();
        param.addValue("user_id",user_id);
        return jdbcTemplate.query("SELECT tag " +
                                    "FROM users_tag " +
                                    "WHERE user_id=:user_id",param,new DataClassRowMapper<>(String.class));
    }

    @Override
    public List<String> threadTag(int thread_id){
        var param=new MapSqlParameterSource();
        param.addValue("thread_id",thread_id);
        return jdbcTemplate.query("SELECT tag " +
                "FROM threads_tag " +
                "WHERE thread_id=:thread_id",param,new DataClassRowMapper<>(String.class));
    }
}
