package demo.stargram.config;

import demo.stargram.controller.AccountController;
import demo.stargram.domain.Account;
import demo.stargram.dto.RegisterDto;
import demo.stargram.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class InitData {

    private final AccountController accountController;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initAccount() {
        RegisterDto testUser = RegisterDto.builder()
                .name("tempname")
                .email("testEmail@test.com")
                .username("tempid1234")
                .password("temppassword1234!")
                .passwordCheck("temppassword1234!")
                .build();

        Account testManager = Account.builder()
                .name("managerName")
                .email("managerEmail@test.com")
                .title("managerAccount")
                .content("managerAccount")
                .picUrl("/img/manager_profile.jpg")
                .username("managerId")
                .password(bCryptPasswordEncoder.encode("managerPw"))
                .build();

        Account testAdmin = Account.builder()
                .name("adminName")
                .email("adminEmail@test.com")
                .title("adminAccount")
                .content("adminAccount")
                .picUrl("/img/admin_profile.jpg")
                .username("adminId")
                .password(bCryptPasswordEncoder.encode("adminPw"))
                .build();

        accountController.registerAccount(testUser);
        accountRepository.save(testManager);
        accountRepository.save(testAdmin);
    }


}
