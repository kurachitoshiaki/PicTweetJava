package com.example.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.business.domain.User;
import com.example.business.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public ModelAndView registration(ModelAndView mav) {
        mav.addObject("user", new User());
        mav.setViewName("/user/registration");
        return mav;
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registration(User user, ModelAndView mav) {
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            mav.setViewName("redirect:/user/registration");
            return mav;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
        mav.setViewName("redirect:/user/login");
        return mav;
    }
    @RequestMapping(value = "ログインページのパスを入力してください", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mav) {
        // addObjectメソッドを使ってインスタンス化したuserをmavに追加してください
        // setViewNameメソッドを使ってログイン後に表示するhtmlを指定してください
        return mav;
    }
    @ModelAttribute(name = "login_user")
    public UserDetails setLoginUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

}