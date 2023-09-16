package io.dongvelop.springsecuritypractice.common.enumtype;

public enum ResultType {

    SUCCESS("정상 처리 완료"),
    ERROR("에러 발생");

    private final String msg;

    ResultType(final String msg) {
        this.msg = msg;
    }
}
