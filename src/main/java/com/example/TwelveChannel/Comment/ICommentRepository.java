package com.example.TwelveChannel.Comment;

import java.util.List;

public interface ICommentRepository {
    List<CommentEntity> getCommentByThread(int thread_id);
    int insertComment(CommentForm commentForm,int thread_id,int user_id);
    int deleteComment(int id);

    List<CommentEntity> getCommentByUser(int user_id);
}
