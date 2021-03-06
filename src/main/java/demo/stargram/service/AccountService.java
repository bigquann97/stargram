package demo.stargram.service;

import demo.stargram.config.jwt.JwtTokenProvider;
import demo.stargram.domain.account.Account;
import demo.stargram.web.dto.account.LoginDto;
import demo.stargram.web.dto.account.RegisterDto;
import demo.stargram.domain.account.AccountRepository;
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
    private final JwtTokenProvider jwtTokenProvider;

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

        return true; // 회원가입 가능
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

    // 로그인 유효성 검증
    public boolean validateLogin(LoginDto loginDto) {
        Optional<Account> found = accountRepository.findUserByUsername(loginDto.getUsername());

        if(found.isEmpty()) {
            log.info("존재하지 않은 ID의 로그인 시도");
            return false;
        }

        String encodedPw = found.get().getPassword();

        if(!bCryptPasswordEncoder.matches(loginDto.getPassword(), encodedPw)) {
            log.info("비밀번호 불일치 로그인 시도");
            return false;
        }

        return true;
    }

    // 로그인 로직 - last login 수정 + Account 받아오기
    @Transactional
    public Account login(LoginDto loginDto) {
        Account found = accountRepository.findUserByUsername(loginDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정"));
        return found;
    }

    public Account findAccount(String userId) {
        Account account = accountRepository.findUserByUsername(userId).orElseThrow(() -> new UsernameNotFoundException("없는 유저"));
        return account;
    }

}
