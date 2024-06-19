package org.f1soft.springcrud.service;

import jakarta.transaction.Transactional;
import org.f1soft.springcrud.dto.ExpenseDTO;
import org.f1soft.springcrud.entity.Category;
import org.f1soft.springcrud.entity.Expense;
import org.f1soft.springcrud.entity.User;
import org.f1soft.springcrud.repository.ExpenseRepository;
import org.f1soft.springcrud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional // why transactional??
public class ExpenseService {
    private final UserRepository userRepository;
    private ExpenseRepository expenseRepository;
    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public List<Expense> findAll(Long userId){
        User user = userRepository.findById(userId);
        return expenseRepository.findAllByUser(user);
    }

//    public Expense getExpenseByID(Long id){
//        return expenseRepository.findById(id);
//    }

    public Expense addExpense(Long userId, ExpenseDTO expenseDTO){
        User user = userRepository.findById(userId);
        if(user != null){
            Expense expense = new Expense();
            expense.setUser(user);
            expense.setCategory(expenseDTO.getCategory());
            expense.setCategory(expenseDTO.getCategory());
            expense.setDescription(expenseDTO.getDescription());
            expense.setAmountSpent(expenseDTO.getAmountSpent());
            expense.setDateOfTheExpense(expenseDTO.getDateOfTheExpense());

            return expenseRepository.save(expense);

        }else{
            throw  new RuntimeException("User not found");
        }
//        return expenseRepository.save(expense);
    }

    public String updateExpense(ExpenseDTO expense, Long id, Long userId){
        User user = userRepository.findById(userId);

        Expense oldExpense = expenseRepository.findByUserAndId(user,id);
        oldExpense.setCategory(expense.getCategory());
        oldExpense.setAmountSpent(expense.getAmountSpent());
        oldExpense.setDescription(expense.getDescription());
        oldExpense.setDateOfTheExpense(expense.getDateOfTheExpense());
        expenseRepository.save(oldExpense);
        return "Expense of id :" +id + " updated";

    }

    // used transactional to delete
    public String deleteExpense(Long userId,Long id){
        User user = userRepository.findById(userId);
        expenseRepository.deleteExpenseByUserAndId(user,id);
        return "expense deleted";
    }

    public List<Expense> findByCategory(Long id,String category, LocalDate fromDate, LocalDate toDate){
        Category categoryEnum = Category.valueOf(category.toUpperCase());
        User user = userRepository.findById(id);
        List<Expense> expenses = expenseRepository.findAllByUserAndCategory(user,categoryEnum);
        if(fromDate != null && toDate==null){
            return expenses.stream()
                    .filter(expense -> expense.getDateOfTheExpense().isAfter(fromDate))
                    .collect(Collectors.toList());
        }else if (fromDate == null && toDate != null){
            return expenses.stream()
                    .filter(expense -> expense.getDateOfTheExpense().isAfter(toDate.minusMonths(1)) && expense.getDateOfTheExpense().isBefore(toDate))
                    .collect(Collectors.toList());
        } else if (fromDate != null && toDate != null) {
            return expenses.stream()
                    .filter(expense -> expense.getDateOfTheExpense().isAfter(fromDate) && expense.getDateOfTheExpense().isBefore(toDate))
                    .collect(Collectors.toList());
        }
        return expenses.stream()
                .filter(expense -> (expense.getDateOfTheExpense().isAfter(LocalDate.now().minusMonths(1))))
                .collect(Collectors.toList());
    }

    public String getExpenseReport(Long id){
//        List<Expense> expenses = expenseRepository.findAllBy();
//        int totalExpenses = expenses.stream().reduce(0, (subtotal, expense) -> subtotal + expense.getAmountSpent(), Integer::sum);
//        List<Expense> foodExpense = expenseRepository.findAllByCategory(Category.valueOf("FOOD"));
//        int foodExpenses = foodExpense.stream().reduce(0, (subtotal, expense) -> subtotal + expense.getAmountSpent(), Integer::sum);
//        List<Expense> entertainmentExpense = expenseRepository.findAllByCategory(Category.valueOf("ENTERTAINMENT"));
//        int entertainmentExpenses = entertainmentExpense.stream().reduce(0, (subtotal, expense) -> subtotal + expense.getAmountSpent(), Integer::sum);
//        List<Expense> utilityExpense = expenseRepository.findAllByCategory(Category.valueOf("UTILITY"));
//        int utilityExpenses = utilityExpense.stream().reduce(0, (subtotal, expense) -> subtotal + expense.getAmountSpent(), Integer::sum);
//        List<Expense> transportationExpense = expenseRepository.findAllByCategory(Category.valueOf("TRANSPORTATION"));
//        int transportationExpenses = transportationExpense.stream().reduce(0, (subtotal, expense) -> subtotal + expense.getAmountSpent(), Integer::sum);
//
//        if(foodExpenses >= entertainmentExpenses && foodExpenses >= transportationExpenses && foodExpenses >= utilityExpenses){
//            return "Total expense is "+totalExpenses+" and top category is FOOD with expenses "+foodExpenses;
//        } else if (entertainmentExpenses >= foodExpenses && entertainmentExpenses >= transportationExpenses && entertainmentExpenses >= utilityExpenses) {
//            return "Total expense is "+totalExpenses+" and top category is ENTERTAINMENT with expenses " + entertainmentExpenses;
//        }else if (transportationExpenses >= foodExpenses && transportationExpenses >= entertainmentExpenses && transportationExpenses >= utilityExpenses) {
//            return "Total expense is "+totalExpenses+" and top category is TRANSPORTATION with expenses "+transportationExpenses;
//        }else {
//            return "Total expense is "+totalExpenses+" and top category is UTILITY with expenses "+utilityExpenses;
//        }
        User user = userRepository.findById(id);
        List<Expense> expenses = expenseRepository.findAllByUser(user);
        int totalExpenses = expenses.stream().mapToInt(expense -> expense.getAmountSpent()).sum();

        // Calculate expenses by category
        Map<Category, Integer> categoryExpenses = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingInt(Expense::getAmountSpent)
                ));

        // Find the top category
        Map.Entry<Category, Integer> topCategory = categoryExpenses.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalArgumentException("No expenses found"));

        return "Total expense is " + totalExpenses + " and top category is " + topCategory.getKey() +
                " with expenses " + topCategory.getValue();
    }

}
