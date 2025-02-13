package com.example.cco.member;

import com.example.cco.exception.DuplicatedException;
import com.example.cco.security.PasswordDto;
import com.example.cco.security.RefreshTknRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTknRepository refreshTknRepository;
    private final ObjectMapper objectMapper;


    @Override
    public void singUp(MemberDto member) throws DuplicatedException {
        if (memberRepository.findByUserId(member.getUserId()).isPresent()) {
            throw new DuplicatedException("이미 존재하는 아이디(이메일)입니다.");
        }
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUserId(member.getUserId());
        memberEntity.setActiveYn("Y");
        memberEntity.setEmail(member.getEmail());
        memberEntity.setPhone(member.getPhone());
        memberEntity.setUserName(member.getUserName());
        log.info(passwordEncoder.encode(member.getUserPw()));
        memberEntity.setUserPw(passwordEncoder.encode(member.getUserPw()));
        memberEntity.setUserAuthCd(getAuthCd(member.getAuth()));
        memberRepository.save(memberEntity);

    }

    @Override
    public void updateMember(MemberDto member) throws Exception {
        MemberEntity memberEntity = memberRepository.findById(member.getPkey()).get();
        log.info(objectMapper.writeValueAsString(member));
        if (!member.getUserPw().equals("")) {
            log.info("pw :" + member.getUserPw());
            memberEntity.setUserPw(passwordEncoder.encode(member.getUserPw()));
        }
        if (member.getActiveYn() != null) {
            memberEntity.setActiveYn(member.getActiveYn());
        }
        memberEntity.setUserName(member.getUserName());
        memberEntity.setEmail(member.getEmail());
        memberEntity.setPhone(member.getPhone());
        memberEntity.setUserAuthCd(getAuthCd(member.getAuth()));
        memberRepository.save(memberEntity);
    }

    @Override
    public void logout(String userId) throws Exception {
        refreshTknRepository.deleteByMember(memberRepository.findByUserId(userId).get());
    }

    @Override
    public List<MemberDto> getMembers(String search) throws Exception {
        List<MemberMapping> memberMappings = new ArrayList<>();
        if (search == null || search.equals("")) {
            memberMappings = memberRepository.findAllByActiveYnOrderByUserId("Y");
        } else {
            memberMappings = memberRepository.findAllByActiveYnAndEmailContainingOrUserNameContainingOrPhoneContainingOrderByUserId("Y", search, search, search);
        }
        List<MemberDto> memberDtos = new ArrayList<>();
        for (MemberMapping memberMapping : memberMappings) {
            MemberDto memberDto = new MemberDto();
            memberDto.setEmail(memberMapping.getEmail());
            memberDto.setPhone(memberMapping.getPhone());
            memberDto.setUserName(memberMapping.getUserName());
            memberDto.setUserId(memberMapping.getUserId());
            memberDto.setPkey(memberMapping.getPkey());
            memberDto.setAuth(getRoleName(memberMapping.getUserAuthCd()));
            memberDtos.add(memberDto);
        }
        return memberDtos;

    }

    @Override
    public MemberDto getMember(Long pkey) throws Exception {
        MemberMapping memberMapping = memberRepository.findByPkey(pkey);
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(memberMapping.getEmail());
        memberDto.setPhone(memberMapping.getPhone());
        memberDto.setUserName(memberMapping.getUserName());
        memberDto.setUserId(memberMapping.getUserId());
        memberDto.setPkey(memberMapping.getPkey());
        memberDto.setAuth(getRoleName(memberMapping.getUserAuthCd()));
        return memberDto;
    }

    @Override
    public void updatePassword(MemberEntity memberEntity, PasswordDto passwordDto) {
        memberEntity.setUserPw(passwordEncoder.encode(passwordDto.getNewPassword()));
        memberRepository.save(memberEntity);
    }

    private String getRoleName(String authCd) {
        if (authCd.equals("ROLE_USER")) {
            return "일반사용자";
        } else if (authCd.equals("ROLE_MANAGER")) {
            return "정산관리자";
        } else if (authCd.equals("ROLE_FRANCHISE")) {
            return "점주";
        } else if (authCd.equals("ROLE_ACCOUNTANT")) {
            return "세무사";
        } else {
            return "전체관리자";
        }
    }

    private String getAuthCd(String auth) {
        if (auth.equals("일반사용자")) {
            return "ROLE_USER";
        } else if (auth.equals("정산관리자")) {
            return "ROLE_MANAGER";
        } else if (auth.equals("점주")) {
            return "ROLE_FRANCHISE";
        } else if (auth.equals("세무사")) {
            return "ROLE_ACCOUNTANT";
        } else {
            return "ROLE_ADMIN";
        }
    }
}

