package demo.stargram.temp;

import javax.persistence.*;

@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ===========================

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Account account;

}
