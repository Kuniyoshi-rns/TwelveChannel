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
}
