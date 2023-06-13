package com.example.loktevexpensesapp.di;

import android.app.Application;

import com.example.loktevexpensesapp.data.ExpenseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public ExpenseDatabase provideExpenseDatabase(Application app){
        return ExpenseDatabase.buildDatabase(app);
    }
}
