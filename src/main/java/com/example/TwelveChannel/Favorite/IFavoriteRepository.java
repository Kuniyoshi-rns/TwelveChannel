package com.example.TwelveChannel.Favorite;

import com.example.TwelveChannel.Thread.ThreadEntity;

import java.util.List;

public interface IFavoriteRepository {

    List<ThreadEntity> getFavoriteListByUser(int userId);

    int getFavoriteListByThread(int threadId);

    boolean isFavorite(int userId,int threadId);

    void insertFavorite(int userId,int threadId);

    void deleteFavorite(int userId, int threadId);

    List<FavoriteThreadCount> favoriteThreadCountHome();

    List<ThreadEntity> getFavoThreadList(int user_id);


}
