package com.example.TwelveChannel.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository implements ICommentRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<CommentEntity> getCommentByThread(int thread_id) {
        var param = new MapSqlParameterSource();
        param.addValue("thread_id", thread_id);
        var list = jdbcTemplate.query("SELECT * " +
                "FROM comments " +
                "WHERE thread_id = :thread_id", param, new DataClassRowMapper<>(CommentEntity.class));
        return list.isEmpty() ? null : list;
    }

    @Override
    public int insertComment(CommentForm commentForm,int thread_id,int user_id){
        var param = new MapSqlParameterSource();
        param.addValue("comment",commentForm.getComment());
        param.addValue("image_name",commentForm.getImage_name());
        param.addValue("image_base64",commentForm.getImage_base64());
        param.addValue("thread_id",thread_id);
        param.addValue("user_id",user_id);
        return jdbcTemplate.update("INSERT INTO comments " +
                "(thread_id,user_id,comment,image_name,image_base64,created_at) " +
                "VALUES " +
                "(:thread_id,:user_id,:comment,:image_name,:image_base64,now())",param);
    }
    @Override
    public int deleteComment(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        return jdbcTemplate.update("Delete From comments " +
                "WHERE id = :id ", param);
    }

    @Override
    public List<CommentEntity> getCommentByUser(int user_id){
        var param = new MapSqlParameterSource();
        param.addValue("user_id", user_id);
        var list = jdbcTemplate.query("SELECT * " +
                "FROM comments " +
                "WHERE user_id = :user_id", param, new DataClassRowMapper<>(CommentEntity.class));
        return list.isEmpty() ? null : list;
    }
}
