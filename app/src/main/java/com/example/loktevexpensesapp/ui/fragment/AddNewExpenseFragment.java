package com.example.loktevexpensesapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.loktevexpensesapp.R;
import com.example.loktevexpensesapp.domain.Expense;
import com.example.loktevexpensesapp.ui.util.ArgumentManager;
import com.example.loktevexpensesapp.ui.util.ExpenseTypeMapper;
import com.example.loktevexpensesapp.ui.viewmodel.ExpensesViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddNewExpenseFragment extends Fragment {
    private ExpensesViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_expense, container, false);
        viewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);

        setUI(view);
        setUpdateOrCreateListener(view);
        setDeleteListener(view);

        Button toMenuBtn = view.findViewById(R.id.toMenuBtn);
        toMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_addNewExpenseFragment_to_homeFragment);
            }
        });

        return view;
    }

    private void setUI(View view){
        if (ArgumentManager.hasArgument()){
            EditText etExpense = view.findViewById(R.id.etExpense);
            EditText etExpenseDate = view.findViewById(R.id.etExpenseDate);
            RadioGroup rgExpenseCategory = view.findViewById(R.id.rgExpenseCategory);
            Button deleteButton = view.findViewById(R.id.deleteExpenseBtn);
            viewModel.getExpenseById(Long.parseLong(ArgumentManager.getArgument())).observe(getViewLifecycleOwner(), new Observer<Expense>(){
                @Override
                public void onChanged(Expense expense) {
                    etExpense.setText(expense.getSpent().toString());
                    etExpenseDate.setText(new SimpleDateFormat("dd.mm.yyyy").format(expense.getDate()));
                    int rbId = rgExpenseCategory.getChildAt(expense.getExpenseType().ordinal()).getId();
                    rgExpenseCategory.check(rbId);
                }
            });
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            Button deleteButton = view.findViewById(R.id.deleteExpenseBtn);
            deleteButton.setVisibility(View.GONE);
        }
    }

    private void setUpdateOrCreateListener(View view){
        Button saveExpenseBtn = view.findViewById(R.id.saveExpenseBtn);
        saveExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = (View)view.getParent();
                EditText etExpense = view.findViewById(R.id.etExpense);
                EditText etExpenseDate = view.findViewById(R.id.etExpenseDate);
                RadioGroup rgExpenseCategory = view.findViewById(R.id.rgExpenseCategory);
                RadioButton checkedButton = view.findViewById(rgExpenseCategory.getCheckedRadioButtonId());

                try{
                    String resultMessage = "";
                    Expense expense = extractDataFromUI(etExpense, etExpenseDate, checkedButton);
                    if (ArgumentManager.hasArgument()){
                        expense.setId(Long.parseLong(ArgumentManager.getArgument()));
                        ArgumentManager.reset();
                        viewModel.updateExpense(expense);
                        resultMessage = "Запись успешно обновлена!";
                    } else {
                        viewModel.addExpense(expense);
                        resultMessage = "Запись успешно создана!";
                    }
                    Toast.makeText(getContext(), resultMessage, Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_addNewExpenseFragment_to_expensesFragment);
                } catch (IllegalArgumentException e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDeleteListener(View view){
        Button deleteButton = view.findViewById(R.id.deleteExpenseBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Expense expense = new Expense();
                    expense.setId(Long.parseLong(ArgumentManager.getArgument()));
                    ArgumentManager.reset();
                    viewModel.deleteExpense(expense);
                    Toast.makeText(getContext(), "Запись успешно удалена!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_addNewExpenseFragment_to_expensesFragment);
                } catch (IllegalArgumentException e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Expense extractDataFromUI(EditText etExpense, EditText etExpenseDate, RadioButton checkedButton){
        Expense expense = new Expense();

        if (checkedButton == null){
            throw new IllegalArgumentException("Не выбрана категория расхода.");
        }
        try{
            String spent = etExpense.getText().toString();
            String expenseDate = etExpenseDate.getText().toString();

            if (spent.equals("")){
                throw new IllegalArgumentException("Не указана сумма расхода.");
            } else if (expenseDate.equals("")){
                throw new IllegalArgumentException("Не указана дата расхода");
            }
            expense.setSpent(Double.parseDouble(spent));
            expense.setDate(new SimpleDateFormat("dd.mm.yyyy").parse(expenseDate));
            expense.setExpenseType(ExpenseTypeMapper.getExpenseTypeFromValue(checkedButton.getText().toString()));
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Сумма должна быть числом.");
        } catch (ParseException e){
            throw new IllegalArgumentException("Неверно введена дата расхода.");
        }

        return expense;
    }
}