package com.budget.application.response.provider;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ExpenseResponseEntity extends ResponseEntity<ExpensesList> {

    public ExpenseResponseEntity(ExpensesList body, HttpStatusCode status) {
        super(body, status);
    }
}
