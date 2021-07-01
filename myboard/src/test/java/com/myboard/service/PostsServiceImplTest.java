package com.myboard.service;

import com.myboard.domain.Posts;
import com.myboard.domain.PostsRepository;
import com.myboard.exception.PostsNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostsServiceImplTest {

    @Mock
    private PostsRepository postsRepository;

    @InjectMocks
    private PostsService postsService = new PostsServiceImpl(this.postsRepository);

    @Test
    void addPosts() {
        final Posts posts = Posts.builder()
                                .title("테스트제목")
                                .content("테스트내용")
                                .author("테스트저자")
                                .build();
        given(postsRepository.save(posts)).willReturn(posts);
        Posts savedPosts = postsService.add(posts);

        assertThat(savedPosts).isNotNull();
        verify(postsRepository).save(any(Posts.class));
    }

    @Test
    void getPostsSuccessfully() {
        final Posts mockPosts = mock(Posts.class);
        when(mockPosts.getId()).thenReturn(1L);
        given(postsRepository.findById(mockPosts.getId())).willReturn(Optional.of(mockPosts));

        Posts postsByGet = postsService.get(mockPosts.getId());

        assertThat(postsByGet).isEqualTo(mockPosts);
        verify(postsRepository).findById(anyLong());
    }

    @Test
    void getPostsFailure() {
        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, ()->postsService.get(anyLong()));
        verify(postsRepository).findById(anyLong());
    }

    @Test
    void getAllPosts() {
        List<Posts> mockPostsList = spy(new ArrayList<>());
        given(postsRepository.findAll()).willReturn(mockPostsList);

        List<Posts> postsByGetAll = postsService.getAll();

        assertThat(postsByGetAll.size()).isEqualTo(mockPostsList.size());
        verify(postsRepository, times(1)).findAll();
    }

    @Test
    void updateSuccessfully() {
        final Posts mockPosts = mock(Posts.class);
        when(mockPosts.getId()).thenReturn(1L);
        given(postsRepository.findById(mockPosts.getId())).willReturn(Optional.of(mockPosts));
        given(postsRepository.save(mockPosts)).willReturn(mockPosts);

        Posts updatedPosts = postsService.update(mockPosts);

        assertThat(updatedPosts).isNotNull();
        verify(postsRepository).save(any(Posts.class));
    }

    @Test
    void updateFailure() {
        final Posts mockPosts = mock(Posts.class);
        when(mockPosts.getId()).thenReturn(1L);
        given(postsRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, ()->postsService.update(mockPosts));
        verify(postsRepository).findById(anyLong());
    }

    @Test
    void deleteSuccessfully() {
        final Long id = 1L;
        postsService.delete(id);
        verify(postsRepository, times(1)).deleteById(anyLong());
        verify(postsRepository, times(1)).findById(anyLong());
    }
}