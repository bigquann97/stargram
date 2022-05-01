package demo.stargram.repository;

import demo.stargram.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findUserByUsername(String name);

}
