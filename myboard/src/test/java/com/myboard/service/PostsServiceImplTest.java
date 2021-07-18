package com.myboard.service;

import com.myboard.domain.Posts;
import com.myboard.domain.PostsRepository;
import com.myboard.domain.User;
import com.myboard.domain.UserRepository;
import com.myboard.dto.PostForm;
import com.myboard.dto.PostUpdateForm;
import com.myboard.exception.PostsNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostsServiceImplTest {

    @Mock
    private PostsRepository postsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostsServiceImpl postsService;

    @Test
    @DisplayName("존재하지 않는 사용자를 게시글 작성자로 설정한 경우")
    void addPosts() {
        PostForm postForm = new PostForm();
        postForm.setTitle("게시글 테스트 제목");
        postForm.setContent("게시글 테스트 내용");
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, ()-> postsService.add(postForm,anyString()));
    }

    @Test
    @DisplayName("성공적으로 게시물이 추가되는 경우")
    void addPostsSuccess() {
        PostForm postForm = new PostForm();
        postForm.setTitle("게시글 테스트 제목");
        postForm.setContent("게시글 테스트 내용");
        final User user = mock(User.class);
        when(user.getEmail()).thenReturn("test@gmail.com");
        final Posts posts = Posts.builder()
                            .title(postForm.getTitle())
                            .content(postForm.getContent())
                            .author(user)
                            .build();
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(postsRepository.save(any(Posts.class))).willReturn(posts);

        Posts addedPosts = postsService.add(postForm, "");

        //TODO: equal method
        assertThat(addedPosts.getAuthor().getEmail()).isEqualTo(user.getEmail());
        assertThat(addedPosts.getTitle()).isEqualTo(postForm.getTitle());
        assertThat(addedPosts.getContent()).isEqualTo(addedPosts.getContent());
    }

    @Test
    @DisplayName("게시물 ID를 통해 게시물 컬럼을 성공적으로 조회하는 경우")
    void getPostsByIdSuccessfully() {
        final Posts posts = mock(Posts.class);
        when(posts.getId()).thenReturn(1L);
        given(postsRepository.findById(posts.getId())).willReturn(Optional.of(posts));

        Posts postsByFind = postsService.findPost(posts.getId());

        assertThat(postsByFind).isEqualTo(posts);
        verify(postsRepository).findById(anyLong());
    }

    @Test
    @DisplayName("게시물 ID를 통해 게시물 컬럼을 조회할때 찾을 수 없는 경우")
    void getPostsByIdFailure() {
        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, ()->postsService.findPost(anyLong()));
        verify(postsRepository).findById(anyLong());
    }


    public Posts read(Long id) {
        Posts post = postsRepository.findById(id).orElseThrow(
                () -> new PostsNotFoundException("delete: Posts not found by : " + id));
        post.increaseViewCount();
        return postsRepository.save(post);
    }

    @Test
    @DisplayName("게시물 ID를 통해 게시물 컬럼을 성공적으로 조회한 이후 게시물 조회수를 증가시키는 경우")
    void readPost() {
        final Posts posts = mock(Posts.class);
        final Posts savedPosts = mock(Posts.class);

        when(posts.getId()).thenReturn(1L);
        when(posts.getViewCount()).thenReturn(0);
        when(savedPosts.getId()).thenReturn(1L);
        when(savedPosts.getViewCount()).thenReturn(1);

        given(postsRepository.findById(posts.getId())).willReturn(Optional.of(posts));
        given(postsRepository.save(posts)).willReturn(savedPosts);

        Posts postsByReadPosts = postsService.read(posts.getId());

        assertThat(postsByReadPosts.getId()).isEqualTo(posts.getId());
        assertThat(postsByReadPosts.getViewCount()).isEqualTo(posts.getViewCount()+1);
        verify(postsRepository).findById(anyLong());
        verify(posts,times(1)).increaseViewCount();
    }

    @Test
    @DisplayName("게시글 조회수를 증가시킬 게시글이 존재하지 않는 경우")
    void readPostFailure() {
        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, ()->postsService.read(anyLong()));
        verify(postsRepository).findById(anyLong());
    }

    @Test
    @DisplayName("성공적으로 게시글이 갱신됭 경우")
    void updatePostsSuccessfully() {
        final Posts posts = mock(Posts.class);
        final PostUpdateForm postUpdateForm = PostUpdateForm.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .build();
        final Posts updatedPosts = mock(Posts.class);

        when(posts.getId()).thenReturn(1L);
        when(updatedPosts.getId()).thenReturn(1L);
        when(updatedPosts.getTitle()).thenReturn(postUpdateForm.getTitle());
        when(updatedPosts.getContent()).thenReturn(postUpdateForm.getContent());

        given(postsRepository.findById(posts.getId())).willReturn(Optional.of(posts));
        given(postsRepository.save(posts)).willReturn(updatedPosts);

        Posts postsUpdated = postsService.update(posts.getId(), postUpdateForm);

        assertThat(postsUpdated.getId()).isEqualTo(posts.getId());
        assertThat(postsUpdated.getTitle()).isEqualTo(postUpdateForm.getTitle());
        assertThat(postsUpdated.getContent()).isEqualTo(postUpdateForm.getContent());
        verify(posts).updatePosts(postUpdateForm);
    }

    @Test
    @DisplayName("갱신할 게시글이 존재하지 않는 경우")
    void updatePostsFailure() {
        final PostUpdateForm postUpdateForm = PostUpdateForm.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .build();

        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, ()->postsService.update(anyLong(),postUpdateForm));
        verify(postsRepository).findById(anyLong());
    }

    @Test
    @DisplayName("성공적으로 게시글이 삭제된 경우")
    void deleteSuccessfully() {
        final Long id = 1L;
        final Posts mockPosts = mock(Posts.class);
        given(postsRepository.findById(id)).willReturn(Optional.of(mockPosts));
        postsService.delete(id);
        verify(postsRepository, times(1)).deleteById(anyLong());
        verify(postsRepository, times(2)).findById(anyLong());
    }

    @Test
    @DisplayName("삭제할 게시글이 존재하지 않는 경우")
    void deleteFailure() {
        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, ()->postsService.delete(anyLong()));
        verify(postsRepository, times(1)).findById(anyLong());
    }
//    @Test
//    void getPostsSuccessfully() {
//        final Posts mockPosts = mock(Posts.class);
//        when(mockPosts.getId()).thenReturn(1L);
//        given(postsRepository.findById(mockPosts.getId())).willReturn(Optional.of(mockPosts));
//
//        Posts postsByGet = postsService.get(mockPosts.getId());
//
//        assertThat(postsByGet).isEqualTo(mockPosts);
//        verify(postsRepository).findById(anyLong());
//    }
//
//    @Test
//    void getPostsFailure() {
//        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());
//
//        assertThrows(PostsNotFoundException.class, ()->postsService.get(anyLong()));
//        verify(postsRepository).findById(anyLong());
//    }
//
//    @Test
//    void getAllPosts() {
//        List<Posts> mockPostsList = spy(new ArrayList<>());
//        given(postsRepository.findAll()).willReturn(mockPostsList);
//
//        List<Posts> postsByGetAll = postsService.getAll();
//
//        assertThat(postsByGetAll.size()).isEqualTo(mockPostsList.size());
//        verify(postsRepository, times(1)).findAll();
//    }
//
//    @Test
//    void updateSuccessfully() {
//        final Posts mockPosts = mock(Posts.class);
//        when(mockPosts.getId()).thenReturn(1L);
//        given(postsRepository.findById(mockPosts.getId())).willReturn(Optional.of(mockPosts));
//        given(postsRepository.save(mockPosts)).willReturn(mockPosts);
//
//        Posts updatedPosts = postsService.update(mockPosts);
//
//        assertThat(updatedPosts).isNotNull();
//        verify(postsRepository).save(any(Posts.class));
//    }
//
//    @Test
//    void updateFailure() {
//        final Posts mockPosts = mock(Posts.class);
//        when(mockPosts.getId()).thenReturn(1L);
//        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());
//
//        assertThrows(PostsNotFoundException.class, ()->postsService.update(mockPosts));
//        verify(postsRepository).findById(anyLong());
//    }
//
//    @Test
//    void deleteSuccessfully() {
//        final Long id = 1L;
//        postsService.delete(id);
//        verify(postsRepository, times(1)).deleteById(anyLong());
//        verify(postsRepository, times(1)).findById(anyLong());
//    }
}