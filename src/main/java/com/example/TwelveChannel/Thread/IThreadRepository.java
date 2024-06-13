package com.example.TwelveChannel.Thread;

import java.util.List;

public interface IThreadRepository {

    List<ThreadEntity> TreadAll();

    ThreadEntity FindbyIdthread(int thread_id);

    int InsertThread(ThreadEntity threadEntity);

    int UpdateThread(ThreadEntity threadEntity);

    int DeleteThread(int thread_id);

    void AddViewCount(int id);
}
