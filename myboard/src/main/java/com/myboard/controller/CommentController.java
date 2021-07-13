package com.myboard.controller;

import com.myboard.dto.CommentCreateDto;
import com.myboard.dto.CommentSaveForm;
import com.myboard.security.PrincipalDetails;
import com.myboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postsId}/comments/new")
    public String saveComment(@PathVariable("postsId")Long postId,
                              CommentSaveForm commentSaveForm,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.createComment(commentSaveForm,postId, principalDetails.getUsername());
        return "redirect:/posts/"+postId;
    }

}
