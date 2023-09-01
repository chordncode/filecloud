package com.chordncode.filecloud.data.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFileDto {
    private String memId;
    private Long fileSn;
    private Long parentFileSn;
    private String savedFileName;
    private String originalFileName;
    private Long fileSize;
    private String dirYn;
    private LocalDateTime createdAt;
}
