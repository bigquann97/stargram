package demo.stargram.service;

import demo.stargram.domain.Account;
import demo.stargram.dto.LoginDto;
import demo.stargram.dto.RegisterDto;
import demo.stargram.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Transactional
    public boolean updateLastLogin(Account account) {
        LocalDateTime now = LocalDateTime.now();
        account.setLastLogin(now);
        return true;
    }

    @Transactional
    public boolean save(RegisterDto registerDto) {

        if (accountRepository.findUserByUsername(registerDto.getUsername()).orElse(null) != null) {
            return false; // 이미있는회원
        }

        accountRepository.save(Account.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .password(bCryptPasswordEncoder.encode(registerDto.getPassword()))
                .picUrl("/img/default_profile.jpg")
                .title(null)
                .content(null)
                .build());
        return true;
    }

    @Transactional
    public boolean login(LoginDto loginDto) {
        Optional<Account> OptionalAccount = accountRepository.findUserByUsername(loginDto.getUsername());

        if(OptionalAccount.isPresent()) {
            Account account = OptionalAccount.get();
            if(bCryptPasswordEncoder.matches(loginDto.getPassword(), account.getPassword())) {
                return true;
            } else {
                throw new IllegalArgumentException("잘못된 비밀번호");
            }
        } else {
            throw new IllegalArgumentException("잘못된 로그인 정보");
        }

    }


}
