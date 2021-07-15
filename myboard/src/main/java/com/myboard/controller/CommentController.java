package com.myboard.controller;

import com.myboard.dto.CommentSaveForm;
import com.myboard.security.PrincipalDetails;
import com.myboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postsId}/comments/new")
    public String saveComment(@PathVariable("postsId")Long postId,
                              @Valid CommentSaveForm commentSaveForm, BindingResult result,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info(commentSaveForm.toString());
        commentService.createComment(commentSaveForm,postId, principalDetails.getUsername());
        return "redirect:/posts/"+postId;
    }

}
