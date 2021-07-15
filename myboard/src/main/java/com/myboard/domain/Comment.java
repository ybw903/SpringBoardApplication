package com.myboard.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myboard.dto.CommentCreateDto;
import com.myboard.dto.CommentSaveForm;
import com.myboard.dto.CommentUpdateForm;
import com.myboard.exception.CommentGoneException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_comment_id")
    private Comment superComment;

    @OneToMany(mappedBy = "superComment", cascade = CascadeType.ALL)
    private List<Comment> subComment = new ArrayList<>();

    private int level;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    private boolean isDeleted;

    public void updateComment(CommentUpdateForm commentUpdateForm) {
        this.content = commentUpdateForm.getContent();
    }

    public static Comment createComment(CommentCreateDto commentCreateDto, Posts posts, User author) {
        Comment newComment = new Comment();

        if(commentCreateDto.getComment() == null) {
            newComment.level = 1;
        } else {
            Comment supComment = commentCreateDto.getComment();
            log.info(supComment.toString());
            if(supComment.isDeleted) {
                throw new CommentGoneException("SuperComment is already deleted");
            }
            newComment.level = supComment.getLevel()+1;
            newComment.superComment = supComment;
            supComment.getSubComment().add(newComment);
        }
        newComment.content = commentCreateDto.getContent();
        newComment.posts = posts;
        newComment.author = author;
        newComment.isDeleted = false;
        return newComment;
    }

    @Override
    public String toString() {
        return "[commentId: " + (id == null? "null" :id )+
                ", content: " + (content == null? "null" :content ) +
                ", level: " + (level == 0? "null" :level ) +
                " isDeleted: " + isDeleted;
    }
}
