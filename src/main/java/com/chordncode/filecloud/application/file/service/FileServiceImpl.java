package com.chordncode.filecloud.application.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
            LocalDateTime now = LocalDateTime.now();
            File targetPath = new File(basicPath, DateTimeFormatter.ofPattern("yyyyMMdd").format(now));
            if(!targetPath.exists()) {
                targetPath.mkdir();
            }
            String savedFileName = UUID.randomUUID().toString();
            file.transferTo(new File(targetPath, savedFileName).toPath());
            MemberFileEntity fileEntity = MemberFileEntity.builder()
                                                          .memId(SecurityContextHolder.getContext().getAuthentication().getName())
                                                          .parentFileSn(fileDto.getParentFileSn())
                                                          .savedFileName(savedFileName)
                                                          .originalFileName(file.getOriginalFilename())
                                                          .fileSize(file.getSize())
                                                          .dirYn("N")
                                                          .createdAt(now)
                                                          .build();

            fileRepo.save(fileEntity);
            return ResultType.SUCCESS;
        } catch (Exception e) {}
        return ResultType.FAILED;
    }

    @Override
    public ResultType createDirectory(MemberFileDto fileDto) {
        try {
            MemberFileEntity fileEntity = MemberFileEntity.builder()
                                                          .memId(SecurityContextHolder.getContext().getAuthentication().getName())
                                                          .parentFileSn(fileDto.getParentFileSn())
                                                          .savedFileName(fileDto.getSavedFileName())
                                                          .fileSize(0L)
                                                          .dirYn("Y")
                                                          .createdAt(LocalDateTime.now())
                                                          .build();
            fileRepo.save(fileEntity);
            return ResultType.SUCCESS;
        } catch (Exception e) {e.printStackTrace();}
        return ResultType.FAILED;
    }

    @Override
    public ResponseEntity<Resource> download(Long fileSn) {
        try {
            MemberFileEntity fileEntity = fileRepo.findByMemIdAndFileSn(SecurityContextHolder.getContext().getAuthentication().getName(), fileSn);
            if(fileEntity == null) {
                throw new Exception();
            }
            if(fileEntity.getDirYn().equals("Y")) {
                return downloadFiles(fileEntity);
            } else if(fileEntity.getDirYn().equals("N")) {
                return downloadFile(fileEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Zip the multiple files and return the ResponseEntity
     * @param fileEntity the file info that client requested
     * @return ResponseEntity
     * @throws Exception
     */
    private ResponseEntity<Resource> downloadFiles(MemberFileEntity fileEntity) throws Exception {
        List<MemberFileEntity> targetFileList = fileRepo.findAllByMemIdAndParentFileSn(SecurityContextHolder.getContext().getAuthentication().getName(), fileEntity.getFileSn());
        if(targetFileList == null || targetFileList.size() == 0) return null;
        
        String zipFileName = fileEntity.getSavedFileName() + ".zip";
        FileOutputStream os = new FileOutputStream(basicPath + File.separator + zipFileName);
        ZipOutputStream zos = new ZipOutputStream(os);
        for(MemberFileEntity file : targetFileList) {
            if(file.getDirYn().equals("Y")) continue;
            zos.putNextEntry(new ZipEntry(file.getOriginalFileName()));
            FileInputStream fis = new FileInputStream(new File(basicPath, DateTimeFormatter.ofPattern("yyyyMMdd").format(file.getCreatedAt()) + File.separator + file.getSavedFileName()));
            byte[] buffer = new byte[1024];
            int length;
            while((length = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }
        zos.close();
        return createResponseEntity(new File(basicPath + File.separator + zipFileName), zipFileName);
    }

    /**
     * return ResponseEntity with only one file
     * @param fileEntity the file info that client requested
     * @return ResponseEntity
     * @throws Exception
     */
    private ResponseEntity<Resource> downloadFile(MemberFileEntity fileEntity) throws Exception {
        File targetFile = new File(DateTimeFormatter.ofPattern("yyyyMMdd").format(fileEntity.getCreatedAt()), fileEntity.getSavedFileName());
        File targetFileWithFullPath = new File(basicPath, targetFile.getPath());
        if(targetFileWithFullPath.exists()) {
            return createResponseEntity(targetFileWithFullPath, fileEntity.getOriginalFileName());
        }
        return null;
    }

    /**
     * create a ResponseEntity instance with the file path and the file name
     * @param targetFile the file that needs to be responded with
     * @param OriginalFileName the file name
     * @return created responseEntity
     * @throws Exception
     */
    private ResponseEntity<Resource> createResponseEntity(File targetFile, String OriginalFileName) throws Exception{
        Resource targetFileResource = new FileSystemResource(targetFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=\"" + new String(OriginalFileName.getBytes(StandardCharsets.UTF_8), "ISO-8859-1") + "\"");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<Resource>(targetFileResource, headers, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResultType delete(Long fileSn) {
        try {
            String memId = SecurityContextHolder.getContext().getAuthentication().getName();
            MemberFileEntity fileEntity = fileRepo.findByMemIdAndFileSn(memId, fileSn);
            if(fileEntity == null) {
                throw new Exception();
            }
            if(fileEntity.getFileSn() == 1) {
                throw new Exception();
            }
            File targetFile = new File(DateTimeFormatter.ofPattern("yyyyMMdd").format(fileEntity.getCreatedAt()), fileEntity.getSavedFileName());
            File targetFileWithFullPath = new File(basicPath, targetFile.getPath());
            if(targetFileWithFullPath.exists()) {
                targetFileWithFullPath.delete();
                fileRepo.deleteByMemIdAndFileSn(memId, fileSn);
                return ResultType.SUCCESS;
            }
        } catch (Exception e) {}
        return ResultType.FAILED;
    }

    @Override
    public ResultType move(Long fileSn, MemberFileDto fileDto) {
        try {
            MemberFileEntity fileEntity = fileRepo.findByMemIdAndFileSn(SecurityContextHolder.getContext().getAuthentication().getName(), fileSn);
            if(fileEntity == null) throw new Exception();
            if(fileDto.getParentFileSn() != null) {
                fileEntity.setParentFileSn(fileDto.getParentFileSn());
            }
            if(fileDto.getOriginalFileName() != null) {
                fileEntity.setOriginalFileName(fileDto.getOriginalFileName());
            }
            fileRepo.save(fileEntity);
            return ResultType.SUCCESS;
        } catch (Exception e) {}
        return ResultType.FAILED;
    }

}
