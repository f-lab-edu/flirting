package site.ymango.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {
  private final HelloService helloService;

  @GetMapping
  public String helloWorld() {
    return helloService.helloWorld();
  }
}
