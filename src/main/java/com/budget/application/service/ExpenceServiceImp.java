package com.budget.application.service;

import com.budget.application.entity.Expense;
import com.budget.application.entity.ExpensesSearchCriteria;
import com.budget.application.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenceServiceImp implements ExpenseService{

    private ExpenseRepository expenseRepository;

    public Optional<List<Expense>> getExpensesBySearchCriteria(ExpensesSearchCriteria criteria) {
        List<Expense> filteredExpenses = new ArrayList<Expense>();
        List<Expense> allExpenses = expenseRepository.findAll();
        filteredExpenses = allExpenses;
        if(criteria.getTagNames()!=null) {
            filteredExpenses = filteredExpenses.stream()
                    .filter(expense -> expense.getTags().stream()
                            .anyMatch(tag -> criteria.getTagNames().contains(tag.getName())))
                    .collect(Collectors.toList());
        }

        if(criteria.getFromDate()!=null) {
            filteredExpenses = filteredExpenses.stream()
                    .filter(expense -> expense.getCreationDate().isAfter(criteria.getFromDate().toLocalDateTime()))
                    .collect(Collectors.toList());
        }

        if(criteria.getToDate()!=null) {
            filteredExpenses = filteredExpenses.stream()
                    .filter(expense -> expense.getCreationDate().isBefore(criteria.getToDate().toLocalDateTime()))
                    .collect(Collectors.toList());
        }


        return Optional.of(filteredExpenses);
    }


    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Optional<List<Expense>> getAllExpenses() {
        return Optional.of(expenseRepository.findAll());
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);

    }
}
