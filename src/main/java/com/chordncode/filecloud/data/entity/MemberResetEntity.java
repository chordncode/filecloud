package com.chordncode.filecloud.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.chordncode.filecloud.data.entity.key.MemberResetKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(MemberResetKey.class)
@Table(name="MEMBER_RESET")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResetEntity {

    @Id
    @Column(name="MEM_RESET_KEY")
    private String memResetKey;

    @Id
    @Column(name="MEM_ID")
    private String memId;

    @Column
    private LocalDateTime validTime;
    
}
