package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // 홈화면(루트 경로)과 매핑
    public String home(){
        return "home"; // 뷰 이름을 반환. 여기서는 리소스 내 /templates/home.html을 보여주게 된다.
    }
}
