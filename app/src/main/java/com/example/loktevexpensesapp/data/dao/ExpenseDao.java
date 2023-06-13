package com.example.loktevexpensesapp.data.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.loktevexpensesapp.domain.Expense;

import java.util.Date;
import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert(onConflict = REPLACE)
    void insertExpense(Expense expense);

    @Query("SELECT * FROM Expenses WHERE id = :id")
    LiveData<Expense> getExpenseById(Long id);

    @Query("SELECT * FROM Expenses")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * FROM Expenses WHERE date = :date")
    LiveData<List<Expense>> getExpensesByDate(Date date);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);
}
