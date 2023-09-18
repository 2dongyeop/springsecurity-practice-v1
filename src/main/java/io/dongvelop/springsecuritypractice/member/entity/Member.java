package io.dongvelop.springsecuritypractice.member.entity;

import io.dongvelop.springsecuritypractice.common.enumtype.Gendertype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Gendertype gendertype;

    @Column(nullable = false, length = 30)
    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberRole> memberRole = new ArrayList<>();

    public void updateName(final String name) {
        this.name = name;
    }
}
