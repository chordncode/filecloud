package com.chordncode.filecloud.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chordncode.filecloud.data.entity.MemberFileEntity;
import com.chordncode.filecloud.data.entity.key.MemberFileKey;

public interface FileRepository extends JpaRepository<MemberFileEntity, MemberFileKey> {
    
}
