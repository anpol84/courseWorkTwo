package org.example.auth;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.auth.dto.AdminUserDto;
import org.example.auth.dto.UserDto;
import org.example.components.base.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }
    public boolean checkAdmin(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        for (Role role : user.getRoles()){
            if (role.getName().equals("ROLE_ADMIN")) return true;
        }
        return false;
    }
    public List<AdminUserDto> getAll() {

        List<User> result1 = userRepository.findAll();
        List<AdminUserDto> result2 = new ArrayList<>();
        for(User user : result1){
             result2.add(AdminUserDto.fromUser(user));
        }
        log.info("IN getAll - {} users found", result2.size());
        return result2;
    }

    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    public ResponseEntity<String> delete(Long id, HttpServletRequest request) {
        if (!checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
    public ResponseEntity<?> getUser(Long id, HttpServletRequest request){
        User user = findById(id);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!checkAdmin(request)) {
            if (!Objects.equals(getUserId(request), id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
            }else{
                UserDto result = UserDto.fromUser(user);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        AdminUserDto result = AdminUserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private Long getUserId(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    public ResponseEntity<?> registryAdmin(User user, HttpServletRequest request){
        if (!checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Role roleUser = roleRepository.findByName("ROLE_USER");
        Role roleUser2 = roleRepository.findByName("ROLE_ADMIN");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        userRoles.add(roleUser2);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }
}
