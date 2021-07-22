package com.myboard.controller;

import com.myboard.domain.Comment;
import com.myboard.domain.Posts;
import com.myboard.dto.*;
import com.myboard.security.PrincipalDetails;
import com.myboard.service.CommentService;
import com.myboard.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@SessionAttributes("postUpdateForm")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final CommentService commentService;

    @GetMapping("/posts/{id}")
    public String readPost(@PathVariable("id") Long postId,Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        model.addAttribute("user", Objects.isNull(principalDetails)?"":principalDetails.getUsername());
        model.addAttribute("post", PostResponseDto.of(postsService.read(postId)));
        List<Comment> commentsWithPosts = commentService.getCommentsWithPosts(postId);
        model.addAttribute("comments",
                commentsWithPosts.stream().map(CommentResponseDto::of).collect(Collectors.toList())
        );
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
                             SessionStatus sessionStatus,
                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Posts posts = postsService.findPost(postId);
        String email = principalDetails.getUsername();
        if(!posts.getAuthor().getEmail().equals(email))
            return "redirect:/posts/"+postId;

        if(result.hasErrors()) {
            return "/posts/editPostForm";
        }

        postsService.update(postId, postUpdateForm);
        sessionStatus.setComplete();
        return "redirect:/posts/"+postId;
    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Posts posts = postsService.findPost(postId);
        String email = principalDetails.getUsername();
        if(!posts.getAuthor().getEmail().equals(email))
            return "redirect:/";
        postsService.delete(postId);
        return "redirect:/";
    }

    @PostMapping("/posts/{postsId}/like")
    public String likePost(@PathVariable("postsId") Long postId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postsService.like(postId, principalDetails.getUsername());
        return "redirect:/posts/" + postId;
    }
}
