package com.chordncode.filecloud.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.chordncode.filecloud.data.entity.key.MemberAuthKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(MemberAuthKey.class)
@Table(name="MEMBER_AUTH")
public class MemberAuthEntity {
    @Id
    @Column(name="MEM_ID")
    private String memId;

    @Id
    @Column(name="MEM_AUTH")
    private String memAuth;

    @ManyToOne
    @JoinColumn(name="memberAuthList", insertable=false, updatable=false)
    private MemberEntity member;
}
