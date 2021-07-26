package com.myboard.service;

import com.myboard.domain.*;
import com.myboard.dto.CommentCreateDto;
import com.myboard.dto.CommentSaveForm;
import com.myboard.dto.CommentUpdateForm;
import com.myboard.exception.CommentNotFoundException;
import com.myboard.exception.PostsNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    CommentRepository commentRepository;

    @Mock
    PostsRepository postsRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    @DisplayName("성공적으로 상위 댓글이 작성되는 경우")
    @Test
    void createCommentSuccessfully() {
        final CommentSaveForm commentSaveForm = new CommentSaveForm();
        commentSaveForm.setContent("테스트 댓글");
        final Posts posts = mock(Posts.class);
        final User user = mock(User.class);

        given(postsRepository.findById(anyLong())).willReturn(Optional.of(posts));
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));

        commentService.createComment(commentSaveForm,1L,"");
        verify(postsRepository).findById(anyLong());
        verify(userRepository).findByEmail(anyString());
        verify(commentRepository).save(any(Comment.class));
    }

    @DisplayName("성공적으로 하위 댓글이 작성되는 경우")
    @Test
    void createSubCommentSuccessfully() {
        final CommentSaveForm commentSaveForm = new CommentSaveForm();
        commentSaveForm.setContent("테스트 댓글");
        commentSaveForm.setCommentId(1L);
        final Posts posts = mock(Posts.class);
        final User user = mock(User.class);
        final Comment supComment = mock(Comment.class);

        when(supComment.getRootComment()).thenReturn(supComment);

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(supComment));
        given(postsRepository.findById(anyLong())).willReturn(Optional.of(posts));
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));

        commentService.createComment(commentSaveForm,1L,"");
        verify(postsRepository).findById(anyLong());
        verify(userRepository).findByEmail(anyString());
        verify(commentRepository).findById(anyLong());
        verify(commentRepository).save(any(Comment.class));
    }

    @DisplayName("존재하지 않는 댓글에 하위 댓글을 작성할 경우, 예외 발생")
    @Test
    void createSubCommentFailure() {
        final CommentSaveForm commentSaveForm = new CommentSaveForm();
        commentSaveForm.setContent("테스트 댓글");
        commentSaveForm.setCommentId(1L);

        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());
        
        assertThrows(CommentNotFoundException.class, ()->commentService.createComment(commentSaveForm,1L,""));
        verify(commentRepository).findById(anyLong());
    }

    @DisplayName("존재하지 않는 게시글에 댓글을 작성하려는 경우, 예외 발생")
    @Test
    void createCommentFailureByPostsNotFound() {
        final CommentSaveForm commentSaveForm = new CommentSaveForm();
        commentSaveForm.setContent("테스트 댓글");
        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, ()->commentService.createComment(commentSaveForm,1L,""));
    }

    @DisplayName("존재하지 않는 사용자로 댓글을 작성하려는 경우, 예외 발생")
    @Test
    void createCommentFailureByUsernameNotFound() {
        final CommentSaveForm commentSaveForm = new CommentSaveForm();
        commentSaveForm.setContent("테스트 댓글");
        final Posts posts = mock(Posts.class);
        given(postsRepository.findById(anyLong())).willReturn(Optional.of(posts));
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, ()->commentService.createComment(commentSaveForm,1L,""));
    }

    @DisplayName("Id를 통해 댓글을 조회하는 경우")
    @Test
    void getCommentByIdSuccessfully() {
        Comment comment = mock(Comment.class);
        when(comment.getId()).thenReturn(1L);
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        Comment commentByGet = commentService.get(1L);

        assertThat(commentByGet.getId()).isEqualTo(comment.getId());
        verify(commentRepository).findById(anyLong());
    }
    
    @DisplayName("존재하지 않는 댓글 ID를 조회한 경우, 예외발생")
    @Test
    void getCommentByIdFailure() {
        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, ()->commentService.get(1L));
    }
    
    @DisplayName("게시글에 달린 댓글들을 성공적으로 조회하는 경우")
    @Test
    void getCommentWithPostsSuccessfully() {
        final Comment comment = mock(Comment.class);
        final List comments = mock(List.class);
        final Posts posts = mock(Posts.class);
        when(comments.get(0)).thenReturn(comment);
        given(postsRepository.findById(anyLong())).willReturn(Optional.of(posts));
        given(commentRepository.findAllByPostsOrderByRootCommentAscLevelAsc(posts)).willReturn(comments);

        List<Comment> commentsByGetCommentWithPosts =  commentService.getCommentsWithPosts(1L);

        assertThat(commentsByGetCommentWithPosts.get(0)).isEqualTo(comments.get(0));
        verify(postsRepository).findById(anyLong());
        verify(commentRepository).findAllByPostsOrderByRootCommentAscLevelAsc(any(Posts.class));
    }
    
    @DisplayName("존재하지 않는 게시글의 댓글들을 조회하는 경우, 예외 발생")
    @Test
    void getCommentWithPostsFailure() {
        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(PostsNotFoundException.class ,()->commentService.getCommentsWithPosts(1L));
    }

    @DisplayName("댓글을 성공적으로 갱신하는 경우")
    @Test
    void getCommentByUpdateSuccessfully() {
        final CommentUpdateForm commentUpdateForm = new CommentUpdateForm();
        commentUpdateForm.setContent("변경된 게시글");
        final Comment comment = mock(Comment.class);
        final Comment updatedComment = mock(Comment.class);

        when(comment.getId()).thenReturn(1L);
        when(comment.getContent()).thenReturn("변경되기 이전 게시글");
        when(updatedComment.getId()).thenReturn(1L);
        when(updatedComment.getContent()).thenReturn(commentUpdateForm.getContent());
        
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));
        given(commentRepository.save(comment)).willReturn(updatedComment);

        Comment getCommentByUpdate =  commentService.update(1L,commentUpdateForm);

        assertThat(getCommentByUpdate.getId()).isEqualTo(comment.getId());
        assertThat(getCommentByUpdate.getContent()).isNotEqualTo(comment.getContent());
        verify(commentRepository).findById(anyLong());
        verify(commentRepository).save(comment);
    }
    
    @DisplayName("갱신하려는 댓글이 존재하지 않는 경우")
    @Test
    void getCommentByUpdateFailure() {
        final CommentUpdateForm commentUpdateForm = new CommentUpdateForm();
        commentUpdateForm.setContent("변경된 게시글");
        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, ()-> commentService.update(1L,commentUpdateForm));
    }

    @Test
    @DisplayName("성공적으로 댓글이 삭제된 경우")
    void deleteSuccessfully() {
        final Long id = 1L;
        final Comment mockComment = mock(Comment.class);
        given(commentRepository.findById(id)).willReturn(Optional.of(mockComment));

        commentService.delete(id);
        verify(commentRepository, times(1)).deleteById(anyLong());
        verify(commentRepository, times(2)).findById(anyLong());
    }

    @Test
    @DisplayName("삭제할 댓글이 존재하지 않는 경우")
    void deleteFailure() {
        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, ()->commentService.delete(anyLong()));
        verify(commentRepository, times(1)).findById(anyLong());
    }

}