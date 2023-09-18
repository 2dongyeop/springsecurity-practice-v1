package io.dongvelop.springsecuritypractice.common.dto.request;

import io.dongvelop.springsecuritypractice.common.enumtype.Gendertype;
import io.dongvelop.springsecuritypractice.member.entity.Member;

import java.time.LocalDate;

public record MemberDto(
        Long id,
        String loginId,
        String password,
        String name,
        LocalDate birthDate,
        Gendertype gender,
        String email
) {

    public Member toEntity(final Long id) {
        return Member.builder()
                .id(id)
                .logId(loginId)
                .password(password)
                .name(name)
                .birthDate(birthDate)
                .gendertype(gender)
                .email(email)
                .build();
    }
}
