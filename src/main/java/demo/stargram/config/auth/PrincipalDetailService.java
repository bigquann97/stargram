package demo.stargram.config.auth;

import demo.stargram.domain.account.Account;
import demo.stargram.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public PrincipalDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));

        return new PrincipalDetail(account);
    }

}
