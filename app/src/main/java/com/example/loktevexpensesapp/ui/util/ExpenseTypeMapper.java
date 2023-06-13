package com.example.loktevexpensesapp.ui.util;

import com.example.loktevexpensesapp.domain.ExpenseType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExpenseTypeMapper {
    private static LinkedHashMap<ExpenseType, String> expenseTypeValues;

    static {
        expenseTypeValues = new LinkedHashMap<>();
        expenseTypeValues.put(ExpenseType.HOME, "Дом");
        expenseTypeValues.put(ExpenseType.FOOD, "Еда");
        expenseTypeValues.put(ExpenseType.ACTIVITY, "Развлечения");
        expenseTypeValues.put(ExpenseType.TRANSPORT, "Транспорт");
    }

    public static String getValueFromExpenseType(ExpenseType expenseType){
        return expenseTypeValues.get(expenseType);
    }

    public static ExpenseType getExpenseTypeFromValue(String value){
        return expenseTypeValues.keySet().stream()
                .filter(x -> expenseTypeValues.get(x).equals(value))
                .findFirst().get();
    }
}
