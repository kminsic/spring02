package com.spring2.mk.controller;



import com.spring2.mk.dto.CommentDto;
import com.spring2.mk.dto.CommentResponseDto;
import com.spring2.mk.security.UserDetailImp;
import com.spring2.mk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    //댓글 쓰기
    @PostMapping("api/auth/comment")
    public CommentResponseDto createComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetailImp userDetail) throws IllegalAccessException {
        return commentService.createComment(commentDto, userDetail.getUsername());
    }

    //댓글 수정
    @PutMapping("api/auth/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetailImp userDetail) throws IllegalAccessException  {
        return commentService.updateComment(id, commentDto, userDetail.getUsername());
    }

    //댓글 삭제
    @DeleteMapping("api/auth/comment/{id}")
    public Long deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailImp userDetail) throws IllegalAccessException  {
        return commentService.deleteComment(id, userDetail.getUsername());
    }

    //댓글 목록 조회
    @GetMapping("api/comment/{id}")
    public List<CommentResponseDto> getComment(@PathVariable Long id) {
        return commentService.getCommentList(id);
    }
}
