package demo.stargram.web.controller;

import demo.stargram.domain.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/info")
    public ResponseEntity findAccount() {
        return ResponseEntity.ok("info");
    }

    @GetMapping("/user")
    public ResponseEntity user() {
        return ResponseEntity.ok("user");
    }

    @GetMapping("/manager")
    public ResponseEntity manager() {
        return ResponseEntity.ok("manager");
    }

    @GetMapping("/admin")
    public ResponseEntity admin() {
        return ResponseEntity.ok("admin");
    }
}
