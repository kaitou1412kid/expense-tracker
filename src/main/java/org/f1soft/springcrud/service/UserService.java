package org.f1soft.springcrud.service;

import org.f1soft.springcrud.dto.ExpenseDTO;
import org.f1soft.springcrud.entity.Expense;
import org.f1soft.springcrud.entity.User;
import org.f1soft.springcrud.repository.ExpenseRepository;
import org.f1soft.springcrud.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final ExpenseRepository expenseRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ExpenseRepository expenseRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
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

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
