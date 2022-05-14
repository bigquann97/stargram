package demo.stargram.web.controller;

import com.google.gson.JsonObject;
import demo.stargram.config.jwt.JwtTokenProvider;
import demo.stargram.domain.account.Account;
import demo.stargram.web.dto.account.RegisterDto;
import demo.stargram.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

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
        if(isAvailable) {
            accountService.registerAccount(registerDto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 로그인
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody Map<String, String> account) {
        boolean isAvailable = accountService.validateLogin(account);

        if (isAvailable) {
            Account acc = accountService.login(account);
            JsonObject outerObj = new JsonObject();
            JsonObject innerObj = new JsonObject();

            String token = jwtTokenProvider.createToken(acc.getUsername(), acc.getRoles());

            innerObj.addProperty("token", token);
            innerObj.addProperty("username", jwtTokenProvider.getUserPk(token));

            outerObj.addProperty("ok", true);
            outerObj.add("result", innerObj);

            return ResponseEntity.ok(outerObj);
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
