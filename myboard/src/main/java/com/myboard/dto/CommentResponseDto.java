package com.myboard.dto;

import com.myboard.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private List<CommentResponseDto> subComment = new ArrayList<>();
    private int level;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
    private boolean isDeleted;

    public static CommentResponseDto of(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setContent(comment.getContent());
        commentResponseDto.setAuthor(comment.getAuthor().getEmail());
        commentResponseDto.setLevel(comment.getLevel());
        commentResponseDto.setCreatedDateTime(comment.getCreatedDateTime());
        commentResponseDto.setDeleted(comment.isDeleted());
        return commentResponseDto;
    }

//    public static List<CommentResponseDto> collectionOf(List<Comment> comments) {
//        List<CommentResponseDto> commentResponse = new ArrayList<>();
//        Map<Long, CommentResponseDto> map = new HashMap<>();
//        comments.forEach(comment -> {
//            CommentResponseDto dto = CommentResponseDto.of(comment);
//            map.put(dto.getId(), dto);
//
//            Comment superComment = comment.getSuperComment();
//            if(superComment != null) {
//                map.get(superComment.getId())
//                        .getSubComment().add(dto);
//            } else {
//                commentResponse.add(dto);
//            }
//        });
//
//        List<CommentResponseDto> result = new ArrayList<>();
//        commentResponse.forEach(response->{
//            result.add(response);
//            if(!response.getSubComment().isEmpty()) {
//                result.addAll(response.getSubComment());
//            }
//        });
//        return result;
//    }
}
