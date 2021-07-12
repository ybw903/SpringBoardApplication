package com.myboard.controller;

import com.myboard.domain.Posts;
import com.myboard.dto.PostSaveRequestDto;
import com.myboard.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/posts/{id}")
    public String readPost(@PathVariable("id") Long postId,Model model) {
        model.addAttribute("post", postsService.get(postId));
        return "posts/post";
    }

    @GetMapping("/posts/new")
    public String createPosts(Model model) {
        model.addAttribute("form", new PostSaveRequestDto());
        return "posts/postForm";
    }

    @PostMapping("/posts/new")
    public String savePosts(PostSaveRequestDto form) {
        postsService.add(form);
        return "redirect:/";
    }

    @GetMapping("/posts/{postId}/edit")
    public String updatePosts(@PathVariable("postId")Long postId, Model model) {

        Posts post = postsService.get(postId);

        model.addAttribute("post", post);
        model.addAttribute("form", new PostSaveRequestDto());

        return"/posts/editPostForm";
    }

    @PostMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long postId, PostSaveRequestDto form) {
        Posts posts = postsService.get(postId);

        postsService.update(postId, form);
        return "redirect:/posts/"+postId;
    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postsService.delete(postId);
        return "redirect:/";
    }

}
