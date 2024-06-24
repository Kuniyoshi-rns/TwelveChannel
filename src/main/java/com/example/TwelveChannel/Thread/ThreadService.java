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

    @Override
    public int updateThreadOkuma(ThreadAddForm threadAddForm, int thread_id) {
        return threadRepository.updateThreadOkuma(threadAddForm,thread_id);
    }

    @Override
    public List<ThreadEntity> searchThread(int offset, String tag, String order, String keyword) {
        return threadRepository.searchThread(offset, tag, order, keyword);
    }

    @Override
    public List<ThreadEntity> searchOffsetThread(int offset, String tag, String order, String keyword) {
        return threadRepository.searchOffsetThread(offset, tag, order, keyword);
    }

    @Override
    public List<ThreadEntity> findThreadOffsetByUser(int user_id, int offset) {
        return threadRepository.findThreadOffsetByUser(user_id, offset);
    }

    @Override
    public List<ThreadEntity> findThreadByUser(int user_id) {
        return threadRepository.findThreadByUser(user_id);
    }

    @Override
    public List<ThreadEntity> findFavoriteOffsetThreadByUser(int user_id, int offset) {
        return threadRepository.findFavoriteOffsetThreadByUser(user_id,offset);
    }

    @Override
    public List<ThreadEntity> findFavoriteThreadByUser(int user_id) {
        return threadRepository.findFavoriteThreadByUser(user_id);
    }

    @Override
    public List<ThreadEntity> recommendationThread(int user_id){
        return threadRepository.recommendationThread(user_id);
    }

    @Override
    public List<ThreadEntity> recommendationOffsetThread(int user_id,int offset){
        return threadRepository.recommendationOffsetThread(user_id,offset);
    }
}
