package org.f1soft.springcrud.repository;

import org.f1soft.springcrud.dto.ExpenseDTO;
import org.f1soft.springcrud.entity.Category;
import org.f1soft.springcrud.entity.Expense;
import org.f1soft.springcrud.entity.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ExpenseRepository extends Repository<Expense,Long> {
    Expense save(Expense expense);
    Expense findByUserAndId(User user,Long id);
    List<Expense> findAllByUser(User user);
    void deleteExpenseByUserAndId(User user,Long id);
    List<Expense> findAllByUserAndCategory(User user,Category category);
}
