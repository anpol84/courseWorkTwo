package org.example.auth;

import liquibase.pro.packaged.S;
import lombok.NonNull;
import org.example.auth.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<String> getUserById(@PathVariable(name = "id") Long id,
                                               @NonNull HttpServletRequest request){

        return userService.getUser(id, request);
    }
    @GetMapping()
    public ResponseEntity<String> getAllUsers(@NonNull HttpServletRequest request){
        return userService.getAll(request);
    }
    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestParam Long id,@NonNull HttpServletRequest request ){
        return userService.delete(id, request);
    }
}
