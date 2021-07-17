package com.myboard.controller;

import com.myboard.domain.Posts;
import com.myboard.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PostsService postsService;

    @GetMapping("/")
    public String main(Model model, @PageableDefault Pageable pageable) {
        Page<Posts> posts = postsService.getPosts(pageable);
        model.addAttribute("posts",posts);
        return "main";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword")String keyword,  Model model, @PageableDefault Pageable pageable) {
        Page<Posts> postsWithKeyword = postsService.getPostsWithTitle(keyword, pageable);
        model.addAttribute("posts", postsWithKeyword);
        return "main";
    }
}
