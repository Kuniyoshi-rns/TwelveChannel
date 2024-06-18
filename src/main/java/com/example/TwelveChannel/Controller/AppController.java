package com.example.TwelveChannel.Controller;

import com.example.TwelveChannel.Comment.CommentService;
import com.example.TwelveChannel.Favorite.FavoriteService;
import com.example.TwelveChannel.Tag.TagService;
import com.example.TwelveChannel.Thread.ThreadService;
import com.example.TwelveChannel.User.Form.LoginForm;
import com.example.TwelveChannel.User.Form.SignUpForm;
import com.example.TwelveChannel.User.UserEntity;
import com.example.TwelveChannel.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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

    //ホーム画面
    @GetMapping("/home")
    public String login(){
        return "home";
    }

}

