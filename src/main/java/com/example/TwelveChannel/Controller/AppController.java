package com.example.TwelveChannel.Controller;

import com.example.TwelveChannel.Comment.CommentService;
import com.example.TwelveChannel.Favorite.FavoriteService;
import com.example.TwelveChannel.Tag.TagService;
import com.example.TwelveChannel.Thread.ThreadAddForm;
import com.example.TwelveChannel.Thread.ThreadService;
import com.example.TwelveChannel.User.Form.LoginForm;
import com.example.TwelveChannel.User.Form.SignUpForm;
import com.example.TwelveChannel.User.UserEntity;
import com.example.TwelveChannel.User.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String index(@ModelAttribute("loginForm")LoginForm loginForm){
        return "login";
    }

    @PostMapping("/login")//ログイン情報確認
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult, Model model){
        System.out.println(loginForm);
        if(bindingResult.hasErrors()) {
            return "login";
        }
        System.out.println(userService.findByIdUser(loginForm));
        if(userService.findByIdUser(loginForm)==null){
            model.addAttribute("loginError","ID、またはパスワードが異なります。");
            return "login";
        }
        return "home";
    }

    //新規登録
    @GetMapping("/signup")
    public String signup(@ModelAttribute("SignUpForm")SignUpForm signUpForm){
        return "signup";
        }

    @PostMapping("/signup")
    public String signup(@Validated@ModelAttribute("SignUpForm")SignUpForm signUpForm,BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "signup";
        }

        var loginform= new LoginForm();
        loginform.setLogin_id(signUpForm.getLogin_id());
        loginform.setPassword(signUpForm.getPassword());
        var checkuser = userService.findByIdUser(loginform);

        if (checkuser==null && signUpForm.getPassword().equals(signUpForm.getPasswordCheck())){
            userService.insertUser(signUpForm);
            return "home";
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
    public String addPost(@Validated @ModelAttribute("addThread") ThreadAddForm threadAddForm,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "addthread";
        }
//        UserEntity userEntity = (UserEntity) session.getAttribute("");
//        int userId = userEntity.id();
//        上記コードはセッションを実装した段階で使う。消さないで。
        int threadId = threadService.insertThreadOkuma(threadAddForm, 1);
        tagService.threadTagInsert(threadId, threadAddForm.getTag());
        return "redirect:/thread/" + threadId;
    }

    @GetMapping("/edit-thread/{thread_id}")
    public String editThread(@PathVariable("thread_id") int threadId, @ModelAttribute("editThread") ThreadAddForm threadAddForm, Model model) {
        var thread = threadService.findByIdThread(threadId);
        var threadTag = tagService.threadTag(threadId);
        threadAddForm.setTag(threadTag.get(0).tag());
        threadAddForm.setTitle(thread.thread_title());
        threadAddForm.setComment(thread.comment());
        threadAddForm.setImage_name(thread.image_name());
        threadAddForm.setImage_base64(thread.image_base64());
        model.addAttribute("editThread", threadAddForm);
        model.addAttribute("ThreadId", threadId);
        return "editthread";
    }

    @PostMapping("/edit-thread/{thread_id}")
    public String editThreadPost(@PathVariable("thread_id") int threadId, @Validated @ModelAttribute("editThread") ThreadAddForm threadAddForm,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "editthread";
        }
        tagService.threadTagDelete(threadId, threadAddForm.getTag());
        threadService.updateThreadOkuma(threadAddForm, threadId);
        tagService.threadTagInsert(threadId, threadAddForm.getTag());
        return "redirect:/thread/" + threadId;
    }

    //ホーム画面
    @GetMapping("/home")
    public String login(){
        return "home";
    }


    @GetMapping({"/thread","/thread/{thread_id}"})
    public String Thread(@PathVariable("thread_id") int thread_id, Model model){
        var loginuser = new UserEntity(2,"yamada","02");
        session.setAttribute("loginuser",loginuser);
        model.addAttribute("thread",threadService.findByIdThread(thread_id));
        model.addAttribute("tags",tagService.threadTag(thread_id));

        return "thread";
    }

    @GetMapping("/mypage")
    public String mypage(){ return "mypage";}
}

