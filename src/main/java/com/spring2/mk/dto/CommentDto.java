package com.spring2.mk.dto;

import com.spring2.mk.model.User;
import lombok.Getter;

@Getter
public class CommentDto {
    private User user;
    private String comment;
    private Long postId;
}
