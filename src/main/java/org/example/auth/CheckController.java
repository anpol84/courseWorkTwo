package org.example.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/check")
public class CheckController {
    @GetMapping
    public ResponseEntity checkToken(){
        System.out.println(1321);
        return ResponseEntity.ok(200);
    }
}
