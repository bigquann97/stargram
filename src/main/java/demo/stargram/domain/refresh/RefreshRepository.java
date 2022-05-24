package demo.stargram.domain.refresh;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Optional<Refresh> findByAccountUsername(Long username);
}
