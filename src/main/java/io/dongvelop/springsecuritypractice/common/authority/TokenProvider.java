package io.dongvelop.springsecuritypractice.common.authority;

import io.dongvelop.springsecuritypractice.common.entity.CustomUser;
import io.dongvelop.springsecuritypractice.common.util.Const;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") final String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public TokenInfo createToken(final Authentication authentication) {

        // authentication.getAuthorities()의 결과가 ["ROLE_USER", "ROLE_ADMIN"] 이런식으로 나오면
        final String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // 매핑 후 조인을 통해 "ROLE_USER,ROLE_ADMIN" 으로 결과를 바꿈

        final Date now = new Date();
        final Date accessTokenExpiration = new Date(now.getTime() + Const.EXPIRATION_MILLISECONDS);

        // 토큰 생성
        final CustomUser principal = (CustomUser) authentication.getPrincipal();
        final String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("userId", principal.getUserId())
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new TokenInfo(Const.BEARER, accessToken);
    }

    /**
     * 토큰으로부터 인증 정보 추출
     *
     * @param token : 엑세스 토큰
     * @return : 인증 정보
     */
    public Authentication getAuthentication(final String token) {

        // 토큰으로부터 Claims 추출
        final Claims claims = getClaims(token);

        // claims에서 authorities 추출 (ex. "ROLE_USER","ROLE_ADMIN" 형태)
        final String authoritiesString = (String) claims.get("auth");
        final Long userId = Long.valueOf(String.valueOf(claims.get("userId")));
        if (authoritiesString == null) {
            throw new IllegalArgumentException();
        }

        // 다시 ","를 기준으로 배열로 각각 나움 (ex. ["ROLE_USER", "ROLE_ADMIN"])
        final String[] authority = authoritiesString.split(",");
        final var authorities = Arrays.stream(authority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 사용자 정보 생성
        final UserDetails principal = new CustomUser(userId, claims.getSubject(), "", authorities);

        // 토큰 생성
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims getClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(final String token) {

        try {
            getClaims(token);
            return true;
        } catch (final SecurityException securityException) {
            // catch logic
            log.debug("token[{}] is invalid(securityException)", token);
        } catch (final MalformedJwtException malformedJwtException) {
            // catch logic
            log.debug("token[{}] is invalid(malformedJwtException)", token);
        } catch (final ExpiredJwtException expiredJwtException) {
            // catch logic
            log.debug("token[{}] is expired(expiredJwtException)", token);
        } catch (final UnsupportedJwtException unsupportedJwtException) {
            // catch logic
            log.debug("token[{}] is unsupported(unsupportedJwtException)", token);
        } catch (final IllegalArgumentException illegalArgumentException) {
            // catch logic
            log.debug("token[{}] claim is empty(illegalArgumentException)", token);
        }
        return false;
    }
}
