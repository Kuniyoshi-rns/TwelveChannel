package com.example.TwelveChannel.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService{
    @Autowired
    ICommentRepository commentRepository;
    @Override
    public List<CommentEntity> getCommentByThread(int thread_id){
        return commentRepository.getCommentByThread(thread_id);
    }

    @Override
    public int insertComment(CommentForm commentForm,int thread_id,int user_id){
        return commentRepository.insertComment(commentForm,thread_id,user_id);
    }

    @Override
    public int deleteComment(int id){
        return commentRepository.deleteComment(id);
    }
    @Override
    public List<CommentEntity> getCommentByUser(int user_id){
        return commentRepository.getCommentByUser(user_id);
    }

    @Override
    public int getCommentListByThread(int threadId){
        return commentRepository.getCommentListByThread(threadId);
    }

    @Override
    public CommentEntity insertAndGet(CommentForm commentForm, int thread_id, int user_id) {
        return commentRepository.insertAndGet(commentForm,thread_id,user_id);
    }
    @Override
    public List<CommentCountHome> getCommentListAllThreadHome(){return commentRepository.getCommentListAllThreadHome();}

    @Override
    public List<CommentEntity> getCommentOffsetByUser(int user_id, int offset) {
        return commentRepository.getCommentOffsetByUser(user_id,offset);
    }
    @Override
    public void userCommentAllDel(int user_id){
        commentRepository.userCommentAllDel(user_id);
    }
}
