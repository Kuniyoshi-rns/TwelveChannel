package com.example.TwelveChannel.Controller;

import com.example.TwelveChannel.Comment.CommentEntity;
import com.example.TwelveChannel.Comment.CommentService;
import com.example.TwelveChannel.Favorite.FavoriteService;
import com.example.TwelveChannel.Tag.TagService;
import com.example.TwelveChannel.Thread.ThreadAddForm;
import com.example.TwelveChannel.Thread.ThreadEntity;
import com.example.TwelveChannel.Thread.ThreadService;
import com.example.TwelveChannel.User.Form.LoginForm;
import com.example.TwelveChannel.User.Form.SignUpForm;
import com.example.TwelveChannel.User.UserEntity;
import com.example.TwelveChannel.User.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    CommentService commentService;

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    TagService tagService;

    @Autowired
    ThreadService threadService;

    @Autowired
    UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping("/login")//ログイン画面
    public String index(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login";
    }

    @PostMapping("/login")//ログイン情報確認
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return "login";
        }
        UserEntity loginuser = userService.findByIdUser(loginForm);
        if (loginuser == null) {
            model.addAttribute("loginError", "ID、またはパスワードが異なります。");
            return "login";
        }
        session.setAttribute("loginuser",loginuser);
        return "redirect:/home";
    }

    //新規登録
    @GetMapping("/signup")
    public String signup(@ModelAttribute("SignUpForm") SignUpForm signUpForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute("SignUpForm") SignUpForm signUpForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        String getLoginId = signUpForm.getLogin_id();
        String getPassword = signUpForm.getPassword();

        var loginIdCheck = !(getLoginId.matches("[-_@+*;:#$%&A-Za-z0-9]+") || getLoginId.matches("\\w"));
        var passCheck = !(getPassword.matches("[-_@+*;:#$%&A-Za-z0-9]+") || getPassword.matches("\\w"));

        if(loginIdCheck || passCheck){
            if(loginIdCheck){model.addAttribute("messageSign", "半角英数字、または-_@+*;:#$%&が使えます");}
            if(passCheck){model.addAttribute("messagePass", "半角英数字、または-_@+*;:#$%&が使えます");}
            return "signup";
        }


        var loginform = new LoginForm();
        loginform.setLogin_id(signUpForm.getLogin_id());
        loginform.setPassword(signUpForm.getPassword());
        var checkuser = userService.findByIdUser(loginform);


        if (checkuser==null && signUpForm.getPassword().equals(signUpForm.getPasswordCheck())){
            int isInsert = userService.insertUser(signUpForm);
            if(isInsert == 1){
            var loginuser = userService.findByIdUser(loginform);
            session.setAttribute("loginuser",loginuser);
            return "redirect:/home";
            }else{
                model.addAttribute("checkuser","そのIDは存在しています");
            }
        }else if(checkuser!=null){
            model.addAttribute("checkuser","そのIDは存在しています");
        }else {
            model.addAttribute("checkuser", "パスワードが一致しません");
        }
        return "signup";
    }

    @GetMapping("/add-thread")
    public String addThread(@ModelAttribute("addThread") ThreadAddForm threadAddForm, Model model) {
        return "addthread";
    }

    @PostMapping("/add-thread")
    public String addPost(@Validated @ModelAttribute("addThread") ThreadAddForm threadAddForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addthread";
        }
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        int userId = userEntity.id();
//        上記コードはセッションを実装した段階で使う。消さないで。

        String getTags = threadAddForm.getTag();
        String[] tags = getTags.split(",");
        for (String tag : tags){
            if (tag.length() > 50){
                model.addAttribute("message", "タグは50文字以内にしてください");
                return "addthread";
            }
        }
        int threadId = threadService.insertThreadOkuma(threadAddForm, userId);
        tagService.threadTagsInsert(threadId, tags);
        return "redirect:/thread/" + threadId;
    }

    @GetMapping("/edit-thread/{thread_id}")
    public String editThread(@PathVariable("thread_id") int threadId, @ModelAttribute("editThread") ThreadAddForm threadAddForm, Model model) {
        var thread = threadService.findByIdThread(threadId);
        var threadTag = tagService.threadTag(threadId);
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");

        if(thread.creator() != userEntity.id()){
            return "redirect:/home";
        }

        String tags = threadTag.get(0).tag();

        for (int i=1 ; i < threadTag.size(); i++){
            tags = tags + "," + threadTag.get(i).tag();
        }
        threadAddForm.setTag(tags);
        threadAddForm.setTitle(thread.thread_title());
        threadAddForm.setComment(thread.comment());
        threadAddForm.setImage_name(thread.image_name());
        threadAddForm.setImage_base64(thread.image_base64());
        model.addAttribute("editThread", threadAddForm);
        model.addAttribute("ThreadId", threadId);
        return "editthread";
    }

    @PostMapping("/edit-thread/{thread_id}")
    public String editThreadPost(@PathVariable("thread_id") int threadId, @Validated @ModelAttribute("editThread") ThreadAddForm threadAddForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editthread";
        }

        String getTags = threadAddForm.getTag();
        String[] tags = getTags.split(",");
        for (String tag : tags){
            if (tag.length() > 50){
                model.addAttribute("message", "タグは50文字以内にしてください");
                return "editthread";
            }
        }
        tagService.threadTagsDelete(threadId, tags);
        threadService.updateThreadOkuma(threadAddForm, threadId);
        tagService.threadTagsInsert(threadId, tags);
        return "redirect:/thread/" + threadId;
    }


    @GetMapping({"/thread", "/thread/{thread_id}"})
    public String Thread(@PathVariable("thread_id") int thread_id, Model model) {
//        var loginuser = new UserEntity(1,"yamada","02");
//        session.setAttribute("loginuser",loginuser);
        model.addAttribute("thread", threadService.findByIdThread(thread_id));
        System.out.println(threadService.findByIdThread(thread_id));
        model.addAttribute("tags", tagService.threadTag(thread_id));

        return "thread";
    }

    @GetMapping("/home")
    public String home(@RequestParam(name = "page", defaultValue = "1") int page,
                       @RequestParam(name = "tag", defaultValue = "") String tag,
                       @RequestParam(name = "order", defaultValue = "") String order,
                       @RequestParam(name = "keyword", defaultValue = "") String keyword,
                       Model model) {

        int offset = (page-1)*20;
        System.out.println(offset);

        int thread_page=threadService.threadAll().size();
        System.out.println(thread_page);
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        if(userEntity==null){
            return "thread";
        }
        int user_id=userEntity.id();
        var thread=threadService.findThread(offset);

        if(tag.isEmpty() && keyword.isEmpty() && order.isEmpty()) {
            if(threadService.recommendationThread(user_id).isEmpty()){
                System.out.println("フォローしているタグ無し or タグを含むスレッド無し");
            }else {
                System.out.println("お勧め表示一覧");
                thread = threadService.recommendationOffsetThread(user_id, offset);
                thread_page=threadService.recommendationThread(user_id).size();
                for (var test : threadService.recommendationOffsetThread(user_id, offset)) {
                    System.out.println(test);
                }
            }
        }else{
            if(keyword.startsWith("#")){
                tag=keyword.substring(1);
                keyword="";
                System.out.println("タグ検索"+keyword+tag);
            }
            thread = threadService.searchThread(offset, tag, order, keyword);
            thread_page=thread.size();
            thread=threadService.searchOffsetThread(offset,tag,order,keyword);
//            for (var test : thread) {
//                System.out.println(test);
//            }
        }

        var all_tag=tagService.threadTagAllFind();
        var comment_count=commentService.getCommentListAllThreadHome();
        var favorite=favoriteService.favoriteThreadCountHome();
        model.addAttribute("threads",thread);
        model.addAttribute("tags",all_tag);
        model.addAttribute("comment_count",comment_count);
        model.addAttribute("favorites",favorite);

        model.addAttribute("offset",offset);
        model.addAttribute("tag",tag);
        model.addAttribute("order",order);
        model.addAttribute("keyword",keyword);
        if(thread_page%20==0){
            thread_page=thread_page/20;
        }else{
            thread_page=thread_page/20+1;
        }
        model.addAttribute("thread_page",page);
        model.addAttribute("thread_all",thread_page);

        return "home";
    }

    @GetMapping("/mypage")
    public String mypage(@RequestParam(name = "page", defaultValue = "1") int page,
                         @RequestParam(name = "menu", defaultValue = "1") int menu,
                         Model model){

        int offset = 20*(page-1);

        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        if(userEntity==null){
            return "thread";
        }
        int user_id=userEntity.id();
      
        System.out.println("マイページ遷移:menu="+menu);

        List<ThreadEntity> thread = null;
        List<CommentEntity> comment = null;
        int thread_page = 0;

        switch (menu) {
            case 1:
                thread = threadService.findThreadOffsetByUser(user_id, offset);
                thread_page = threadService.findThreadByUser(user_id).size();
                break;
            case 2:
                thread = threadService.findFavoriteOffsetThreadByUser(user_id, offset);
                thread_page = threadService.findFavoriteThreadByUser(user_id).size();
                break;
            case 3:
                comment = commentService.getCommentOffsetByUser(user_id, offset);
                thread_page = commentService.getCommentByUser(user_id).size();
                break;
            default:
                break;
        }

        System.out.println("全部で" + thread_page + "件");

        for (var test : commentService.getCommentOffsetByUser(user_id, offset)) {
            System.out.println(test);
        }

        var all_tag = tagService.threadTagAllFind();
        var comment_count = commentService.getCommentListAllThreadHome();
        var favorite = favoriteService.favoriteThreadCountHome();

        model.addAttribute("threads", thread);
        model.addAttribute("comments", comment);
        model.addAttribute("tags", all_tag);
        model.addAttribute("comment_count", comment_count);
        model.addAttribute("favorites", favorite);

        model.addAttribute("offset", offset);
        model.addAttribute("menu", menu);
        if (thread_page % 20 == 0 && thread_page != 0) {
            thread_page = thread_page / 20;
        } else {
            thread_page = thread_page / 20 + 1;
        }
        model.addAttribute("thread_page", page);
        model.addAttribute("thread_all", thread_page);
        return "mypage";
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute("loginForm") LoginForm loginForm) {
        session.invalidate();
        //session.removeAttribute("loginuser");
        if (session.getAttribute("loginuser") == null){
            System.out.println("破棄されました");
        }else {
            System.out.println("破棄できませんでした");
        }
        return "redirect:/login";
    }

    @Transactional
    @PostMapping("/withdrawal")
    public String withdrawal(){
        UserEntity userEntity = (UserEntity) session.getAttribute("loginuser");
        int userId = userEntity.id();
        tagService.userTagAllDel(userId);//●
        favoriteService.userfavoriteAllDel(userId);//●
        commentService.userCommentAllDel(userId);//●
        threadService.userThreadAllChange(userId);
        userService.deleteUser(userId);
        session.invalidate();
        return "redirect:/login";
    }
}

