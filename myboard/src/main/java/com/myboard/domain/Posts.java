package com.myboard.domain;

import com.myboard.dto.PostUpdateForm;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;


    int viewCount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @Builder
    public Posts(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void updatePosts(PostUpdateForm postUpdateForm) {
        this.title= postUpdateForm.getTitle();
        this.content = postUpdateForm.getContent();
    }

    public void increaseViewCount() {
        viewCount++;
    }
}
