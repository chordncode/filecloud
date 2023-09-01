package com.chordncode.filecloud.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chordncode.filecloud.data.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    MemberEntity findByMemId(String memId);
}
