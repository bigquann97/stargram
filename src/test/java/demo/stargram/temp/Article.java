package demo.stargram.temp;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
    private List<String> imgUrls = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    // =========================

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Account account;
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @Builder.Default
//    private Set<Comment> comments = new HashSet<>();
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @Builder.Default
//    private Set<Heart> hearts = new HashSet<>();

}
