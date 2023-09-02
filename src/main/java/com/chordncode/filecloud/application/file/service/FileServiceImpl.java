package com.chordncode.filecloud.application.file.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberFileDto;
import com.chordncode.filecloud.data.entity.MemberFileEntity;
import com.chordncode.filecloud.data.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource(
    value="file:${user.home}/Desktop/dev/web/jwt/filecloud.properties"
)
public class FileServiceImpl implements FileService {

    @Value("${spring.file.basicpath}")
    private String basicPath;
    private final FileRepository fileRepo;
    
    @Override
    public ResultType upload(MultipartFile file, MemberFileDto fileDto) {
        try {
            File targetPath = new File(basicPath, DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()));
            if(!targetPath.exists()) {
                targetPath.mkdir();
            }
            String savedFileName = UUID.randomUUID().toString();
            file.transferTo(new File(targetPath, savedFileName).toPath());
            MemberFileEntity fileEntity = MemberFileEntity.builder()
                                                          .memId(fileDto.getMemId())
                                                          .parentFileSn(fileDto.getParentFileSn())
                                                          .savedFileName(savedFileName)
                                                          .originalFileName(file.getOriginalFilename())
                                                          .fileSize(file.getSize())
                                                          .dirYn("N")
                                                          .createdAt(LocalDateTime.now())
                                                          .build();

            fileRepo.save(fileEntity);
            return ResultType.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultType.FAILED;
        }
    }

}
