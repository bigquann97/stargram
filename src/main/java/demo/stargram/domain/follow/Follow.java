package demo.stargram.domain.follow;

import demo.stargram.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
//@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "from_user_id")
    @ManyToOne
    private Account fromAccount;

    @JoinColumn(name = "to_user_id")
    @ManyToOne
    private Account toAccount;


}
