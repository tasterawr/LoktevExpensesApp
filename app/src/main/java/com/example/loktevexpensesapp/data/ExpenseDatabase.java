package com.example.loktevexpensesapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.loktevexpensesapp.data.converters.DateConverter;
import com.example.loktevexpensesapp.data.dao.ExpenseDao;
import com.example.loktevexpensesapp.domain.Expense;

@Database(entities = {Expense.class}, version = 2)
@TypeConverters({DateConverter.class})
public abstract class ExpenseDatabase extends RoomDatabase {
    public abstract ExpenseDao expenseDao();

    private static final String DATABASE_NAME = "expenses_db";

    private static ExpenseDatabase instance;

    public static ExpenseDatabase buildDatabase(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,
                    ExpenseDatabase.class,
                    DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
