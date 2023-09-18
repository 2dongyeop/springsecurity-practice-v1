package io.dongvelop.springsecuritypractice.member.controller;

import io.dongvelop.springsecuritypractice.common.authority.TokenInfo;
import io.dongvelop.springsecuritypractice.common.dto.request.LoginRequest;
import io.dongvelop.springsecuritypractice.common.dto.request.MemberDto;
import io.dongvelop.springsecuritypractice.common.dto.request.SignUpRequest;
import io.dongvelop.springsecuritypractice.common.dto.response.GetMemberResponse;
import io.dongvelop.springsecuritypractice.common.entity.CustomUser;
import io.dongvelop.springsecuritypractice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 API
     *
     * @param request : 회원가입 요청
     * @return 회원가입 결과 문구
     */
    @PostMapping("/signup")
    public Long signUp(@RequestBody final SignUpRequest request) {

        log.debug("signup request[{}]", request);
        return memberService.signUp(request);
    }


    /**
     * 로그인 API
     *
     * @param request : 로그인 요청
     * @return : 토큰 정보
     */
    @PostMapping("/login")
    public TokenInfo login(@RequestBody final LoginRequest request) {

        log.debug("login request[{}]", request);
        return memberService.login(request);
    }


    /**
     * 내 정보 조회 API
     *
     * @return : 조회 결과
     */
    @GetMapping("/info")
    public GetMemberResponse searchMyInfo() {

        final CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Long memberId = principal.getUserId();

        log.debug("member id[{}]", memberId);
        return new GetMemberResponse(memberService.searchMyInfo(memberId));
    }


    @PutMapping("/info")
    public Long updateInfo(@RequestBody final MemberDto memberDto) {

        final CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberService.update(principal.getUserId(), memberDto);
    }
}
