package demo.stargram.temp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @GetMapping("/{username}/{articleId}/comment")
    public ResponseEntity viewComment() {
        return null;
    }

    @PostMapping("/{username}/{articleId}/comment")
    public ResponseEntity writeComment() {
        return null;
    }

    @PutMapping("/{username}/{articleId}/{commentId}")
    public ResponseEntity editComment() {
        return null;
    }

    @DeleteMapping("/{username}/{articleId}/{commentId}")
    public ResponseEntity deleteComment() {
        return null;
    }

}
