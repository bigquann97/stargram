package demo.stargram.config.jwt;

import demo.stargram.config.auth.PrincipalDetail;
import demo.stargram.config.auth.PrincipalDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private String secretKey = "stargram"; // base64로 인코딩될 예정

    private long tokenValidTime = 1000L * 60 * 30; // 60초 * 30 = 30분

    private final PrincipalDetailService principalDetailService;

    // 객체 초기화, secretKey Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 인증 성공시 SecurityContextHolder에 저장할 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        PrincipalDetail principalDetail = principalDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(principalDetail, "", principalDetail.getAuthorities());
    }
    
    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값 가져오기 - "X-AUTH-TOKEN" : "TOKEN값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
    
    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
