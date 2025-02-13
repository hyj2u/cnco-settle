package com.example.cco.member;

import com.example.cco.base.BaseEntity;
import com.example.cco.security.RefreshTknEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "member")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MemberEntity extends BaseEntity implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;
    @Column
    private String userId;
    @Column
    private String userPw;
    @Column
    private String userName;
    @Column
    private String userAuthCd;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String activeYn;
    @OneToOne
    @JoinColumn(name = "tkn_id")
    private RefreshTknEntity refreshTkn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); //별도로 권한제어 없음
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return email;
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

