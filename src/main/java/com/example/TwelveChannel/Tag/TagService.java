package com.example.TwelveChannel.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements ITagService{
    @Autowired
    TagRepository tagRepository;
    @Override
    public List<UserTagEntity> userTag(int user_id){return tagRepository.userTag(user_id);}

    @Override
    public List<ThreadTagEntity> threadTag(int thread_id){return tagRepository.threadTag(thread_id);}

    @Override
    public int userTagInsert(int user_id,String tag){return tagRepository.userTagInsert(user_id,tag);}

    @Override
    public  int userTagDelete(int user_id,String tag){return tagRepository.userTagDelete(user_id,tag);}
}
