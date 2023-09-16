package io.dongvelop.springsecuritypractice.member.controller;

import io.dongvelop.springsecuritypractice.common.dto.request.SignUpRequest;
import io.dongvelop.springsecuritypractice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return memberService.signUp(request);
    }
}
