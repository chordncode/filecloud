package com.chordncode.filecloud.application.file.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResultType upload(@ModelAttribute MultipartFile file, @RequestParam Long parentFileSn) {
        return fileService.upload(file, parentFileSn);
    }
    
    @PostMapping("/directory")
    public ResultType createDirectory(@RequestBody MemberFileDto fileDto) {
        return fileService.createDirectory(fileDto);
    }

    @GetMapping("/file")
    public List<MemberFileDto> list() {
        return fileService.list();
    }

    @GetMapping("/file/{fileSn}")
    public ResponseEntity<?> download(@PathVariable Long fileSn) {
        return fileService.download(fileSn);
    }

    @DeleteMapping("/file/{fileSn}")
    public ResultType delete(@PathVariable Long fileSn) {
        return fileService.delete(fileSn);
    }

    @PutMapping("/file/{fileSn}")
    public ResultType move(@PathVariable Long fileSn, @RequestBody MemberFileDto fileDto) {
        return fileService.move(fileSn, fileDto);
    }

}
