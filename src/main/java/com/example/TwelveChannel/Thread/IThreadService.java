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

    int insertThreadOkuma(ThreadAddForm threadAddForm,int userId);

    List<ThreadEntity> findThread(int offset);

    int updateThreadOkuma(ThreadAddForm threadAddForm,int thread_id);

    List<ThreadEntity> searchThread(int offset,String tag,String order,String keyword);

    List<ThreadEntity> searchOffsetThread(int offset, String tag, String order, String keyword);

    List<ThreadEntity> findThreadOffsetByUser(int user_id, int offset);

    List<ThreadEntity> findThreadByUser(int user_id);

    List<ThreadEntity> findFavoriteOffsetThreadByUser(int user_id, int offset);

    List<ThreadEntity> findFavoriteThreadByUser(int user_id);

    void userThreadAllDel(int user_id);

}
