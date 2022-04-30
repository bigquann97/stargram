package demo.stargram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import demo.stargram.config.JwtTokenProvider;
import demo.stargram.domain.Account;
import demo.stargram.dto.LoginDto;
import demo.stargram.dto.RegisterDto;
import demo.stargram.dto.UserDto;
import demo.stargram.repository.AccountRepository;
import demo.stargram.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    // 회원가입
    @PostMapping("/api/signup")
    public ResponseEntity registerAccount(@RequestBody @Valid RegisterDto registerDto) {
        Optional<Account> found = accountRepository.findUserByUsername(registerDto.getUsername());
        if (found.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        if (!registerDto.getPassword().equals(registerDto.getPasswordCheck())) {
            return ResponseEntity.badRequest().build();
        }

        accountService.registerAccount(registerDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody Map<String, String> account) throws JsonProcessingException {
        Account acc = accountRepository.findUserByUsername(account.get("username")).orElseThrow(() -> new IllegalArgumentException("가입되지 않은 계정입니다."));

        if (!bCryptPasswordEncoder.matches(account.get("password"), acc.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        accountService.updateLastLogin(acc);
        JsonObject obj = new JsonObject();
        JsonObject obj2 = new JsonObject();

        String token = jwtTokenProvider.createToken(acc.getUsername(), acc.getRoles());

        UserDto userDto = UserDto.builder()
                        .name(acc.getName())
                        .token(token)
                        .build();

        String userPk = jwtTokenProvider.getUserPk(token);

        obj2.addProperty("user", userPk);
        obj2.addProperty("token", token);

        String s = objectMapper.writeValueAsString(userDto);


        obj.addProperty("ok", true);
        obj.add("result", obj2);

        return ResponseEntity.ok(obj);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity logout() {
        return null;
    }


}
