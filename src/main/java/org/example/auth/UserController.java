package org.example.auth;

import liquibase.pro.packaged.S;
import lombok.NonNull;
import org.example.auth.dto.AdminUserDto;
import org.example.auth.dto.AuthenticationRequestDto;
import org.example.auth.dto.RegistrationUserDto;
import org.example.auth.dto.UserDto;
import org.example.components.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationRestController authenticationRestController;
    @Autowired
    public UserController(UserService userService, AuthenticationRestController authenticationRestController) {
        this.userService = userService;
        this.authenticationRestController = authenticationRestController;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id,
                                               @NonNull HttpServletRequest request){

        return userService.getUser(id, request);
    }
    @GetMapping()
    public String getAllUsers(Model model){
        List<AdminUserDto> users = userService.getAll();
        model.addAttribute("users", users);
        return "user";
    }
    @PostMapping
    public ResponseEntity<?> registryAdmin(@RequestBody RegistrationUserDto registrationUserDto,
                                                @NonNull HttpServletRequest request){
        ResponseEntity<?> answer = userService.registryAdmin(registrationUserDto.toUser(), request);
        if (answer.getStatusCode().equals(HttpStatus.FORBIDDEN))return answer;
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto(registrationUserDto.getUsername(),
                registrationUserDto.getPassword());
        return authenticationRestController.login(requestDto);
    }
    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestParam Long id,@NonNull HttpServletRequest request ){
        return userService.delete(id, request);
    }

}
