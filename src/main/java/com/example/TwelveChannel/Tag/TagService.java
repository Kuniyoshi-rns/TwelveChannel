package com.example.TwelveChannel.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService implements ITagService{
    @Autowired
    TagRepository tagRepository;
    @Override
    public String userTag(int user_id){return tagRepository.userTag(user_id).get(0);}

    @Override
    public String threadTag(int thread_id){return tagRepository.threadTag(thread_id).get(0);}
}
