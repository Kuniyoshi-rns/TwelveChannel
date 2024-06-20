package com.example.TwelveChannel.Tag;

import com.example.TwelveChannel.Thread.ThreadEntity;

import java.util.List;

public interface ITagService {
    List<UserTagEntity> userTag(int user_id);
    List<ThreadTagEntity> threadTag(int thread_id);

    int userTagInsert(int user_id,String tag);
    int userTagDelete(int user_id,String tag);

    int threadTagInsert(int thread_id,String tag);

    List<ThreadTagEntity> threadTagAllFind();

    int threadTagDelete(int thread_id,String tag);
}
