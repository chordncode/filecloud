package com.chordncode.filecloud.data.entity.key;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberAuthKey implements Serializable {
    private String memId;
    private String memAuth;
}
