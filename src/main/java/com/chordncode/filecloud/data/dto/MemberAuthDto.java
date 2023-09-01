package com.chordncode.filecloud.data.dto;

import com.chordncode.filecloud.data.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAuthDto {
    private String memId;
    private String memAuth;
    private MemberEntity member;
}
