package io.dongvelop.springsecuritypractice.member.service;

import io.dongvelop.springsecuritypractice.common.authority.TokenInfo;
import io.dongvelop.springsecuritypractice.common.authority.TokenProvider;
import io.dongvelop.springsecuritypractice.common.dto.request.LoginRequest;
import io.dongvelop.springsecuritypractice.common.dto.request.SignUpRequest;
import io.dongvelop.springsecuritypractice.common.enumtype.RoleType;
import io.dongvelop.springsecuritypractice.member.entity.Member;
import io.dongvelop.springsecuritypractice.member.entity.MemberRole;
import io.dongvelop.springsecuritypractice.member.repository.MemberRepository;
import io.dongvelop.springsecuritypractice.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입
     *
     * @param request : 회원가입 요청
     * @return : 회원가입 결과 문구
     */
    @Transactional
    public Long signUp(final SignUpRequest request) {

        // ID 중복 검사
        final boolean isDuplicated = memberRepository.existsByLogId(request.loginId());
        if (isDuplicated) {
            log.debug("login id[{}] is duplicated", request.loginId());
            throw new IllegalArgumentException();
        }

        // 회원 생성
        final Member member = memberRepository.save(request.toEntity());

        // 회원 ROLE 생성
        final MemberRole memberRole = MemberRole.builder()
                .role(RoleType.MEMBER)
                .member(member)
                .build();
        memberRoleRepository.save(memberRole);

        return member.getId();
    }


    /**
     * 로그인 (토큰 발급)
     *
     * @param request : 로그인 요청
     * @return : Bearer Token
     */
    public TokenInfo login(final LoginRequest request) {

        // LoginRequest 에 있는 정보를 가지고 UsernamePasswordAuthenticationToken 생성
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(request.loginId(), request.password());

        // 위에서 생성한 UsernamePasswordAuthenticationToken로 AuthenticationManagerBuilder.authenticate() 메서드를 호출
        // -> CustumUserDetailsService.loadUserByUsername()를 호출하여 DB에 있는 사용자 정보와 비교하도록 되어있음
        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.createToken(authentication);
    }


    /**
     * 내 정보 조회
     *
     * @param id : 조회할 멤버 아이디
     * @return : 조회 결과
     */
    public Member searchMyInfo(final Long id) {

        return memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.debug("id[{}] not found", id);
                    return new IllegalArgumentException();
                });
    }
}
