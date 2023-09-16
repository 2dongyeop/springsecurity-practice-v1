package io.dongvelop.springsecuritypractice.member.service;

import io.dongvelop.springsecuritypractice.common.dto.request.SignUpRequest;
import io.dongvelop.springsecuritypractice.member.entity.Member;
import io.dongvelop.springsecuritypractice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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

        return member.getId();
    }
}
