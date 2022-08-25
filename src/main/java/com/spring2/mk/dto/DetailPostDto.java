package com.spring2.mk.dto;


import com.spring2.mk.model.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// 글상세보기를 위한 Dto
public class DetailPostDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public DetailPostDto (Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.author = memo.getUser().getUsername();
        this.content = memo.getContents();
        this.createAt = memo.getCreateAt();
        this.modifiedAt = memo.getModifiedAt();
    }
}

