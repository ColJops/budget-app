package com.budget.application.service;

import com.budget.application.entity.Expense;
import com.budget.application.entity.ExpensesSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    public Optional<List<Expense>> getExpensesBySearchCriteria(ExpensesSearchCriteria criteria);
    public Expense createExpense(Expense expense);
    public Optional<List<Expense>> getAllExpenses();
    public void deleteExpense(Long id);
}
