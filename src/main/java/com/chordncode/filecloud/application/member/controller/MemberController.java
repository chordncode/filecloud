package com.chordncode.filecloud.application.member.controller;

import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chordncode.filecloud.application.member.service.MemberService;
import com.chordncode.filecloud.config.jwt.JwtProvider;
import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResultType signup(@RequestBody MemberDto memberDto) {
        return memberService.signup(memberDto);
    }

    @PostMapping("/signin")
    public ResultType signin(@RequestBody MemberDto memberDto, HttpServletResponse response) {
        MemberDto member = memberService.signin(memberDto);
        if(member != null) {
            String token = jwtProvider.createToken(member.getMemId(), member.getMemberAuthList()
                                                                            .stream()
                                                                            .map(auth -> auth.getMemAuth())
                                                                            .collect(Collectors.toList()));
            
            Cookie tokenCookie = new Cookie("jwt", token);
            tokenCookie.setMaxAge(360);
            response.addCookie(tokenCookie);
            return ResultType.SUCCESS;
        }
        return ResultType.FAILED;
    }


}
