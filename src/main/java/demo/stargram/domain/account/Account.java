package demo.stargram.domain.account;

import demo.stargram.domain.refresh.Refresh;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Table(name = "ACCOUNT")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQUENCE_ID")
    private Long id;

    @Column(nullable = false, name = "USER_NAME")
    private String name; // 이름

    @Column(nullable = false, name = "USER_EMAIL", unique = true)
    private String email; // 이메일

    @Column(nullable = false, name = "USER_ID", unique = true)
    private String username; // id

    @Column(nullable = false, name = "USER_PW")
    private String password; // pw

    @Column(name = "USER_PIC")
    private String picUrl; // 프로필사진

    @Column(name = "USER_TITLE")
    private String title; // 자기소개 제목

    @Column(name = "USER_CONTENT")
    private String content; // 자기소개 내용

    @CreatedDate
    @Column(name = "USER_CREATED_AT")
    private LocalDateTime createdAt; // 생성시간

    @LastModifiedDate
    @Column(name = "USER_LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt; // 수정 시간

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

}
