package demo.stargram.domain.account;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String username; // id

    @Column(nullable = false)
    private String password; // pw

    private String picUrl; // 프로필사진
    
    private String title; // 자기소개 제목
    
    private String content; // 자기소개 내용

    @CreatedDate
    private LocalDateTime createdAt; // 생성시간

    @LastModifiedDate
    private LocalDateTime modifiedAt; // 수정 시간

    private LocalDateTime lastLogin; // 마지막 로그인시간

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();



}
