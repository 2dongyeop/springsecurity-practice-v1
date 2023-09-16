package io.dongvelop.springsecuritypractice.common.dto.response;

import io.dongvelop.springsecuritypractice.common.enumtype.ResultType;

public record BaseResponse<T>(

        String resultCode,
        T data,
        String msg
) {
}
