package com.budget.application.service;

import com.budget.application.entity.Expense;
import com.budget.application.entity.Tag;
import com.budget.application.entity.ExpensesSearchCriteria;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExpenceServiceIntegrationImpTest {

    @Autowired
    private ExpenseService expenseService;
    private ExpenseRepository expenseRepository;
    private Expense testExpense;

    private TestUtils testUtils;


    @BeforeEach
    void setUp() throws Exception {
        testUtils = new TestUtils();
        testExpense = testUtils.generateTestExpense(1, LocalDateTime.now());
        for(int i=0; i<10; i++) {
            expenseService.createExpense(testUtils.generateTestExpense(1, LocalDateTime.now()));
        }
    }

    @Test
    void testCreateExpense() {
        Expense expense = expenseService.createExpense(testExpense);
        assertEquals(expense.getValue(), testExpense.getValue() );
    }

    @Test
    void testDeleteExpense() {
        Long expenseId = expenseService.getAllExpenses().get().get(0).getId();
        expenseService.deleteExpense(expenseId);
        Optional<Expense> foundById = expenseRepository.findById(expenseId);
        assertFalse(foundById.isPresent());
    }

    @Test
    void testGetAllExpenses() {
        List<Expense> allExpenses = expenseService.getAllExpenses().get();
        assertTrue(allExpenses.size()>=10);
    }

    @Test
    void testGetExpensesByCriteriaWithTagsSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        List<String> tagNames = expenseService.getAllExpenses().get().get(0).getTags().stream().map(Tag::getName).collect(Collectors.toList());
        expensesSearchCriteria.setTagNames(tagNames);
        Optional<List<Expense>> expensesRetreivedByCriteria = expenseService.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertTrue(expensesRetreivedByCriteria.isPresent());
        assertTrue(expensesRetreivedByCriteria.get().size()>0);
    }

    @Test
    void testGetExpensesByCriteriaWithFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        LocalDateTime fromDate = expenseService.getAllExpenses().get().get(0).getCreationDate();
        fromDate = fromDate.minusSeconds(1);
        expensesSearchCriteria.setFromDate(Timestamp.valueOf(fromDate));
        Optional<List<Expense>> expensesRetreivedByCriteria = expenseService.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertTrue(expensesRetreivedByCriteria.isPresent());
        assertTrue(expensesRetreivedByCriteria.get().size()>0);
    }

    @Test
    void testGetExpensesByCriteriaWithToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        LocalDateTime toDate = expenseService.getAllExpenses().get().get(0).getCreationDate();
        toDate = toDate.plusSeconds(5);
        expensesSearchCriteria.setToDate(Timestamp.valueOf(toDate));
        Optional<List<Expense>> expensesRetreivedByCriteria = expenseService.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertTrue(expensesRetreivedByCriteria.isPresent());
        assertTrue(expensesRetreivedByCriteria.get().size()>0);
    }

    @Test
    void testGetExpensesByCriteriaWithBothDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        LocalDateTime creationDate = expenseService.getAllExpenses().get().get(0).getCreationDate();
        LocalDateTime fromDate = creationDate.minusSeconds(5);
        LocalDateTime toDate = creationDate.plusSeconds(5);
        expensesSearchCriteria.setFromDate(Timestamp.valueOf(fromDate));
        expensesSearchCriteria.setToDate(Timestamp.valueOf(toDate));
        Optional<List<Expense>> expensesRetreivedByCriteria = expenseService.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertTrue(expensesRetreivedByCriteria.isPresent());
        assertTrue(expensesRetreivedByCriteria.get().size()>0);
    }

    @Test
    void testGetExpensesByCriteriaWithBothDateAndTagsSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        Expense retreivedExpense = expenseService.getAllExpenses().get().get(0);
        LocalDateTime creationDate = retreivedExpense.getCreationDate();
        LocalDateTime fromDate = creationDate.minusSeconds(5);
        LocalDateTime toDate = creationDate.plusSeconds(5);
        expensesSearchCriteria.setFromDate(Timestamp.valueOf(fromDate));
        expensesSearchCriteria.setToDate(Timestamp.valueOf(toDate));
        List<String> tagNames = retreivedExpense.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        expensesSearchCriteria.setTagNames(tagNames);
        Optional<List<Expense>> expensesRetreivedByCriteria = expenseService.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertTrue(expensesRetreivedByCriteria.isPresent());
        assertTrue(expensesRetreivedByCriteria.get().size()>0);
    }

}