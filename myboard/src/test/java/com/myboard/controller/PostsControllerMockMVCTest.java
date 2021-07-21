package com.myboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myboard.domain.Posts;
import com.myboard.dto.CommentResponseDto;
import com.myboard.dto.CommentSaveForm;
import com.myboard.dto.PostResponseDto;
import com.myboard.service.PostsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostsController.class)
class PostsControllerMockMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostsServiceImpl postsService;

//    @GetMapping("/posts/{id}")
//    public String readPost(@PathVariable("id") Long postId, Model model) {
//        model.addAttribute("post", PostResponseDto.of(postsService.read(postId)));
//        model.addAttribute("comments",
//                CommentResponseDto.collectionOf(commentService.getCommentsWithPosts(postId))
//        );
//        model.addAttribute("commentSaveForm", new CommentSaveForm());
//        return "posts/post";
//    }

//    @DisplayName("게시글을 읽어들이는 요청을 받은 경우")
//    @Test
//    void readPostsTest() throws Exception {
//        final Posts posts = mock(Posts.class);
//        when(posts.getId()).thenReturn(1L);
//        when(posts.getTitle()).thenReturn("테스트제목");
//        when(posts.getContent()).thenReturn("테스트내용");
//        given(postsService.read(anyLong())).willReturn(posts);
//
//        mockMvc.perform(get("/posts/{id}",1L))
//                .andExpect(status().isOk());
//    }

//
//    ObjectMapper mapper = new ObjectMapper();
//
//    @MockBean
//    private PostsServiceImpl postsService;
//
//    @Test
//    void postsAddPosts() throws Exception {
//        PostsDto postsDto = PostsDto.builder()
//                                .id(1L)
//                                .title("테스트제목")
//                                .content("테스트내용")
//                                .author("테스트저자")
//                                .build();
//
//        Posts mockPosts = mock(Posts.class);
//        when(mockPosts.getId()).thenReturn(1L);
//        when(mockPosts.getTitle()).thenReturn("테스트제목");
//        when(mockPosts.getContent()).thenReturn("테스트내용");
//        when(mockPosts.getAuthor()).thenReturn("테스트저자");
//        given(postsService.add(any(Posts.class))).willReturn(mockPosts);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                    .post("/posts")
//                    .content(mapper.writeValueAsString(postsDto))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON))
//                    .andDo(print())
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.id", is(mockPosts.getId().intValue())))
//                    .andExpect(jsonPath("$.title", is(mockPosts.getTitle())))
//                    .andExpect(jsonPath("$.content", is(mockPosts.getContent())))
//                    .andExpect(jsonPath("$.author", is(mockPosts.getAuthor())));
//    }
//
//    @Test
//    void postsFindAll() throws Exception {
//        List<Posts> postsList = spy(new ArrayList<>());
//        when(postsService.getAll()).thenReturn(postsList);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/posts")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void postsFindById() throws Exception {
//        Posts mockPosts = mock(Posts.class);
//        when(mockPosts.getId()).thenReturn(1L);
//        when(mockPosts.getTitle()).thenReturn("테스트제목");
//        when(mockPosts.getContent()).thenReturn("테스트내용");
//        when(mockPosts.getAuthor()).thenReturn("테스트저자");
//        when(postsService.get(mockPosts.getId())).thenReturn(mockPosts);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/posts/id")
//                .param("id", mockPosts.getId().toString())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(mockPosts.getId().intValue())))
//                .andExpect(jsonPath("$.title", is(mockPosts.getTitle())))
//                .andExpect(jsonPath("$.content", is(mockPosts.getContent())))
//                .andExpect(jsonPath("$.author", is(mockPosts.getAuthor())));
//    }

}