package com.example.cco.security;

import com.example.cco.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RefreshTknRepository extends JpaRepository<RefreshTknEntity, Long> {
    Optional<RefreshTknEntity> findByMemberUserId(String userId);
    void deleteByMember(MemberEntity member);

}
