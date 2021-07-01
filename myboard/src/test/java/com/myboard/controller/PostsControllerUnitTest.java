package com.myboard.controller;

import com.myboard.domain.Posts;
import com.myboard.exception.PostsNotFoundException;
import com.myboard.service.PostsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostsControllerUnitTest {

    @Mock
    private PostsServiceImpl postsService;

    @InjectMocks
    private PostsController postsController;

    @Test
    void findPostsByIdSuccess() {

        //given
        final Posts mockPosts = mock(Posts.class);
        when(mockPosts.getId()).thenReturn(0L);
        when(mockPosts.getTitle()).thenReturn("테스트제목");
        when(mockPosts.getContent()).thenReturn("테스트내용");
        when(mockPosts.getAuthor()).thenReturn("테스트저자");

        final PostsDto postsDto = PostsDto.of(mockPosts);

        given(postsService.get(mockPosts.getId())).willReturn(mockPosts);

        //when
        ResponseEntity<PostsDto> responseEntity = postsController.findPostsById(mockPosts.getId());

        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getTitle()).isEqualTo(postsDto.getTitle());
        verify(postsService).get(anyLong());
    }

    @Test
    void findPostsByIdFailure() {
        given(postsService.get(anyLong())).willThrow(PostsNotFoundException.class);
        assertThrows(PostsNotFoundException.class,() -> postsController.findPostsById(anyLong()));
        verify(postsService).get(anyLong());
    }

    @Test
    void findPostsAllSuccess() {
        List<Posts> spyPostsList = spy(new ArrayList<>());
        when(spyPostsList.size()).thenReturn(0);
        given(postsService.getAll()).willReturn(spyPostsList);

        ResponseEntity<List<PostsDto>> responseEntity = postsController.findPostsAll();

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).size()).isEqualTo(spyPostsList.size());
    }

}