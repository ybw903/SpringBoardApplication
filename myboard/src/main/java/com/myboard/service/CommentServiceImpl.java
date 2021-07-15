package com.myboard.service;

import com.myboard.domain.*;
import com.myboard.dto.CommentCreateDto;
import com.myboard.dto.CommentSaveForm;
import com.myboard.dto.CommentUpdateForm;
import com.myboard.exception.CommentNotFoundException;
import com.myboard.exception.PostsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    static final boolean DELETE_SUCCESS = true;
    static final boolean DELETE_FAILED = false;

    private final CommentRepository commentRepository;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(CommentSaveForm commentSaveForm, Long postsId, String email) {

        CommentCreateDto commentCreateDto = null;
        if(commentSaveForm.getCommentId() != null) {
            long supCommentId =commentSaveForm.getCommentId();
            Comment supComment = commentRepository.getOne(supCommentId);
            commentCreateDto = new CommentCreateDto(commentSaveForm.getContent(), supComment);
        } else {
            commentCreateDto = new CommentCreateDto(commentSaveForm.getContent(), null);
        }
        Posts posts = postsRepository.findById(postsId).orElseThrow(() -> new PostsNotFoundException("delete: Posts not found by : " + postsId));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found User: " + email));

        Comment newComment =  Comment.createComment(commentCreateDto, posts, user);

        commentRepository.save(newComment);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment get(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("not found comment :" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsWithPosts(Long postsId) {
        Posts posts = postsRepository.findById(postsId).orElseThrow(() -> new PostsNotFoundException("delete: Posts not found by : " + postsId));
        return commentRepository.findAllByPosts(posts);
    }

    @Override
    @Transactional
    public Comment update(Long id, CommentUpdateForm commentUpdateForm) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("not found comment :" + id));
        comment.updateComment(commentUpdateForm);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if(!commentRepository.findById(id).isPresent())
            throw new CommentNotFoundException("not found comment :" + id);
        commentRepository.deleteById(id);
        if(!commentRepository.findById(id).isPresent())
            return DELETE_SUCCESS;
        else
            return DELETE_FAILED;

    }
}
