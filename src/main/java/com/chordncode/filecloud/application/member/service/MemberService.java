package com.chordncode.filecloud.application.member.service;

import java.util.Map;

import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberDto;

public interface MemberService {
    ResultType signup(MemberDto memberDto);
    MemberDto signin(MemberDto memberDto);
    ResultType sendMail(Map<String, String> param);
    MemberDto findId(MemberDto memberDto);
    ResultType resetPassword(MemberDto memberDto);
}
