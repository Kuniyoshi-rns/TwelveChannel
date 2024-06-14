package com.example.TwelveChannel.Thread;

import java.util.List;

public interface IThreadService {

    public List<ThreadEntity> threadAll();

    public ThreadEntity findByIdThread(int thread_id);

    public List<ThreadEntity> findByCreatorThread(int user_id);

    public int insertThread(ThreadEntity threadEntity);

    public int updateThread(ThreadEntity threadEntity);

    public int deleteThread(int thread_id);

    public void addViewCount(int id);
}
