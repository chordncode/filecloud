package com.chordncode.filecloud.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chordncode.filecloud.data.entity.MemberFileEntity;
import com.chordncode.filecloud.data.entity.key.MemberFileKey;

public interface FileRepository extends JpaRepository<MemberFileEntity, MemberFileKey> {
    MemberFileEntity findByMemIdAndFileSn(String memId, Long fileSn);
    List<MemberFileEntity> findAllByMemIdAndParentFileSn(String memId, Long parentFileSn);
    void deleteByMemIdAndFileSn(String memId, Long fileSn);
}
