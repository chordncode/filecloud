package com.chordncode.filecloud.application.file.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberFileDto;

public interface FileService {
    ResultType upload(MultipartFile file, MemberFileDto fileDto);
    ResultType createDirectory(MemberFileDto fileDto);
    ResponseEntity<Resource> download(Long fileSn);
    ResultType delete(Long fileSn);
}
