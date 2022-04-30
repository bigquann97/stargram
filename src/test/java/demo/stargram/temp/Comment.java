package demo.stargram.temp;

//import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    // ============================

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Account account;
//
//    @ManyToOne
//    private Article article;
//
//    @OneToMany(mappedBy = "comment", orphanRemoval = true, cascade = CascadeType.REMOVE)
//    private List<Heart> hearts;

}
