package io.dongvelop.springsecuritypractice.common.service;

import io.dongvelop.springsecuritypractice.member.entity.Member;
import io.dongvelop.springsecuritypractice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final Member member = memberRepository.findByLogId(username)
                .orElseThrow(() -> new UsernameNotFoundException("not exist member"));

        return createUserDetails(member);
    }

    private UserDetails createUserDetails(final Member member) {
        return new User(
                member.getLogId(),
                passwordEncoder.encode(member.getPassword()),
                member.getMemberRole()
                        .stream()
                        .map(it -> new SimpleGrantedAuthority("ROLE_" + it))
                        .toList()
        );
    }
}
