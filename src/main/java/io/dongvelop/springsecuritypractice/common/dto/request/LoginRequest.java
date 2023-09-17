package io.dongvelop.springsecuritypractice.common.dto.request;

public record LoginRequest(
        String loginId,
        String password
) {
}
