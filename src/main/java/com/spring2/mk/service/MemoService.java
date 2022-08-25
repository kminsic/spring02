package com.spring2.mk.service;



import com.spring2.mk.dto.DetailPostDto;
import com.spring2.mk.dto.MemoRequestDto;
import com.spring2.mk.dto.PostListDto;
import com.spring2.mk.model.Comment;
import com.spring2.mk.model.Memo;
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

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private User getMember(String username) {
        Optional<User> mem = userRepository.findByUsername(username);
        if(!mem.isPresent())
            throw new IllegalArgumentException("사용자 정보가 없습니다!");
        return mem.get();
    }

    //글업데이트
    @Transactional
    public DetailPostDto update(Long id, MemoRequestDto requestDto, String username) {
        User user = getMember(username);
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
        if(!memo.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        memo.update(requestDto);
        return new DetailPostDto(memo);

    }

    //글저장
    @Transactional
    public DetailPostDto create(MemoRequestDto requestDto, String username)  {
        User user = getMember(username);
        Memo memo = new Memo(requestDto, user);
        memoRepository.save(memo);
        return new DetailPostDto(memo);
    }

    //글삭제
    @Transactional
    public void delete(Long id, String username)  {
        User user = getMember(username);
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
        if(!memo.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        memoRepository.deleteById(id);
        List<Comment> list = commentRepository.findAllByPostId(id);
        for(Comment comment : list) {
            commentRepository.deleteById(comment.getId());
        }
    }

    //글상세보기
    public DetailPostDto getDetailPost(long id) {
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
        return new DetailPostDto(memo);
    }

    //글목록보기
    public List<PostListDto> getmemos() {
        List<Memo> list = memoRepository.findAllByOrderByModifiedAtDesc();
        List<PostListDto> plist = new ArrayList<>();
        for(Memo memo : list){
            PostListDto listDto = PostListDto.builder()
                    .id(memo.getId())
                    .title(memo.getTitle())
                    .author(memo.getUser().getUsername())
                    .createAt(memo.getCreateAt())
                    .modifiedAt(memo.getModifiedAt())
                    .build();
            plist.add(listDto);
        }
        return plist;
    }

}