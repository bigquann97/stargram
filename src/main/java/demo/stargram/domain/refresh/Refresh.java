package demo.stargram.domain.refresh;

import demo.stargram.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Table(name = "auth")
@Entity
public class Refresh {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @Builder
    public Refresh(String refreshToken, Account account) {
        this.refreshToken = refreshToken;
        this.account = account;
    }

    public void refreshUpdate(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
