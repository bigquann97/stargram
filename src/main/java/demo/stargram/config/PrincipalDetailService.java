package demo.stargram.config;

import demo.stargram.domain.Account;
import demo.stargram.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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
