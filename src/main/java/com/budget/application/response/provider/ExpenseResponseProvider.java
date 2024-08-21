package com.budget.application.response.provider;

import com.budget.application.entity.Expense;
import com.budget.application.entity.ExpensesSearchCriteria;
import com.budget.application.service.ExpenseService;
import com.budget.application.utils.CommonTools;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
public class ExpenseResponseProvider {

    private ExpenseService expenseService;
    private CommonTools commonTools;

    public ExpenseResponseEntity getAllExpenses() {
        ExpenseResponseEntity response = null;
        try {
            ExpensesList expenses = new ExpensesList(expenseService.getAllExpenses().get());
            response = new ExpenseResponseEntity(expenses, HttpStatus.OK);
        } catch (Exception e) {
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.NO_CONTENT);
        }
        return response;
    }

    public ExpenseResponseEntity createExpense(Expense expense) {
        ExpenseResponseEntity response = null;
        try {
            ExpensesList expenses = new ExpensesList(Arrays.asList(expenseService.createExpense(expense)));
            response = new ExpenseResponseEntity(expenses, HttpStatus.OK);
        } catch (Exception e) {
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return response;
    }

    public ExpenseResponseEntity deleteExpense(Long expenseId) {
        ExpenseResponseEntity response = null;
        try {
            expenseService.deleteExpense(expenseId);
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.OK);
        } catch (Exception e) {
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public ExpenseResponseEntity getExpensesBySearchCriteria(List<String> tagNames, String fromDate, String toDate) {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        ExpenseResponseEntity response = null;
        if (!tagNames.isEmpty()) {
            expensesSearchCriteria.setTagNames(tagNames);
        }
        if (!StringUtils.hasText(fromDate)) {
            Timestamp fDate = null;
            try {
                fDate = commonTools.getTimeStampFromISODate(fromDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            expensesSearchCriteria.setToDate(fDate);
        }
        if (!StringUtils.hasText(toDate)) {
            Timestamp tDate = null;
            try {
                tDate = commonTools.getTimeStampFromISODate(toDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            expensesSearchCriteria.setToDate(tDate);
        }
        try {
            ExpensesList expensesList = new ExpensesList(expenseService.getExpensesBySearchCriteria(expensesSearchCriteria).get());
            response = new ExpenseResponseEntity(expensesList, HttpStatus.OK);
        }catch (Exception e) {
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.NO_CONTENT);
        }
        return response;
    }
}
