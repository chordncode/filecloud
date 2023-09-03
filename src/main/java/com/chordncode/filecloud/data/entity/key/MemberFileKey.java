package com.chordncode.filecloud.data.entity.key;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFileKey implements Serializable {
    private String memId;
    private Long fileSn;
}
