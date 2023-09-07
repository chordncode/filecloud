package com.chordncode.filecloud.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @Column(name="MEM_ID", nullable=false)
    private String memId;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="FILE_SN", nullable=false)
    private Long fileSn;

    @Column(name="PARENT_FILE_SN")
    private Long parentFileSn;

    @Column(name="SAVED_FILE_NAME", nullable=false)
    private String savedFileName;

    @Column(name="ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name="FILE_SIZE", nullable=false)
    private Long fileSize;
    
    @Column(name="DIR_YN", nullable=false)
    private String dirYn;

    @Column(name="CREATED_AT", nullable=false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="MEM_ID", insertable=false, updatable=false)
    private MemberEntity member;
    
}
