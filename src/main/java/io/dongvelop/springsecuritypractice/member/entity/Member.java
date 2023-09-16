package io.dongvelop.springsecuritypractice.member.entity;

import io.dongvelop.springsecuritypractice.common.enumtype.Gendertype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, length = 30, updatable = false)
    private String logId;

    @Column(nullable = false, length = 100)
    String password;

    @Column(nullable = false, length = 10)
    String name;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    LocalDate birthDate;

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    Gendertype gendertype;

    @Column(nullable = false, length = 30)
    String email;
}
