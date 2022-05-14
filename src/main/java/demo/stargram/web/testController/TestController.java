package demo.stargram.web.testController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/testA")
    public String forTest() {
        return "test completed";
    }

    @GetMapping("/testB")
    public String forTest2() {
        return "test completed";
    }
}
