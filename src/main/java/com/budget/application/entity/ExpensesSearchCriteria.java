package com.budget.application.entity;

import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.util.List;

@Getter
@Setter
public class ExpensesSearchCriteria {

    private Timestamp fromDate;
    private Timestamp toDate;
    private List<String> tagNames;
}
