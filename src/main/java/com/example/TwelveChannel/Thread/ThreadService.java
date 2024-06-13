package com.example.TwelveChannel.Thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService implements IThreadService{

    @Autowired
    ThreadRepository threadRepository;

    @Override
    public List<ThreadEntity> TreadAll(){
        return threadRepository.TreadAll();
    }

    @Override
    public ThreadEntity FindbyIdthread(int thread_id) {
        return threadRepository.FindbyIdthread(thread_id);
    }

    @Override
    public int InsertThread(ThreadEntity threadEntity){
        return threadRepository.InsertThread(threadEntity);
    }

    @Override
    public int UpdateThread(ThreadEntity threadEntity){
        return threadRepository.UpdateThread(threadEntity);
    }

    @Override
    public int DeleteThread(int thread_id){
        return threadRepository.DeleteThread(thread_id);
    }

    @Override
    public void AddViewCount(int id){
        threadRepository.AddViewCount(id);
    }
}
