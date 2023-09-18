package io.dongvelop.springsecuritypractice.common.dto.response;

import io.dongvelop.springsecuritypractice.member.entity.Member;

import java.time.format.DateTimeFormatter;

public record GetMemberResponse(
        Long id,
        String loginId,
        String name,
        String birthDate,
        String gender,
        String email
) {

    public GetMemberResponse(final Member member) {
        this(
                member.getId(),
                member.getLogId(),
                member.getName(),
                member.getBirthDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                member.getGendertype().getDesc(),
                member.getEmail()
        );
    }
}
