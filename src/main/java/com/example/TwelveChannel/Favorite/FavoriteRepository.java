package com.example.TwelveChannel.Favorite;

import com.example.TwelveChannel.Thread.ThreadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteRepository implements IFavoriteRepository{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<ThreadEntity> getFavoriteListByUser(int userId) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id",userId);
        return jdbcTemplate.query("SELECT id, creator,thread_title,comment,image_name,image_base64,created_at,updated_at,view_count " +
                "FROM favorite_threads " +
                "JOIN threads ON favorite_threads.thread_id=threads.id " +
                "WHERE user_id = :user_id;",param,new DataClassRowMapper<>(ThreadEntity.class));

    }

    @Override
    public int getFavoriteListByThread(int threadId) {
        var param = new MapSqlParameterSource();
        param.addValue("thread_id", threadId);
        return jdbcTemplate.query("SELECT count(*) " +
                "FROM favorite_threads " +
                "WHERE thread_id = :thread_id", param, new DataClassRowMapper<>(count.class)).get(0).count();
    }

    @Override
    public boolean isFavorite(int userId, int threadId) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id",userId);
        param.addValue("thread_id",threadId);

        return !jdbcTemplate.query("SELECT * " +
                "FROM favorite_threads " +
                "WHERE user_id = :user_id " +
                "AND thread_id = :thread_id",param,new DataClassRowMapper<>(FavoriteEntity.class)).isEmpty();
    }

    @Override
    public void insertFavorite(int userId, int threadId) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id",userId);
        param.addValue("thread_id",threadId);
        jdbcTemplate.update("INSERT INTO favorite_threads " +
                "VALUES (:thread_id,:user_id)",param);
    }

    @Override
    public void deleteFavorite(int userId, int threadId) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id",userId);
        param.addValue("thread_id",threadId);
        jdbcTemplate.update("DELETE FROM favorite_threads WHERE user_id = :user_id AND thread_id = :thread_id ",param);
    }
}
