package demo.controller;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class HelloController {

    @PostMapping(value = "/some", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity some(@RequestBody String some){
        return ResponseEntity.ok("{\"test\": \"test1\"}");
    }

    @GetMapping(value = "/healthz")
    public ResponseEntity test() {
        return ResponseEntity.ok("UP");
    }
}
