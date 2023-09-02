package com.chordncode.filecloud.application.member.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberAuthDto;
import com.chordncode.filecloud.data.dto.MemberDto;
import com.chordncode.filecloud.data.entity.MemberAuthEntity;
import com.chordncode.filecloud.data.entity.MemberEntity;
import com.chordncode.filecloud.data.entity.MemberFileEntity;
import com.chordncode.filecloud.data.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public ResultType signup(MemberDto memberDto) {
        try {
            memberRepository.save(MemberEntity.builder()
                                          .memId(memberDto.getMemId())
                                          .memPw(passwordEncoder.encode(memberDto.getMemPw()))
                                          .memNm(memberDto.getMemNm())
                                          .memMail(memberDto.getMemMail())
                                          .memberAuthList(Collections.singletonList(MemberAuthEntity.builder()
                                                                          .memId(memberDto.getMemId())
                                                                          .memAuth("ROLE_MEMBER")
                                                                          .build()))
                                          .memberFileList(Collections.singletonList(MemberFileEntity.builder()
                                                                                                    .memId(memberDto.getMemId())
                                                                                                    .fileSn(0L)
                                                                                                    .fileSize(0L)
                                                                                                    .dirYn("Y")
                                                                                                    .savedFileName("root")
                                                                                                    .createdAt(LocalDateTime.now())
                                                                                                    .build()
                                          ))
                                          .build());

            return ResultType.SUCCESS;
        } catch (Exception e) {
            return ResultType.FAILED;
        }
    }

    @Override
    public MemberDto signin(MemberDto memberDto) {
        MemberEntity memberEntity = memberRepository.findByMemId(memberDto.getMemId());
        if(memberEntity == null){
            return null;
        }
        
        if(!passwordEncoder.matches(memberDto.getMemPw(), memberEntity.getMemPw())){
            return null;
        }

        return MemberDto.builder()
                        .memId(memberEntity.getMemId())
                        .memberAuthList(memberEntity.getMemberAuthList()
                                                    .stream()
                                                    .map(auth -> MemberAuthDto.builder()
                                                                              .memId(auth.getMemId())
                                                                              .memAuth(auth.getMemAuth())
                                                                              .build()).collect(Collectors.toList()))
                        .build();
    }
    
}
