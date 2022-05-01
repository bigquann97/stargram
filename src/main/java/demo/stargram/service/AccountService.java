package demo.stargram.service;

import demo.stargram.domain.Account;
import demo.stargram.dto.RegisterDto;
import demo.stargram.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입 유효성 체크
    public boolean validateSignup(RegisterDto registerDto) {
        Optional<Account> found = accountRepository.findUserByUsername(registerDto.getUsername());

        if(found.isPresent()) {
            log.info("이미 가입된 회원");
            return false;
        }

        if (!registerDto.getPassword().matches(registerDto.getPasswordCheck())) {
            log.info("비밀번호 확인값과 일치하지 않음");
            return false;
        }

        return true; // 정상 회원가입
    }

    // 회원가입
    @Transactional
    public Account registerAccount(RegisterDto registerDto) {
        return accountRepository.save(
                Account.builder()
                        .username(registerDto.getUsername())
                        .password(bCryptPasswordEncoder.encode(registerDto.getPassword()))
                        .roles(Collections.singletonList("ROLE_USER"))
                        .picUrl("/img/default_profile.jpg")
                        .name(registerDto.getName())
                        .email(registerDto.getEmail())
                        .build()
        );
    }

    // 로그인 로직 - last login 수정 + Account 받아오기
    @Transactional
    public Account login(Map<String, String> account) {
        LocalDateTime now = LocalDateTime.now();
        Account found = accountRepository.findUserByUsername(account.get("username")).orElseThrow(() -> new UsernameNotFoundException("유저 찾을 수 없음"));
        found.setLastLogin(now);
        return found;
    }
    
    // 로그인 유효성 검증
    public boolean validateLogin(Map<String, String> account) {
        Optional<Account> found = accountRepository.findUserByUsername(account.get("username"));

        if(found.isEmpty()) {
            log.info("가입되지 않은 회원");
            return false;
        }

        String encodedPw = found.get().getPassword();

        if(!bCryptPasswordEncoder.matches(account.get("password"), encodedPw)) {
            log.info("비밀번호 불일치");
            return false;
        }

        return true;
    }
}
