package com.budget.application.repository;

import com.budget.application.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findById(Long id);

    Expense save(Expense expense);

    List<Expense> findAll();
}
