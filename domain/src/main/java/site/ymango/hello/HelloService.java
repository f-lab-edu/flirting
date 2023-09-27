package site.ymango.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository helloRepository;

    public String helloWorld() {
        helloRepository.findAll();
        return "Hello, world!";
    }
}
