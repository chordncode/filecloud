package com.chordncode.filecloud.application.member.service;

import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberDto;

public interface MemberService {
    ResultType signup(MemberDto memberDto);
    MemberDto signin(MemberDto memberDto);
    MemberDto findId(MemberDto memberDto);
}
