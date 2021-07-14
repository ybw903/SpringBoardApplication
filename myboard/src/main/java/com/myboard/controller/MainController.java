package com.myboard.controller;

import com.myboard.domain.Posts;
import com.myboard.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostsService postsService;

    @GetMapping("/")
    public String main(Model model) {
        List<Posts> posts = postsService.getAll();
        model.addAttribute("posts",posts);
        return "main";
    }
}
