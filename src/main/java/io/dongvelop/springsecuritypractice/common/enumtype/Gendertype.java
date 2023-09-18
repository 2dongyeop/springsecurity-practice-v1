package io.dongvelop.springsecuritypractice.common.enumtype;

import lombok.Getter;

@Getter
public enum Gendertype {

    MAN("남"),
    WOMAN("여");

    private String desc;

    Gendertype(final String desc) {
        this.desc = desc;
    }
}
