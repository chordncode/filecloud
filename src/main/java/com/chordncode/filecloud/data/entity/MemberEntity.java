package com.chordncode.filecloud.data.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="MEMBER")
public class MemberEntity implements UserDetails {

    @Id
    @Column(name="MEM_ID")
    private String memId;

    @Column(name="MEM_MAIL")
    private String memMail;

    @Column(name="MEM_PW")
    private String memPw;

    @Column(name="MEM_NM")
    private String memNm;

    @OneToMany(mappedBy="member", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
    private List<MemberAuthEntity> memberAuthList;

    @OneToMany(mappedBy="member", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<MemberFileEntity> memberFileList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.memberAuthList.stream()
                                  .map(auth -> new SimpleGrantedAuthority(auth.getMemAuth()))
                                  .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.memPw;
    }

    @Override
    public String getUsername() {
        return this.memId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
