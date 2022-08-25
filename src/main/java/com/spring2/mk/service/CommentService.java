package com.spring2.mk.service;



import com.spring2.mk.dto.CommentDto;
import com.spring2.mk.dto.CommentResponseDto;
import com.spring2.mk.model.Comment;
import com.spring2.mk.model.User;
import com.spring2.mk.repository.CommentRepository;
import com.spring2.mk.repository.MemoRepository;
import com.spring2.mk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository memberRepository;
    private final MemoRepository postRepository;

    private User getMember(String username) {
        Optional<User> mem = memberRepository.findByUsername(username);
        if(!mem.isPresent())
            throw new IllegalArgumentException("사용자 정보가 없습니다!");
        return mem.get();
    }

    private void extracted(CommentDto commentDto) {
        postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
    }

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(CommentDto commentDto, String username)  {
        User member = getMember(username);
        extracted(commentDto);
        Comment comment = new Comment(commentDto, member);
        commentRepository.save(comment);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .author((comment.getUser().getUsername()))
                .comment(comment.getComment())
                .createAt(comment.getCreateAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentDto commentDto, String username) {
        User member = getMember(username);
        extracted(commentDto);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!member.getUsername().equals(comment.getUser().getUsername()))
            throw new IllegalArgumentException("댓글 작성자가 다릅니다.");
        comment.update(commentDto);
        return CommentResponseDto.builder()
                .id(comment.getId())
                .author((comment.getUser().getUsername()))
                .comment(comment.getComment())
                .createAt(comment.getCreateAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }


    // 댓글 삭제
    public Long deleteComment(Long id, String username)  {
        User user = getMember(username);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!user.getUsername().equals(comment.getUser().getUsername()))
            throw new IllegalArgumentException("댓글 작성자가 다릅니다.");
        commentRepository.deleteById(id);
        return id;
    }

    // 댓글 목록 조회
    public List<CommentResponseDto> getCommentList(Long id) {
        List<Comment> list = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> clist = new ArrayList<>();
        for (Comment c : list) {
            CommentResponseDto commentDto = CommentResponseDto.builder()
                    .id(c.getId())
                    .author(c.getUser().getUsername())
                    .comment(c.getComment())
                    .createAt(c.getCreateAt())
                    .modifiedAt(c.getModifiedAt())
                    .build();
            clist.add(commentDto);
        }

        return clist;
    }
}
