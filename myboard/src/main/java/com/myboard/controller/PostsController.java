package com.myboard.controller;

import com.myboard.domain.Posts;
import com.myboard.dto.CommentSaveForm;
import com.myboard.dto.PostForm;
import com.myboard.dto.PostUpdateForm;
import com.myboard.security.PrincipalDetails;
import com.myboard.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@Slf4j
@SessionAttributes("postUpdateForm")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/posts/{id}")
    public String readPost(@PathVariable("id") Long postId,Model model) {
        model.addAttribute("post", postsService.read(postId));
        model.addAttribute("commentSaveForm", new CommentSaveForm());
        return "posts/post";
    }

    @GetMapping("/posts/new")
    public String createPosts(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "posts/postForm";
    }

    @PostMapping("/posts/new")
    public String savePosts(@Valid PostForm postForm, BindingResult result, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(result.hasErrors()) {
            return"posts/postForm";
        }

        String userEmail = principalDetails.getUsername();
        postsService.add(postForm, userEmail);
        return "redirect:/";
    }

    @GetMapping("/posts/{postId}/edit")
    public String updatePosts(@PathVariable("postId")Long postId, Model model) {

        Posts post = postsService.findPost(postId);
        PostUpdateForm postUpdateForm = PostUpdateForm.builder()
                                                .id(post.getId())
                                                .title(post.getTitle())
                                                .content(post.getContent())
                                                .build();
        model.addAttribute("postUpdateForm",postUpdateForm);

        return"/posts/editPostForm";
    }

    @PostMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long postId,
                             @Valid PostUpdateForm postUpdateForm,
                             BindingResult result,
                             SessionStatus sessionStatus) {
        if(result.hasErrors()) {
            return "/posts/editPostForm";
        }
        postsService.update(postId, postUpdateForm);
        sessionStatus.setComplete();
        return "redirect:/posts/"+postId;
    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postsService.delete(postId);
        return "redirect:/";
    }

}
