package io.dongvelop.springsecuritypractice.common.dto.request;

import io.dongvelop.springsecuritypractice.common.enumtype.Gendertype;
import io.dongvelop.springsecuritypractice.member.entity.Member;

import java.time.LocalDate;

public record SignUpRequest(
        String loginId,
        String password,
        String name,
        LocalDate birthDate,
        Gendertype gender,
        String email
) {

    public Member toEntity() {
        return Member.builder()
                .logId(loginId)
                .password(password)
                .name(name)
                .birthDate(birthDate)
                .gendertype(gender)
                .email(email)
                .build();
    }
}
