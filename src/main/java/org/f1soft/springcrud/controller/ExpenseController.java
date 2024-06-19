package org.f1soft.springcrud.controller;

import org.f1soft.springcrud.dto.ExpenseDTO;
import org.f1soft.springcrud.entity.Expense;
import org.f1soft.springcrud.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {
    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/getAllExpense/{id}")
    public List<Expense> getAllExpense(@PathVariable Long id){
        return expenseService.findAll(id);
//        return "All student";
    }

    @PostMapping("/addExpense/{id}")
    public Expense addExpense(@PathVariable Long id, @RequestBody ExpenseDTO expenseDTO){
        return expenseService.addExpense(id,expenseDTO);
    }

    @PutMapping("/updateExpense/{id}")
    public String updateExpense(@PathVariable Long id,@RequestParam("expenseId") Long expenseId, @RequestBody ExpenseDTO expenseDTO){
        return expenseService.updateExpense(expenseDTO,expenseId, id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id, @RequestParam("expenseId") Long expenseId){
        return expenseService.deleteExpense(id, expenseId);
    }

    @GetMapping("/sortByCategory/{id}")
    public List<Expense> sortByCategory(@PathVariable Long id,@RequestParam("category") String category, @RequestParam(value = "fromDate", required = false)LocalDate fromDate, @RequestParam(value = "toDate", required = false) LocalDate toDate){
        return expenseService.findByCategory(id,category, fromDate, toDate);
    }

    @GetMapping("/getExpenseReport/{id}")
    public String getExpenseReport(@PathVariable Long id){
        return expenseService.getExpenseReport(id);
    }
}
