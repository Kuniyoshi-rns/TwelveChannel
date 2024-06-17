package com.example.TwelveChannel.Controller;

import com.example.TwelveChannel.Comment.CommentEntity;
import com.example.TwelveChannel.Comment.CommentForm;
import com.example.TwelveChannel.Comment.ICommentService;
import com.example.TwelveChannel.Favorite.FavoriteEntity;
import com.example.TwelveChannel.Favorite.IFavoriteService;
import com.example.TwelveChannel.Thread.IThreadService;
import com.example.TwelveChannel.User.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppRestController {
    @Autowired
    ICommentService commentService;

    @Autowired
    IFavoriteService favoriteService;

    @Autowired
    HttpSession session;

    @GetMapping("view-comment/{threadId}")
    public ResponseEntity<List<CommentEntity>> viewAll(@PathVariable("threadId") int threadId){
        List<CommentEntity> list = commentService.getCommentByThread(threadId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("isFavorite/{threadId}")
    public ResponseEntity<Boolean> isFavorite(@PathVariable("threadId") int threadId){
        UserEntity userEntity = (UserEntity) session.getAttribute("");
        Boolean isFavorite = favoriteService.isFavorite(userEntity.id(),threadId);
        return new ResponseEntity<>(isFavorite, HttpStatus.OK);
    }

    @PostMapping("insert-comment/{threadId}")
    public ResponseEntity<CommentEntity> writeCmt(@PathVariable("threadId") int threadId, @RequestBody CommentForm commentForm){
        UserEntity userEntity = (UserEntity) session.getAttribute("");
//        commentService.insertComment(commentForm,threadId,userEntity.id());
        commentService.insertComment(commentForm,threadId,1);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete-comment/{commentId}")
    public ResponseEntity<CommentEntity> deleteCmt(@PathVariable("commentId") int commentId){
        System.out.println(commentId);
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("favorite-insert/{threadId}")
    public ResponseEntity<FavoriteEntity> favoriteInsert(@PathVariable("threadId") int threadId){
        UserEntity userEntity = (UserEntity) session.getAttribute("");
        favoriteService.insertFavorite(userEntity.id(),threadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //明日はいいねの削除をやる
    //isFavoriteメソッドは動作未確認





}
