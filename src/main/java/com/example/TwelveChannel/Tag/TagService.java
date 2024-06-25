package com.example.TwelveChannel.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public int threadTagInsert(int thread_id,String tag){
        return tagRepository.threadTagInsert(thread_id,tag);
    }

    @Override
    public List<ThreadTagEntity> threadTagAllFind() {
        return tagRepository.threadTagAllFind();
    }

    @Override
    public int threadTagDelete(int thread_id, String tag) {
        return tagRepository.threadTagDelete(thread_id,tag);
    }

    @Override
    public List<TagCountEntity> TagCount(String keyword){return tagRepository.TagCount(keyword);}

    @Transactional
    @Override
    public void threadTagsInsert(int tread_id,String[] tags){
        for (String tag : tags){
            threadTagInsert(tread_id,tag);
        }
    }

    @Transactional
    @Override
    public void threadTagsDelete(int thread_id, String[] tags) {
        for (String tag : tags) {
            threadTagDelete(thread_id, tag);
        }
      
    @Override
    public void userTagAllDel(int user_id){
        tagRepository.userTagAllDel(user_id);
    }
}
