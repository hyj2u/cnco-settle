package com.example.cco.member;

import com.example.cco.security.PasswordDto;

import java.util.List;

public interface MemberService {
    public void singUp(MemberDto member) throws Exception;

    public void logout(String userId) throws Exception;

    public List<MemberDto> getMembers(String search) throws Exception;

    public MemberDto getMember(Long pkey) throws Exception;

    public void updateMember(MemberDto member) throws Exception;

    public void updatePassword(MemberEntity memberEntity, PasswordDto passwordDto);


}
