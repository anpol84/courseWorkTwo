package org.example.auth;


import org.example.auth.dto.AdminUserDto;
import org.example.auth.dto.AuthenticationRequestDto;
import org.example.auth.dto.RegistrationUserDto;

import org.example.components.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getUserById(@PathVariable(name = "id") Long id, Model model){
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user_info";
    }
    @GetMapping()
    public String getAllUsers(Model model){
        List<AdminUserDto> users = userService.getAll();
        model.addAttribute("users", users);
        return "user";
    }
    @GetMapping("/update")
    public String updatePage(@ModelAttribute RegistrationUserDto user, @RequestParam Long id, Model model){
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        return "user_put";
    }
    @PostMapping
    public String registryAdmin(@ModelAttribute RegistrationUserDto registrationUserDto, Model model){
        userService.registryAdmin(registrationUserDto.toUser());
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto(registrationUserDto.getUsername(),
                registrationUserDto.getPassword());
        authenticationRestController.login(requestDto);
        return getAllUsers(model);
    }
    @DeleteMapping()
    public String deleteUser(@RequestParam Long id, Model model){
        userService.delete(id);
        return getAllUsers(model);
    }

    @PutMapping
    public String update(@ModelAttribute RegistrationUserDto registrationUserDto, @RequestParam Long id,
                         Model model){
        userService.update(registrationUserDto.toUser(), id);
        return getAllUsers(model);
    }
}
