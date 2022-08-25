package com.spring2.mk.controller;


import com.spring2.mk.dto.LoginRequestDto;
import com.spring2.mk.dto.SignupRequestDto;
import com.spring2.mk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService memberService;

    // 회원가입
    @PostMapping("api/user/signup")
    public String signup(@RequestBody @Valid SignupRequestDto signupRequestDto) throws IllegalAccessException {
        return memberService.registerUser(signupRequestDto);
    }

    // 로그인
    @PostMapping("api/user/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

}
