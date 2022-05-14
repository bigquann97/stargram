package demo.stargram.web.controller;

import com.google.gson.JsonObject;
import demo.stargram.config.auth.PrincipalDetail;
import demo.stargram.domain.account.Account;
import demo.stargram.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final AccountService accountService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> myProfile(@PathVariable String userId, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        String username = principalDetail.getUsername();
        Account foundAccount = accountService.findAccount(userId);

        JsonObject result = new JsonObject();
        result.addProperty("username", foundAccount.getUsername());
        result.addProperty("follower", 0);
        result.addProperty("following", 0);
        result.addProperty("title", foundAccount.getTitle());
        result.addProperty("content", foundAccount.getContent());
        result.addProperty("picUrl", foundAccount.getPicUrl());

        if (userId == username) {
            // 로그인 유저가 본인 계정 페이지에 접근 로직
            result.addProperty("isMyPage", true);
        } else {
            // 로그인 유저가 타 계쩡 페이지에 접근 로직
            result.addProperty("isMyPage", false);
        }

        JsonObject outer = new JsonObject();
        outer.add("result", result);

        return new ResponseEntity<>("asdf", HttpStatus.OK);
    }
}
