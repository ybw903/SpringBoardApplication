package com.myboard.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="POST_LIKE")
@EqualsAndHashCode(of ="id")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    public void mappingPosts(Posts posts) {
        this.posts = posts;
        posts.mappingLike(this);
    }

    public void mappingUser(User user) {
        this.user = user;
    }
}
