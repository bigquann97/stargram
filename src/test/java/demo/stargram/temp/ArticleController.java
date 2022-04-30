package demo.stargram.temp;

//import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
public class ArticleController {

//    private final ArticleService articleService;

    @PostMapping("/{username}")
    public ResponseEntity writeArticle(@RequestBody ArticleRequestDto articleRequestDto) {
//        articleService.writeArticle(articleRequestDto);
        return null;
    }

    @GetMapping("/{username}/{articleId}")
    public ResponseEntity viewArticle() {
        return null;
    }

    @PutMapping("/{username}/{articleId}")
    public ResponseEntity editArticle() {
        return null;
    }

    @DeleteMapping("/{username}/{articleId}")
    public ResponseEntity deleteArticle() {
        return null;
    }
}
