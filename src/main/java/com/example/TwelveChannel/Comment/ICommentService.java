package com.example.TwelveChannel.Comment;

import java.util.List;

public interface ICommentService {
    List<CommentEntity> getCommentByThread(int thread_id);

    int insertComment(CommentForm commentForm,int thread_id,int user_id);

    int deleteComment(int id);

    List<CommentEntity> getCommentByUser(int user_id);

    int getCommentListByThread(int threadId);
    CommentEntity insertAndGet(CommentForm commentForm,int thread_id,int user_id);
    List<CommentCountHome> getCommentListAllThreadHome();
    List<CommentEntity> getCommentOffsetByUser(int user_id, int offset);

    void userCommentAllDel(int user_id);
}
