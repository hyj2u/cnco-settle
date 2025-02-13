package com.example.cco.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    public Optional<MemberEntity> findByUserId(String userId);
    public List<MemberMapping> findAllByActiveYnOrderByUserId(String activeYn);
    public List<MemberMapping> findAllByActiveYnAndEmailContainingOrUserNameContainingOrPhoneContainingOrderByUserId(String activeYn, String email, String userName, String userId);
    public MemberMapping findByPkey(Long pkey);
}
