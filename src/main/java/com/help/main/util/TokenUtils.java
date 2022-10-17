package com.help.main.util;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.help.main.entity.LoginUser;
import com.help.main.service.MemberService;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils {


    private static final String secretKey = "ThisIsA_SecretKeyForJwtExample";    

    public static String generateJwtToken(LoginUser loginUser) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(loginUser.getLoginId())     //토큰제목
                .setHeader(createHeader())              // 헤더
                .setClaims(createClaims(loginUser))     // 페이로드
                .setExpiration(createExpireDateForOneYear())    // 토큰 만료기한
                .signWith(SignatureAlgorithm.HS256, createSigningKey());    // 서명. 사용할 암호화 알고리즘과 signature 에 들어갈 secretKey 세팅

        return builder.compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime :" + claims.getExpiration());
            log.info("loginId :" + claims.get("loginId"));
            log.info("role :" + claims.get("role"));
            return true;

        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Date createExpireDateForOneYear() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);

        /*
        //토큰 유효시간 설정
        Long tokenValidTime = 240 * 60 * 1000L;
        Date now = new Date();
        new Date(now.getTime());
        (now.getTime() + tokenValidTime);
         */
        
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private static Map<String, Object> createClaims(LoginUser loginUser) {
        // 공개 클레임에 사용자의 id와 권한을 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();

        claims.put("loginId", loginUser.getLoginId());
        claims.put("role", loginUser.getAuthorities());

        return claims;
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    private static String getUserIdFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("loginId");
    }

    // private static UserRole getRoleFromToken(String token) {
    //     Claims claims = getClaimsFormToken(token);
    //     return (UserRole) claims.get("role");
    // }


}