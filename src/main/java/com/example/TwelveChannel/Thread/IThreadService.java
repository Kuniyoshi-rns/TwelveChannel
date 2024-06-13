package com.example.TwelveChannel.Thread;

import java.util.List;

public interface IThreadService {

    public List<ThreadEntity> TreadAll();

    public ThreadEntity FindbyIdthread(int thread_id);

    public int InsertThread(ThreadEntity threadEntity);

    public int UpdateThread(ThreadEntity threadEntity);

    public int DeleteThread(int thread_id);

    public void AddViewCount(int id);
}
