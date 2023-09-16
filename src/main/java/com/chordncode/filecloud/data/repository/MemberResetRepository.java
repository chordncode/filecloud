package com.chordncode.filecloud.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chordncode.filecloud.data.entity.MemberResetEntity;
import com.chordncode.filecloud.data.entity.key.MemberResetKey;

public interface MemberResetRepository extends JpaRepository<MemberResetEntity, MemberResetKey> {
    
}
