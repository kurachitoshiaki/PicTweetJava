package com.example.web.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.business.domain.Tweet;
import com.example.business.repository.TweetRepository;

@Controller
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@PageableDefault(size = 5) Pageable pageable, ModelAndView mav, @AuthenticationPrincipal UserDetails userDetails) {
      Page<Tweet> tweets = tweetRepository.findAllByOrderByIdDesc(pageable);
      mav.addObject("tweets", tweets);
      mav.setViewName("tweet/index");
        return mav;
    }
    @RequestMapping(value = "/tweet/new", method = RequestMethod.GET)
    public ModelAndView newTweet(ModelAndView mav) {
        mav.setViewName("tweet/new");
        return mav;
    }
    @RequestMapping(value = "/tweet/new", method = RequestMethod.POST)
    public ModelAndView createTweet(Tweet newTweet, ModelAndView mav) {
        tweetRepository.saveAndFlush(newTweet);
        mav.setViewName("tweet/create");
        return mav;
    }
    @ModelAttribute(name = "login_user")
    public UserDetails setLoginUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
}