package io.dongvelop.springsecuritypractice.member.repository;

import io.dongvelop.springsecuritypractice.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByLogId(final String loginId);

    Optional<Member> findByLogId(final String loginId);
}
