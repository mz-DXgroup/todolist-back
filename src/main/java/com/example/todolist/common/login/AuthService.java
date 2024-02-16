package com.example.todolist.common.login;

import com.example.todolist.common.config.JwtUtil;
import com.example.todolist.member.application.request.MemberLoginRequest;
import com.example.todolist.member.domain.entity.Member;
import com.example.todolist.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @Transactional
    public String login(MemberLoginRequest dto) {
        String email = dto.email();
        String password = dto.pw();
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if (!encoder.matches(password, member.getPw())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        MemberLoginRequest info = modelMapper.map(member, MemberLoginRequest.class);

        String accessToken = jwtUtil.createAccessToken(info);
        return accessToken;
    }
}
