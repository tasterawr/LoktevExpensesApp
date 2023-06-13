package com.example.loktevexpensesapp.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Expenses")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    Long id;
    Double spent;
    Date date;
    ExpenseType expenseType;

    public Expense() {
    }

    public Expense(Double spent, Date date, ExpenseType expenseType) {
        this.spent = spent;
        this.date = date;
        this.expenseType = expenseType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSpent() {
        return spent;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }
}
