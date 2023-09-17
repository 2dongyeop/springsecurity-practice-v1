package io.dongvelop.springsecuritypractice.member.repository;

import io.dongvelop.springsecuritypractice.member.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
