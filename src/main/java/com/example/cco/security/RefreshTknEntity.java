package com.example.cco.security;

import com.example.cco.base.BaseEntity;
import com.example.cco.member.MemberEntity;
import jakarta.persistence.*;

import lombok.Data;


@Entity(name="refresh_tkn")
@Data
public class RefreshTknEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tknId;
    @Column
    private String refreshTkn;
    @OneToOne(mappedBy = "refreshTkn")
    private MemberEntity member;


}
