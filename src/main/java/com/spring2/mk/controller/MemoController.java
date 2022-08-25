package com.spring2.mk.controller;


import com.spring2.mk.dto.DetailPostDto;
import com.spring2.mk.dto.MemoRequestDto;
import com.spring2.mk.dto.PostListDto;
import com.spring2.mk.security.UserDetailImp;
import com.spring2.mk.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 글 작성
    @PostMapping("/api/auth/memos")
    public DetailPostDto createPost(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailImp userDetail)  {
        return memoService.create(requestDto, userDetail.getUsername());
    }

    // 글 수정
    @PutMapping("/api/auth/memos/{id}")
    public DetailPostDto updatePost(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailImp userDetail)  {
        return memoService.update(id, requestDto, userDetail.getUsername());
    }

    // 글 삭제
    @DeleteMapping("/api/auth/memos/{id}")
    public Long deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailImp userDetail) {
        memoService.delete(id, userDetail.getUsername());
        return id;
    }

    // 전체 조회
    @GetMapping("/api/memos")
    public List<PostListDto> getPosts() {
        return memoService.getmemos();
    }

    // 글 조회
    @GetMapping("/api/Memos/{id}")
    public DetailPostDto getDetailPosts(@PathVariable Long id) {
        return memoService.getDetailPost(id);
    }
}