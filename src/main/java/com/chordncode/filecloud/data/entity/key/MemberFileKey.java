package com.chordncode.filecloud.data.entity.key;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberFileKey implements Serializable {
    private String memId;
    private Long fileSn;
}
