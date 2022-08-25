package com.spring2.mk.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class MemoRequestDto {
    private final String title;
    private final String contents;
}
