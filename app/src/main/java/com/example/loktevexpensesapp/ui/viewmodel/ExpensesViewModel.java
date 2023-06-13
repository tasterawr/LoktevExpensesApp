package com.example.loktevexpensesapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.loktevexpensesapp.data.ExpenseDatabase;
import com.example.loktevexpensesapp.domain.Expense;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExpensesViewModel extends ViewModel {
    private ExpenseDatabase database;

    @Inject
    public ExpensesViewModel(ExpenseDatabase database){
        this.database = database;
    }

    public void addExpense(Expense expense){
        Thread insertThread = new Thread() {
            @Override
            public void run() {
                database.expenseDao().insertExpense(expense);
            }
        };
        insertThread.start();
    }

    public LiveData<Expense> getExpenseById(Long id){
        return database.expenseDao().getExpenseById(id);
    }

    public LiveData<List<Expense>> getExpenses(){
        return database.expenseDao().getAllExpenses();
    }

    public LiveData<List<Expense>> getExpensesByDate(Date date){
        return database.expenseDao().getExpensesByDate(date);
    }

    public void updateExpense(Expense expense){
        Thread updateThread = new Thread() {
            @Override
            public void run() {
                database.expenseDao().update(expense);
            }
        };
        updateThread.start();
    }

    public void deleteExpense(Expense expense){
        Thread deleteThread = new Thread(){
            @Override
            public void run() {
                database.expenseDao().delete(expense);
            }
        };
        deleteThread.start();
    }
}
