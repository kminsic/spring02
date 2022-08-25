package com.spring2.mk.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
// 글목록가져오기위한 Dto
public class PostListDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

}
