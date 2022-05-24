package demo.stargram.web.dto.account;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
public class RegisterDto {

    @Email
    @NotBlank(message = "EMAIL 값 입력은 필수입니다")
    String email;

    @NotBlank(message = "NAME 값 입력은 필수입니다")
    @Size(min = 1, max = 10, message = "이름은 1 ~ 10자 이어야 합니다.")
    String name;

    @NotBlank(message = "ID 값 입력은 필수입니다")
    String username;

    @NotBlank(message = "PW 값 입력은 필수입니다")
    String password;

    @NotBlank(message = "PW CHECK 값 입력은 필수입니다")
    String passwordCheck;

}

