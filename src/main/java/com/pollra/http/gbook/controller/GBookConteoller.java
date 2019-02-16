package com.pollra.http.gbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/guestbook")
public class GBookConteoller {
    private static final Logger log = LoggerFactory.getLogger(GBookConteoller.class);

    @GetMapping("")
    public ModelAndView getGuestBookPage(){
        ModelAndView modelAndView = new ModelAndView("guestbook");
        return modelAndView;
    }
}
