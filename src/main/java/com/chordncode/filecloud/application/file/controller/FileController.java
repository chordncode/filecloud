package com.chordncode.filecloud.application.file.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chordncode.filecloud.application.file.service.FileService;
import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberFileDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {
    
    private final FileService fileService;

    @PostMapping("/file")
    public ResultType upload(@ModelAttribute MultipartFile file, @ModelAttribute MemberFileDto fileDto) {
        return fileService.upload(file, fileDto);
    }
    
}