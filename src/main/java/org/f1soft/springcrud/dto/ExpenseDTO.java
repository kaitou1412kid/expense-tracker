package org.f1soft.springcrud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.f1soft.springcrud.entity.Category;

import java.time.LocalDate;

public class ExpenseDTO {
    private Category category;
    private int amountSpent;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfTheExpense;

    public ExpenseDTO(Category category, int amountSpent, String description, LocalDate dateOfTheExpense) {
        this.category = category;
        this.amountSpent = amountSpent;
        this.description = description;
        this.dateOfTheExpense = dateOfTheExpense;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(int amountSpent) {
        this.amountSpent = amountSpent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfTheExpense() {
        return dateOfTheExpense;
    }

    public void setDateOfTheExpense(LocalDate dateOfTheExpense) {
        this.dateOfTheExpense = dateOfTheExpense;
    }
}
