package com.example.TwelveChannel.Tag;

import java.util.List;

public interface ITagRepository {
    List<String> userTag(int user_id);
    List<String> threadTag(int thread_id);
}
