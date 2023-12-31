package com.chordncode.filecloud.application.member.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chordncode.filecloud.config.mail.MailUtil;
import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberAuthDto;
import com.chordncode.filecloud.data.dto.MemberDto;
import com.chordncode.filecloud.data.entity.MemberAuthEntity;
import com.chordncode.filecloud.data.entity.MemberEntity;
import com.chordncode.filecloud.data.entity.MemberFileEntity;
import com.chordncode.filecloud.data.entity.MemberResetEntity;
import com.chordncode.filecloud.data.repository.MemberRepository;
import com.chordncode.filecloud.data.repository.MemberResetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberResetRepository memberResetRepository;
    private final MailUtil mailUtil;

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

    @Override
    public ResultType sendMail(Map<String, String> param) {
        try {
            mailUtil.sendMail(param.get("recipient"), param.get("subject"), param.get("mailbody"));
            return ResultType.SUCCESS;
        } catch (Exception e) {
            return ResultType.FAILED;
        }
    }

    @Override
    public MemberDto findId(MemberDto memberDto) {
        return MemberDto.builder()
                        .memId(memberRepository.findMemIdByMemMail(memberDto.getMemMail()).getMemId())
                        .build();
    }

    @Override
    public ResultType resetPassword(MemberDto memberDto) {
        Long isMemberExists = memberRepository.countByMemIdAndMemMail(memberDto.getMemId(), memberDto.getMemMail());
        if(isMemberExists == 0) {
            return ResultType.FAILED;
        }

        String memResetKey = UUID.randomUUID().toString();
        memberResetRepository.save(MemberResetEntity.builder()
                                                    .memId(memberDto.getMemId())
                                                    .memResetKey(memResetKey)
                                                    .build());
        return ResultType.SUCCESS;
    }

}
