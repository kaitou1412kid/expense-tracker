package org.f1soft.springcrud.controller;

import org.f1soft.springcrud.dto.ExpenseDTO;
import org.f1soft.springcrud.dto.UserDTO;
import org.f1soft.springcrud.entity.Expense;
import org.f1soft.springcrud.entity.User;
import org.f1soft.springcrud.repository.UserRepository;
import org.f1soft.springcrud.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;
    private UserService userService;

    public UserController(UserService userService,  UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User register(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO.getUsername(),userDTO.getPassword());
    }

    @PostMapping("/login")
    public User login(@RequestBody UserDTO userDTO) {
        return userService.loginUser(userDTO.getUsername(), userDTO.getPassword());

    }

    @GetMapping("/getUser")
    public User getUser(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }
}
