package com.chordncode.filecloud.application.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberFileDto;

public interface FileService {
    public ResultType upload(MultipartFile file, MemberFileDto fileDto);
}
