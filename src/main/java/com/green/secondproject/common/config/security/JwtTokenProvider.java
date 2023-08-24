package com.green.secondproject.common.config.security;

import com.green.secondproject.common.repository.SchoolAdminRepository;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.SchoolAdminEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtTokenProvider {
    public final Key ACCESS_KEY;
    public final Key REFRESH_KEY;
    public final String TOKEN_TYPE;
    //public final long ACCESS_TOKEN_VALID_MS = 3_600_000L; // 1000L * 60 * 60 -> 1시간
    public final long ACCESS_TOKEN_VALID_MS = 1000L * 60 * 30;
    public final long REFRESH_TOKEN_VALID_MS = 1_296_000_000L; // 1000L * 60 * 60 * 24 * 15 -> 15일
    private final UserRepository userRepository;
    private final SchoolAdminRepository adminRepository;

    public JwtTokenProvider(@Value("${springboot.jwt.access-secret}") String accessSecretKey
                            , @Value("${springboot.jwt.refresh-secret}") String refreshSecretKey
                            , @Value("${springboot.jwt.token-type}") String tokenType, UserRepository userRepository,
                            SchoolAdminRepository adminRepository) {
        byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecretKey);
        this.ACCESS_KEY = Keys.hmacShaKeyFor(accessKeyBytes);

        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecretKey);
        this.REFRESH_KEY = Keys.hmacShaKeyFor(refreshKeyBytes);
        this.TOKEN_TYPE = tokenType;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public String generateJwtToken(String strIuser, List<String> roles, long token_valid_ms, Key key) {
        log.info("JwtTokenProvider - generateJwtToken: 토큰 생성 시작");
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(createClaims(strIuser, roles))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + token_valid_ms))
                .signWith(key)
                .compact();
        log.info("JwtTokenProvider - generateJwtToken: 토큰 생성 완료");
        return token;
    }

    private Claims createClaims(String strIuser, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(strIuser);
        claims.put("roles", roles);
        return claims;
    }

    public Authentication getAuthentication (String token) {
        log.info("JwtTokenProvider - getAuthentication: 토큰 인증 정보 조회 시작");
        //UserDetails userDetails = SERVICE.loadUserByUsername(getUsername(token));
        UserDetails userDetails = getUserDetailsFromToken(token, ACCESS_KEY);
        log.info("JwtTokenProvider - getAuthentication: 토큰 인증 정보 조회 완료, UserDetails UserName : {}"
                , userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private UserDetails getUserDetailsFromToken(String token, Key key) {
        Claims claims = getClaims(token, key);
        String strIuser = claims.getSubject();
        Long id = Long.valueOf(strIuser);
        List<String> roles = (List<String>)claims.get("roles");

        log.info(roles.get(0));
        if ("ROLE_ADMIN".equals(roles.get(0))) {
            SchoolAdminEntity admin = adminRepository.findById(id).get();
            return MyUserDetails.builder()
                    .userId(admin.getAdminId())
                    .email(admin.getEmail())
                    .pw(admin.getPw())
                    .schoolNm(admin.getSchoolEntity().getNm())
                    .build();
        }

        UserEntity user = userRepository.findById(Long.valueOf(strIuser)).get();

        return MyUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .pw(user.getPw())
                .nm(user.getNm())
                .vanId(user.getVanEntity().getVanId())
                .schoolNm(user.getVanEntity().getSchoolEntity().getNm())
                .grade(user.getVanEntity().getGrade())
                .classNum(user.getVanEntity().getClassNum())
                .pic(user.getPic())
                .roles(roles)
                .build();
    }

    public String resolveToken(HttpServletRequest req, String type) {
        log.info("JwtTokenProvider - resolveToken: HTTP 헤더에서 Token 값 추출");
        String headerAuth = req.getHeader("authorization");
        return headerAuth != null &&
                headerAuth.startsWith(String.format("%s ", type)) ? headerAuth.substring(type.length()).trim() : null;
    }

    public Claims getClaims(String token, Key key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidateToken(String token, Key key) {
        log.info("JwtTokenProvider - isValidateToken: 토큰 유효 체크 시작");
        try {
            return !getClaims(token, key).getExpiration().before(new Date()); //현재 시간보다 만료 전 > true
        } catch (Exception e) {
            log.info("JwtTokenProvider - isValidateToken: 토큰 유효 체크 예외 발생");
            return false;
        }
    }

    public Long getTokenExpirationTime(String accessToken, Key accessKey) {
        try {
            return getClaims(accessToken, accessKey).getExpiration().getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
