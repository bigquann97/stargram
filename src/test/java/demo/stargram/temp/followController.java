package demo.stargram.temp;

//import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
public class followController {

//    private final FollowService followService;

    @PostMapping("/{userId}/follow")
    public ResponseEntity followUser() {
        return null;
    }

    @PostMapping("/{userId}/unfollow")
    public ResponseEntity unfollowUser() {
        return null;
    }

    @GetMapping("/{userId}/follower")
    public ResponseEntity follower() {
        return null;
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity following() {
        return null;
    }
}
