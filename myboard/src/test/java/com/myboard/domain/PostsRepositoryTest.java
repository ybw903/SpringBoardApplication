package com.myboard.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @Test
    void idStrategyTest() {
        //given
        Posts posts1 =  Posts.builder()
                .title("테스트제목")
                .content("테스트내용")
                .author("테스트작성자")
                .build();
        Posts posts2 =  Posts.builder()
                .title("테스트제목")
                .content("테스트내용")
                .author("테스트작성자")
                .build();

        //when
        Posts savedPosts1 = postsRepository.save(posts1);
        Posts savedPosts2 = postsRepository.save(posts2);

        //then
        assertThat(Math.abs(savedPosts1.getId()-savedPosts2.getId())).isEqualTo(1L);
    }

    @Test
    void savePostsTest() {
        //given
        Posts posts =  Posts.builder()
                        .title("테스트제목")
                        .content("테스트내용")
                        .author("테스트작성자")
                        .build();

        //when
        Posts savedPosts =  postsRepository.save(posts);

        //then
        assertThat(savedPosts.getTitle()).isEqualTo(posts.getTitle());
        assertThat(savedPosts.getContent()).isEqualTo(posts.getContent());
        assertThat(savedPosts.getAuthor()).isEqualTo(posts.getAuthor());
    }

}