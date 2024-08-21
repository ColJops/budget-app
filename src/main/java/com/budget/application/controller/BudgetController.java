package com.budget.application.controller;

import com.budget.application.entity.Expense;
import com.budget.application.entity.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BudgetController {

    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    public Object addExpense(@RequestBody Expense expense) {

        return expense;
    }

    @RequestMapping(value = "/expense/{expenseId}", method = RequestMethod.DELETE)
    public Object deleteExpense(@PathVariable("expenseId") Long expenseId) {

        return null;
    }

    @RequestMapping(value = "/expenses", method = RequestMethod.GET)
    public List<Expense> getAllExpenses() {
        return null;
    }

    @RequestMapping(value = "/expense/criteria", method = RequestMethod.GET)
    public List<Expense> getExpensesByCriteria(@RequestParam(value="tagNames") List<String> tagNames,
                                               @RequestParam(value = "fromDate") String fromDate,
                                               @RequestParam(value = "toDate") String toDate) {
        return null;
    }

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public List<Tag> getAllTags() {
        return null;
    }

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public Object addNewTag(@RequestBody String name) {
        return null;
    }

    @RequestMapping(value = "/tag/{}tagId", method = RequestMethod.DELETE)
    public Object deleteTag(@PathVariable("tagId") Long tagId) {
        return null;
    }
}
