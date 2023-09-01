package com.chordncode.filecloud.data.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String memId;
    private String memMail;
    private String memPw;
    private String memNm;
    private List<MemberAuthDto> memberAuthList;
    private List<MemberFileDto> memberFileList;
}
