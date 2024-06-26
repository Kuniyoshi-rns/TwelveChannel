package com.example.TwelveChannel.Comment;

import com.example.TwelveChannel.Favorite.Count;
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
        return jdbcTemplate.query("SELECT comments.id,thread_id,thread_title,user_id,comments.comment,comments.image_name,comments.image_base64,comments.created_at " +
                "FROM comments " +
                "INNER JOIN threads " +
                "ON comments.thread_id = threads.id " +
                "WHERE thread_id = :thread_id ORDER BY created_at DESC", param, new DataClassRowMapper<>(CommentEntity.class));
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
        return jdbcTemplate.query("SELECT comments.id,thread_id,thread_title,user_id,comments.comment,comments.image_name,comments.image_base64,comments.created_at " +
                "FROM comments " +
                "INNER JOIN threads " +
                "ON comments.thread_id = threads.id " +
                "WHERE user_id = :user_id", param, new DataClassRowMapper<>(CommentEntity.class));
    }

    @Override
    public int getCommentListByThread(int threadId) {
        var param = new MapSqlParameterSource();
        param.addValue("thread_id", threadId);
        return jdbcTemplate.query("SELECT count(*) " +
                "FROM comments " +
                "WHERE thread_id = :thread_id", param, new DataClassRowMapper<>(CommentCount.class)).get(0).count();
    }

    @Override
    public CommentEntity insertAndGet(CommentForm commentForm, int thread_id, int user_id) {
        var param = new MapSqlParameterSource();
        param.addValue("comment",commentForm.getComment());
        param.addValue("image_name",commentForm.getImage_name());
        param.addValue("image_base64",commentForm.getImage_base64());
        param.addValue("thread_id",thread_id);
        param.addValue("user_id",user_id);
        return jdbcTemplate.query("INSERT INTO comments " +
                "(thread_id,user_id,comment,image_name,image_base64,created_at) " +
                "VALUES " +
                "(:thread_id,:user_id,:comment,:image_name,:image_base64,now())"+
                " RETURNING id,thread_id,'' thread_title,user_id,comment,image_name,image_base64,created_at;",param,new DataClassRowMapper<>(CommentEntity.class)).get(0);
    }

    @Override
    public List<CommentCountHome> getCommentListAllThreadHome() {
        return jdbcTemplate.query("SELECT thread_id,count(*) " +
                "FROM comments " +
                "GROUP BY thread_id", new DataClassRowMapper<>(CommentCountHome.class));
    }

    @Override
    public List<CommentEntity> getCommentOffsetByUser(int user_id, int offset){
        var param = new MapSqlParameterSource();
        param.addValue("user_id", user_id);
        param.addValue("offset", offset);
        return jdbcTemplate.query("SELECT comments.id,thread_id,thread_title,user_id,comments.comment,comments.image_name,comments.image_base64,comments.created_at " +
                "FROM comments " +
                "INNER JOIN threads " +
                "ON comments.thread_id = threads.id " +
                "WHERE user_id = :user_id " +
                "ORDER BY comments.created_at DESC LIMIT 20 " +
                "OFFSET :offset", param, new DataClassRowMapper<>(CommentEntity.class));
    }

    @Override
    public void userCommentAllDel(int user_id){
        var param=new MapSqlParameterSource();
        param.addValue("user_id",user_id);
        jdbcTemplate.update("DELETE FROM comments " +
                "WHERE user_id = :user_id",param);
    }
}