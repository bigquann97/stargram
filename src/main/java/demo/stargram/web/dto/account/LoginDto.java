package demo.stargram.web.dto.account;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class LoginDto {

    @NotNull(message = "ID 입력은 필수입니다.")
    String username;

    @NotNull(message = "PW 입력은 필수입니다.")
    String password;
}

/*
{
    "username": "tempid134",
    "password": "temppassword1234!"
}
 */