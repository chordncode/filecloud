package com.chordncode.filecloud.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.chordncode.filecloud.data.entity.key.MemberFileKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(MemberFileKey.class)
@Table(name="MEMBER_FILE")
public class MemberFileEntity {

    @Id
    @Column(name="MEM_ID")
    private String memId;

    @Id
    @Column(name="FILE_SN")
    private Long fileSn;

    @Column(name="PARENT_FILE_SN")
    private Long parentFileSn;

    @Column(name="SAVED_FILE_NAME")
    private String savedFileName;

    @Column(name="ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name="FILE_SIZE")
    private Long fileSize;
    
    @Column(name="DIR_YN")
    private String dirYn;

    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="memberFileList", insertable=false, updatable=false)
    private MemberEntity member;
    
}
