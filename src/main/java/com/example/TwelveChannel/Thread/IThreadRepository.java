package com.example.TwelveChannel.Thread;

import java.util.List;

public interface IThreadRepository {

    List<ThreadEntity> threadAll();

    ThreadEntity findByIdThread(int thread_id);

    List<ThreadEntity> findByCreatorThread(int user_id);

    int insertThread(ThreadEntity threadEntity);

    int updateThread(ThreadEntity threadEntity);

    int deleteThread(int thread_id);

    void addViewCount(int id);

    int insertThreadOkuma(ThreadAddForm threadAddForm,int userId);
    List<ThreadEntity> findThread(int offset);

    int updateThreadOkuma(ThreadAddForm threadAddForm,int thread_id);

    List<ThreadEntity> searchThread(int offset,String tag,String order,String keyword);

    List<ThreadEntity> searchOffsetThread(int offset, String tag, String order, String keyword);

    List<ThreadEntity> findThreadOffsetByUser(int user_id, int offset);

    List<ThreadEntity> findThreadByUser(int user_id);

    List<ThreadEntity> findFavoriteOffsetThreadByUser(int user_id, int offset);

    List<ThreadEntity> findFavoriteThreadByUser(int user_id);


    List<ThreadEntity> recommendationThread(int user_id);

    List<ThreadEntity> recommendationOffsetThread(int user_id,int offset);

    void userThreadAllDel(int user_id);

}
