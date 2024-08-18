package com.budget.application.service;

import com.budget.application.entity.Expense;
import com.budget.application.entity.ExpensesSearchCriteria;
import com.budget.application.entity.Tag;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.CommonTools;
import com.budget.application.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExpenseServiceImpTest {

    @InjectMocks
    private ExpenceServiceImp expenceServiceImp;
    @Mock
    private ExpenseRepository expenseRepository;

    private TestUtils testUtils;
    @Mock
    private CommonTools tools;

    private Expense testExpense;
    List<Expense> generatedExpenses;

    private Timestamp fromDate;
    private Timestamp toDate;

    @BeforeEach
    void setUp() throws Exception {
        testUtils = new TestUtils();
        generatedExpenses = testUtils.generateGivenAmounOfTestExpenseObjects(1, 1, Timestamp.valueOf("2018-11-12 01:00:00.123456789"));
        fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
        toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");
        testExpense = testUtils.generateGivenAmounOfTestExpenseObjects(1, 1, Timestamp.valueOf("2018-11-10 01:02:03.123456789")).getFirst();
        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(testExpense);
        Mockito.when(expenseRepository.findAll()).thenReturn(generatedExpenses);
    }

    @Test
    void getExpensesBySearchCriteriaWithTagsSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        List<String> tagNames = generatedExpenses.getFirst().getTags().stream().map(Tag::getName).toList();
        expensesSearchCriteria.setTagNames(tagNames);
        Optional<List<Expense>> foundExpenses = expenceServiceImp.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertFalse(foundExpenses.get().isEmpty());
    }

    @Test
    void getExpensesBySearchCriteriaWithFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);
        Optional<List<Expense>> foundExpenses = expenceServiceImp.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertFalse(foundExpenses.get().isEmpty());
    }

    @Test
    void getExpensesBySearchCriteriaWithToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(toDate);
        Optional<List<Expense>> foundExpenses = expenceServiceImp.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertFalse(foundExpenses.get().isEmpty());
    }

    @Test
    void getExpensesBySearchCriteriaWithBothDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);
        expensesSearchCriteria.setFromDate(toDate);
        Optional<List<Expense>> foundExpenses = expenceServiceImp.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertFalse(foundExpenses.get().isEmpty());
    }

    @Test
    void getExpensesBySearchCriteriaWithBothDateAndTagsSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);
        expensesSearchCriteria.setFromDate(toDate);
        List<String> tagNames = generatedExpenses.getFirst().getTags().stream().map(Tag::getName).toList();
        expensesSearchCriteria.setTagNames(tagNames);
        Optional<List<Expense>> foundExpenses = expenceServiceImp.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertFalse(foundExpenses.get().isEmpty());
    }

    @Test
    void createExpense() {
        Expense createdExpense = null;
        createdExpense = expenceServiceImp.createExpense(testExpense);
        assertNotNull(createdExpense);
        assertEquals(createdExpense.getValue(), testExpense.getValue());
    }

    @Test
    void getAllExpenses() {
        Optional<List<Expense>> allExpenses = expenceServiceImp.getAllExpenses();
        assertEquals(allExpenses.get().size(), generatedExpenses.size());
    }

    @Test
    void deleteExpense() {
        Expense expense = expenseRepository.findAll().getFirst();
        expenceServiceImp.deleteExpense(expense.getId());
        Optional<Expense> foundIdAfterDelete = expenseRepository.findById(expense.getId());
        assertFalse(foundIdAfterDelete.isPresent());
    }
}