package demo.stargram.config.initData;

import demo.stargram.web.controller.AccountController;
import demo.stargram.domain.account.Account;
import demo.stargram.web.dto.account.RegisterDto;
import demo.stargram.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class InitData {

    private final AccountController accountController;

    @PostConstruct
    public void initAccount() {
        RegisterDto testUser = RegisterDto.builder()
                .name("tempname")
                .email("testEmail@test.com")
                .username("tempid1234")
                .password("temppassword1234!")
                .passwordCheck("temppassword1234!")
                .build();

        accountController.registerAccount(testUser);
    }

}
