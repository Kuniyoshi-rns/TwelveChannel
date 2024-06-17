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
    public List<UserTagEntity> userTag(int user_id){
        var param=new MapSqlParameterSource();
        param.addValue("user_id",user_id);
        return jdbcTemplate.query("SELECT * " +
                                    "FROM users_tags " +
                                    "WHERE user_id=:user_id",param,new DataClassRowMapper<>(UserTagEntity.class));
    }

    @Override
    public List<ThreadTagEntity> threadTag(int thread_id){
        var param=new MapSqlParameterSource();
        System.out.println("thread_id確認:"+thread_id);
        param.addValue("thread_id",thread_id);
        return jdbcTemplate.query("SELECT * " +
                "FROM threads_tags " +
                "WHERE thread_id=:thread_id",param,new DataClassRowMapper<>(ThreadTagEntity.class));
    }

    @Override
    public int userTagInsert(int user_id,String tag){
        var param=new MapSqlParameterSource();
        param.addValue("user_id",user_id);
        param.addValue("tag",tag);
        var result=jdbcTemplate.query("SELECT * FROM users_tags " +
                                    "WHERE user_id=:user_id AND tag=:tag",param,new DataClassRowMapper<>(UserTagEntity.class));
        if(result.isEmpty()){
            return jdbcTemplate.update("INSERT INTO users_tags " +
                                        "VALUES(:user_id,:tag)",param);
        }
        return 0;
    }

    @Override
    public int userTagDelete(int user_id,String tag){
        var param=new MapSqlParameterSource();
        param.addValue("user_id",user_id);
        param.addValue("tag",tag);
        return jdbcTemplate.update("DELETE FROM users_tags " +
                                    "WHERE user_id=:user_id and tag=:tag",param);
    };
}
