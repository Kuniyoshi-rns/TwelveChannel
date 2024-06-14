package com.example.TwelveChannel.Thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThreadRepository implements IThreadRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<ThreadEntity>  threadAll(){
        return jdbcTemplate.query("SELECT * FROM threads",new DataClassRowMapper<>(ThreadEntity.class));
    }

    @Override
    public ThreadEntity findByIdThread(int thread_id){
        var param = new MapSqlParameterSource();
        param.addValue("threadid",thread_id);
        var threadList = jdbcTemplate.query("SELECT * FROM threads " +
                                                "WHERE id = :threadid",
                                                param,new DataClassRowMapper<>(ThreadEntity.class));
        return threadList.isEmpty() ? null : threadList.get(0);
    }

    @Override
    public List<ThreadEntity> findByCreatorThread(int user_id){
        var param = new MapSqlParameterSource();
        param.addValue("creator",user_id);
        return jdbcTemplate.query("SELECT * FROM threads " +
                        "WHERE creator = :creator",
                param,new DataClassRowMapper<>(ThreadEntity.class));
    }

    public int insertThread(ThreadEntity threadEntity){
        var param = new MapSqlParameterSource();
        param.addValue("creator",threadEntity.creator());
        param.addValue("thread_title",threadEntity.thread_title());
        param.addValue("comment",threadEntity.comment());
        param.addValue("image_name",threadEntity.image_name());
        param.addValue("image_base64",threadEntity.image_base64());
        param.addValue("created_at",threadEntity.created_at());
        param.addValue("updated_at",threadEntity.updated_at());
        param.addValue("view_count",threadEntity.view_count());

        return jdbcTemplate.update("insert into threads(creator,thread_title,comment,image_name,image_base64,created_at,updated_at,view_count)" +
                " values(:creator,:thread_title,:comment,:image_name,:image_base64,:created_at,:updated_at,:view_count)",param);
    }

    @Override
    public int updateThread(ThreadEntity threadEntity){

        var param = new MapSqlParameterSource();
        param.addValue("id",threadEntity.id());
        param.addValue("thread_title",threadEntity.thread_title());
        param.addValue("comment",threadEntity.comment());
        param.addValue("image_name",threadEntity.image_name());
        param.addValue("image_base64",threadEntity.image_base64());
        param.addValue("updated_at",threadEntity.updated_at());

        return jdbcTemplate.update("UPDATE threads SET thread_title = :thread_title," +
                                                            "comment = :comment," +
                                                            "image_name= :image_name," +
                                                            "image_base64= :image_base64," +
                                                            "updated_at= :updated_at " +
                                                            "WHERE id = :id",param);
    }

    @Override
    public int deleteThread(int thread_id){
        var param = new MapSqlParameterSource();
        param.addValue("id",thread_id);
        return jdbcTemplate.update("DELETE FROM threads WHERE id = :id",param);
    }

    @Override
    public void addViewCount(int id){
        var param=new MapSqlParameterSource();
        param.addValue("id",id);
        jdbcTemplate.update("UPDATE threads " +
                "SET view_count=view_count+1 " +
                "WHERE id=:id",param);
    }
}
