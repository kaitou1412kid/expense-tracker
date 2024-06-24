package org.f1soft.springcrud.service;

import org.f1soft.springcrud.dto.ExpenseDTO;
import org.f1soft.springcrud.dto.LoginDTO;
import org.f1soft.springcrud.entity.Expense;
import org.f1soft.springcrud.entity.MyUserDetailService;
import org.f1soft.springcrud.entity.User;
import org.f1soft.springcrud.repository.ExpenseRepository;
import org.f1soft.springcrud.repository.UserRepository;
import org.f1soft.springcrud.webtoken.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final ExpenseRepository expenseRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MyUserDetailService myUserDetailService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ExpenseRepository expenseRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, MyUserDetailService myUserDetailService) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.myUserDetailService = myUserDetailService;
    }

    public User save(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        return userRepository.save(user);
    }

//    public Expense addExpenseToUser(Long userId, ExpenseDTO expenseDTO) {
//        User user = userRepository.findById(userId);
//        if(user != null){
//            Expense expense = new Expense();
//            expense.setUser(user);
//            expense.setCategory(expenseDTO.getCategory());
//            expense.setCategory(expenseDTO.getCategory());
//            expense.setDescription(expenseDTO.getDescription());
//            expense.setAmountSpent(expenseDTO.getAmountSpent());
//            expense.setDateOfTheExpense(expenseDTO.getDateOfTheExpense());
//
//            return expenseRepository.save(expense);
//
//        }else{
//            throw  new RuntimeException("User not found");
//        }
//
//    }

    public LoginDTO loginUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(myUserDetailService.loadUserByUsername(username));
            User user = userRepository.findByUsername(username);
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setToken(token);
            loginDTO.setUsername(user.getUsername());
            return loginDTO;
        }else{
            return null;
        }
//        if(passwordEncoder.matches(password, user.getPassword())) {
//            return user;
//        }else{
//            return null;
//        }
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
