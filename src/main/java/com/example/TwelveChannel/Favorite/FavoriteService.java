package com.example.TwelveChannel.Favorite;

import com.example.TwelveChannel.Thread.ThreadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FavoriteService implements IFavoriteService{

    @Autowired
    IFavoriteRepository favoriteRepository;

    @Override
    public List<ThreadEntity> getFavoriteListByUser(int userId) {
        return favoriteRepository.getFavoriteListByUser(userId);
    }

    @Override
    public int getFavoriteListByThread(int threadId){
        return favoriteRepository.getFavoriteListByThread(threadId);
    }

    @Override
    public boolean isFavorite(int userId, int threadId) {
        return favoriteRepository.isFavorite(userId, threadId);
    }

    @Override
    public void insertFavorite(int userId, int threadId) {
        favoriteRepository.insertFavorite(userId, threadId);
    }

    @Override
    public void deleteFavorite(int userId, int threadId){
        favoriteRepository.deleteFavorite(userId,threadId);
    }
    @Override
    public List<FavoriteThreadCount> favoriteThreadCountHome(){return favoriteRepository.favoriteThreadCountHome();}

    @Override
    public List<ThreadEntity> getFavoThreadList(int user_id) {
        return favoriteRepository.getFavoThreadList(user_id);
    }

    @Override
    public void userfavoriteAllDel(int user_id){
        favoriteRepository.userfavoriteAllDel(user_id);
    }
}

