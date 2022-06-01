package demo.stargram.web.controller;

import com.google.gson.JsonObject;
import demo.stargram.config.jwt.JwtTokenProvider;
import demo.stargram.domain.account.Account;
import demo.stargram.web.dto.account.LoginDto;
import demo.stargram.web.dto.account.RegisterDto;
import demo.stargram.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity registerAccount(@RequestBody @Valid RegisterDto registerDto) {
        boolean isAvailable = accountService.validateSignup(registerDto);

        if (isAvailable) {
            accountService.registerAccount(registerDto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody @Valid LoginDto loginDto) {
        boolean isAvailable = accountService.validateLogin(loginDto);

        if (isAvailable) {
            Account acc = accountService.login(loginDto);

            JsonObject returnObj = new JsonObject();
            String accessToken = jwtTokenProvider.createToken(acc.getUsername(), acc.getRoles());

            returnObj.addProperty("token", accessToken);
            returnObj.addProperty("username", jwtTokenProvider.getUserPk(accessToken));

            return ResponseEntity.ok(returnObj);
        } else {
            throw new IllegalArgumentException("잘못된 ID or PW");
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity logout() {
        return null;
    }


}