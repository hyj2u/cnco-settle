package com.example.cco.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("userId= "+ userId));
    }
}
