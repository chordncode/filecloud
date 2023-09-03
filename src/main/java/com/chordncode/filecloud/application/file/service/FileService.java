package com.chordncode.filecloud.application.file.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.chordncode.filecloud.config.util.ResultType;
import com.chordncode.filecloud.data.dto.MemberFileDto;

public interface FileService {
    /**
     * upload the file and save the file information to database
     * @param file the file will be uploaded
     * @param parentFileSn the serial number of the directory where the file will be saved
     * @return SUCCESS : 1, FAILED : 0
     */
    ResultType upload(MultipartFile file, Long parentFileSn);

    /**
     * create a directory
     * @param fileDto the object that includes the directory name and the serial number of the parent directory
     * @return SUCCESS : 1, FAILED : 0
     */
    ResultType createDirectory(MemberFileDto fileDto);

    /**
     * make the client download the file or the zip file of the directory
     * @param fileSn the serial number of the file or dirctory that will be downloaded
     * @return ResponseEntity to make the client download the file
     */
    ResponseEntity<Resource> download(Long fileSn);

    /**
     * delete the file
     * @param fileSn the serial number of the file will be deleted
     * @return SUCCESS : 1, FAILED : 0
     */
    ResultType delete(Long fileSn);
    
    /**
     * move or rename the file
     * @param fileSn the serial number of the file will be modified
     * @param fileDto the serial number of the parent file which the file will be moved,
     *                or new name of the file
     * @return SUCCESS : 1, FAILED : 0
     */
    ResultType move(Long fileSn, MemberFileDto fileDto);
}
