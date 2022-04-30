package demo.stargram.temp;

//import lombok.*;

import javax.persistence.*;

@Entity
//@Getter
//@Setter
//@EqualsAndHashCode(of = "id")
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer Count;

    private boolean isHeart;

    // ==========================
//
//    @ManyToOne
//    private Article article;
//
//    @ManyToOne
//    private Comment comment;
//
//    @ManyToOne
//    private Account account;
}
