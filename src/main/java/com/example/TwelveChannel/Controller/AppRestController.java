package com.example.TwelveChannel.Controller;

import com.example.TwelveChannel.Comment.CommentEntity;
import com.example.TwelveChannel.Comment.CommentForm;
import com.example.TwelveChannel.Comment.ICommentService;
import com.example.TwelveChannel.Favorite.FavoriteEntity;
import com.example.TwelveChannel.Favorite.IFavoriteService;
import com.example.TwelveChannel.Tag.ITagService;
import com.example.TwelveChannel.Tag.TagCountEntity;
import com.example.TwelveChannel.Tag.UserTagEntity;
import com.example.TwelveChannel.Thread.IThreadService;
import com.example.TwelveChannel.Thread.ThreadEntity;
import com.example.TwelveChannel.User.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppRestController {

    List<CommentEntity> list = null;

    int commentCount = 0;

    @Autowired
    IThreadService threadService;
    @Autowired
    ICommentService commentService;

    @Autowired
    IFavoriteService favoriteService;

    @Autowired
    ITagService tagService;

    @Autowired
    HttpSession session;

    @GetMapping("view-comment/{threadId}")
    public ResponseEntity<List<CommentEntity>> viewAll(@PathVariable("threadId") int threadId){
        list = commentService.getCommentByThread(threadId);
        commentCount = 0; //後から変更
        List<CommentEntity> returnList;
        if(list.size() < commentCount) {
            returnList = list.subList(0,commentCount);
            System.out.println(list.size());
        }else{
            returnList = list;
        }
        return new ResponseEntity<>(returnList, HttpStatus.OK);
    }

    @GetMapping("view-comment-reload")
    public ResponseEntity<List<CommentEntity>> reloadViewAll(){
        List<CommentEntity> returnList;
        int prevCount = commentCount;
        commentCount += 5; //後から変更
        if(list.size() > commentCount) {
            returnList = list.subList(prevCount,commentCount);
            System.out.println(list.size());
        }else{
            returnList = list.subList(prevCount, list.size());
            System.out.println(list.size());
        }
        return new ResponseEntity<>(returnList, HttpStatus.OK);
    }

    @GetMapping("isFavorite/{threadId}")
    public ResponseEntity<Boolean> isFavorite(@PathVariable("threadId") int threadId){
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        boolean isFavorite = favoriteService.isFavorite(userEntity.id(),threadId);
//        Boolean isFavorite = favoriteService.isFavorite(1,threadId);
        System.out.println(isFavorite);
        return new ResponseEntity<>(isFavorite, HttpStatus.OK);
    }

    @PostMapping("insert-comment/{threadId}")
    public ResponseEntity<CommentEntity> writeCmt(@PathVariable("threadId") int threadId, @RequestBody CommentForm commentForm){
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        var sample = commentService.insertAndGet(commentForm,threadId,userEntity.id());
//        var sample = commentService.insertAndGet(commentForm,threadId,1);
        return new ResponseEntity<>(sample,HttpStatus.OK);
    }

    @DeleteMapping("delete-comment/{commentId}")
    public ResponseEntity<CommentEntity> deleteCmt(@PathVariable("commentId") int commentId){
        System.out.println(commentId);
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("favorite/{threadId}")
    public ResponseEntity<FavoriteEntity> favoriteInsert(@PathVariable("threadId") int threadId){
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        favoriteService.insertFavorite(userEntity.id(),threadId);
//        favoriteService.insertFavorite(1,threadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("favorite/{threadId}")
    public ResponseEntity<FavoriteEntity> favoriteDelete(@PathVariable("threadId") int threadId){
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        favoriteService.deleteFavorite(userEntity.id(), threadId);
//        favoriteService.deleteFavorite(1,threadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("deleteThread/{threadId}")
    public ResponseEntity<ThreadEntity> deleteThread(@PathVariable("threadId") int threadId){
        System.out.println(threadId);
        threadService.deleteThread(threadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("myTag/{tagValue}")
    public ResponseEntity<UserEntity> insertTag(@PathVariable("tagValue") String tag){
        System.out.println(tag+"を追加");
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        tagService.userTagInsert(userEntity.id(),tag);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("myTag/{tagValue}")
    public ResponseEntity<UserTagEntity> deleteTag(@PathVariable("tagValue") String tag){
        System.out.println(tag+"を削除");
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        tagService.userTagDelete(userEntity.id(),tag);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getTags")
    public ResponseEntity<List<UserTagEntity>> myTags(){
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        return new ResponseEntity<>(tagService.userTag(userEntity.id()),HttpStatus.OK);
    }

    @GetMapping("suggest/{term}")
    public ResponseEntity<List<TagCountEntity>> suggest(@PathVariable("term") String term){
        return new ResponseEntity<>(tagService.TagCount(term),HttpStatus.OK);
    }

}
