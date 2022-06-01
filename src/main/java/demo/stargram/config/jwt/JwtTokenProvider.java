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

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private String secretKey = "stargram"; // base64로 인코딩될 예정

    private long accessTokenValidTime = 1000L * 60 * 1; // 60초 * 1 = 1분
    private long refreshTokenValidTime = 1000L * 60 * 2; // 60초 * 30 = 2분

    private final PrincipalDetailService principalDetailService;

    // 객체 초기화, secretKey Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        Claims claims = Jwts.claims().setSubject(userPk);

        // 정보는 key / value 쌍으로 저장된다.
        claims.put("roles", roles);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret 값 세팅
                .compact();
    }

    // 인증 성공시 SecurityContextHolder에 저장할 Authentication 객체 생성
    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        PrincipalDetail principalDetail = principalDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(principalDetail, "", principalDetail.getAuthorities());
    }
    
    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값 가져오기 - "Authorization" : "TOKEN값"
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
