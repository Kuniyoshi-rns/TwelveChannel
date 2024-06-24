package com.example.TwelveChannel.Thread;

import com.example.TwelveChannel.Comment.ICommentService;
import com.example.TwelveChannel.Favorite.IFavoriteService;
import com.example.TwelveChannel.Tag.ITagService;
import com.example.TwelveChannel.Tag.TagService;
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

        jdbcTemplate.update("DELETE FROM comments WHERE thread_id = :id",param);
        jdbcTemplate.update("DELETE FROM favorite_threads WHERE thread_id = :id",param);
        jdbcTemplate.update("DELETE FROM threads_tags WHERE thread_id = :id",param);
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
@Override
    public int insertThreadOkuma(ThreadAddForm threadAddForm,int userId){
        var param = new MapSqlParameterSource();
        param.addValue("creator",userId);
        param.addValue("thread_title",threadAddForm.getTitle());
        param.addValue("comment",threadAddForm.getComment());
        param.addValue("image_name",threadAddForm.getImage_name());
        param.addValue("image_base64",threadAddForm.getImage_base64());

        return jdbcTemplate.query("insert into threads" +
                "(creator,thread_title,comment,image_name,image_base64,created_at,updated_at,view_count)" +
                " values" +
                "(:creator,:thread_title,:comment,:image_name,:image_base64,now(),now(),0)" +
                " RETURNING " +
                "id",param,new DataClassRowMapper<>(GetThreadId.class)).get(0).id();
    }

    @Override
    public List<ThreadEntity> findThread(int offset) {
        var param= new MapSqlParameterSource();
        param.addValue("offset",offset);
        return jdbcTemplate.query("SELECT * FROM threads ORDER BY id desc LIMIT 20 OFFSET :offset",
                param,new DataClassRowMapper<>(ThreadEntity.class));
    }

    @Override
    public int updateThreadOkuma(ThreadAddForm threadAddForm,int thread_id){
        var param = new MapSqlParameterSource();
        param.addValue("id",thread_id);
        param.addValue("thread_title",threadAddForm.getTitle());
        param.addValue("comment",threadAddForm.getComment());
        param.addValue("image_name",threadAddForm.getImage_name());
        param.addValue("image_base64",threadAddForm.getImage_base64());

        return jdbcTemplate.update("UPDATE threads " +
                "SET " +
                "thread_title = :thread_title," +
                "comment = :comment," +
                "image_name= :image_name," +
                "image_base64= :image_base64," +
                "updated_at= now()" +
                "WHERE id = :id",param);
    }

    @Override
    public List<ThreadEntity> searchThread(int offset, String tag, String order, String keyword) {
        var param = new MapSqlParameterSource();
        String query = "SELECT * FROM threads ";

        if (!tag.isEmpty()) {
            query += "WHERE id IN (SELECT thread_id FROM threads_tags WHERE tag = '"+tag+"') ";
            param.addValue("tag", tag);
        }

        if (!keyword.isEmpty() && keyword.length() > 0) {
            if (!tag.isEmpty() && tag.length() > 1) {
                query += "AND thread_title LIKE :keyword ";
            } else {
                query += "WHERE thread_title LIKE :keyword ";
            }
            param.addValue("keyword", "%" + keyword + "%");
        }

        switch (order) {
            case "並び替え":
            case "新しい順":
                query += "ORDER BY id desc";
                break;
            case "古い順":
                query += "ORDER BY id asc";
                break;
            default:
                query += "ORDER BY id asc";
        }

//        query += " LIMIT :offset";
//
//        param.addValue("offset", offset);

        System.out.println(query);
        return jdbcTemplate.query(query, param, new DataClassRowMapper<>(ThreadEntity.class));
    }

    @Override
    public List<ThreadEntity> searchOffsetThread(int offset, String tag, String order, String keyword){
        var param = new MapSqlParameterSource();
        String query = "SELECT * FROM threads ";

        if (!tag.isEmpty() && tag.length() > 0) {
            query += "WHERE id IN (SELECT thread_id FROM threads_tags WHERE tag = :tag) ";
            param.addValue("tag", tag);
        }

        if (!keyword.isEmpty() && keyword.length() > 0) {
            if (!tag.isEmpty() && tag.length() > 1) {
                query += "AND thread_title LIKE :keyword ";
            } else {
                query += "WHERE thread_title LIKE :keyword ";
            }
            param.addValue("keyword", "%" + keyword + "%");
        }

        switch (order) {
            case "並び替え":
            case "新しい順":
                query += "ORDER BY id desc";
                break;
            case "古い順":
                query += "ORDER BY id asc";
                break;
            default:
                query += "ORDER BY id asc";
        }

        query += " LIMIT 20 OFFSET :offset";

        param.addValue("offset", offset);

        return jdbcTemplate.query(query, param, new DataClassRowMapper<>(ThreadEntity.class));
    }

    @Override
    public List<ThreadEntity> findThreadOffsetByUser(int user_id, int offset){
        var param = new  MapSqlParameterSource();
        param.addValue("creator",user_id);
        param.addValue("offset", offset);
        return jdbcTemplate.query("SELECT * " +
                "FROM threads " +
                "WHERE creator = :creator " +
                "ORDER BY id desc " +
                "LIMIT 20 " +
                "OFFSET :offset", param, new DataClassRowMapper<>(ThreadEntity.class));
    }

    @Override
    public List<ThreadEntity> findThreadByUser(int user_id){
        var param = new  MapSqlParameterSource();
        param.addValue("creator",user_id);
        return jdbcTemplate.query("SELECT * " +
                        "FROM threads " +
                        "WHERE creator = :creator "+
                        "ORDER BY id desc ",
                param, new DataClassRowMapper<>(ThreadEntity.class));
    }

    @Override
    public List<ThreadEntity> findFavoriteOffsetThreadByUser(int user_id, int offset){
        var param = new MapSqlParameterSource();
        param.addValue("creator", user_id);
        param.addValue("offset", offset);
        return jdbcTemplate.query("SELECT * " +
                        "FROM threads " +
                        "WHERE id IN ( " +
                        "SELECT thread_id " +
                        "FROM favorite_threads " +
                        "WHERE user_id = :creator) " +
                        "LIMIT 20 " +
                        "OFFSET :offset",
                param, new DataClassRowMapper<>(ThreadEntity.class));
    }

    @Override
    public List<ThreadEntity> findFavoriteThreadByUser(int user_id){
        var param = new MapSqlParameterSource();
        param.addValue("creator", user_id);
        return jdbcTemplate.query("SELECT * " +
                        "FROM threads " +
                        "WHERE id IN ( " +
                        "SELECT thread_id " +
                        "FROM favorite_threads " +
                        "WHERE user_id = :creator) ",
                param, new DataClassRowMapper<>(ThreadEntity.class));
    }

    @Override
    public void userThreadAllDel(int user_id){
        var param=new MapSqlParameterSource();
        param.addValue("creator",user_id);
        jdbcTemplate.update("DELETE FROM threads " +
                "WHERE creator = :creator",param);
    }
}
