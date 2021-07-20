package com.myboard.domain;

import com.myboard.dto.PostUpdateForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostsTest {

    @Test
    @DisplayName("게시글조회수 증가 테스트")
    void increaseViewCountTest() {

        //given
        Posts posts = new Posts();
        int beforeIncreaseViewCount = posts.getViewCount();

        //when
        posts.increaseViewCount();
        int afterIncreaseViewCount = posts.getViewCount();

        //then
        assertThat(afterIncreaseViewCount).isEqualTo(beforeIncreaseViewCount+1);
    }

    @DisplayName("게시글 갱신 테스트")
    @Test
    void updatePostsTest() {
        //given
        final User user = new User();
        final Posts posts = Posts.builder()
                        .title("게시글 제목")
                        .content("게시글 내용")
                        .author(user)
                        .build();
        final PostUpdateForm postUpdateForm = PostUpdateForm.builder()
                                                .title("변경된 게시글 제목")
                                                .content("변경된 게시글 내용")
                                                .build();
    }
}