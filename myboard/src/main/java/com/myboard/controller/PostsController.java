package com.myboard.controller;

import com.myboard.domain.Posts;
import com.myboard.dto.CommentSaveForm;
import com.myboard.dto.PostSaveForm;
import com.myboard.dto.PostUpdateForm;
import com.myboard.security.PrincipalDetails;
import com.myboard.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/posts/{id}")
    public String readPost(@PathVariable("id") Long postId,Model model) {
        model.addAttribute("post", postsService.read(postId));
        model.addAttribute("form", new CommentSaveForm());
        return "posts/post";
    }

    @GetMapping("/posts/new")
    public String createPosts(Model model) {
        model.addAttribute("form", new PostSaveForm());
        return "posts/postForm";
    }

    @PostMapping("/posts/new")
    public String savePosts(PostSaveForm form, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String userEmail = principalDetails.getUsername();
        postsService.add(form, userEmail);
        return "redirect:/";
    }

    @GetMapping("/posts/{postId}/edit")
    public String updatePosts(@PathVariable("postId")Long postId, Model model) {

        Posts post = postsService.findPost(postId);

        model.addAttribute("post", post);
        model.addAttribute("form", new PostSaveForm());

        return"/posts/editPostForm";
    }

    @PostMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long postId, PostUpdateForm form) {
        postsService.update(postId, form);
        return "redirect:/posts/"+postId;
    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postsService.delete(postId);
        return "redirect:/";
    }

}
