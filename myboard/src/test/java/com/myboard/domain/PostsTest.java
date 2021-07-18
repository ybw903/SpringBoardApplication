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
}