package com.example.TwelveChannel.Thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService implements IThreadService{

    @Autowired
    ThreadRepository threadRepository;

    @Override
    public List<ThreadEntity> threadAll(){
        return threadRepository.threadAll();
    }

    @Override
    public ThreadEntity findByIdThread(int thread_id) {
        return threadRepository.findByIdThread(thread_id);
    }

    @Override
    public List<ThreadEntity> findByCreatorThread(int user_id){
        return threadRepository.findByCreatorThread(user_id);
    }

    @Override
    public int insertThread(ThreadEntity threadEntity){
        return threadRepository.insertThread(threadEntity);
    }

    @Override
    public int updateThread(ThreadEntity threadEntity){
        return threadRepository.updateThread(threadEntity);
    }

    @Override
    public int deleteThread(int thread_id){
        return threadRepository.deleteThread(thread_id);
    }

    @Override
    public void addViewCount(int id){
        threadRepository.addViewCount(id);
    }

    @Override
    public int insertThreadOkuma(ThreadAddForm threadAddForm,int userId){
        return threadRepository.insertThreadOkuma(threadAddForm,userId);
    }

    @Override
    public List<ThreadEntity> findThread(int offset) {
        return threadRepository.findThread(offset);
    }
}
