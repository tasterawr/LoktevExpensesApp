package com.example.loktevexpensesapp.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loktevexpensesapp.R;
import com.example.loktevexpensesapp.domain.Expense;
import com.example.loktevexpensesapp.domain.ExpenseType;
import com.example.loktevexpensesapp.ui.util.ArgumentManager;
import com.example.loktevexpensesapp.ui.viewmodel.ExpensesViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExpenseStatsFragment extends Fragment {
    private ExpensesViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_stats, container, false);
        viewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);

        setFullExpense(view);

        EditText etExpenseDate = view.findViewById(R.id.etExpenseDate);
        Button showStats = view.findViewById(R.id.showStats);
        showStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = (View)view.getParent().getParent();
                try {
                    if (etExpenseDate.getText().toString().equals("")){
                        setFullExpense(view);
                    } else {
                        Date date = new SimpleDateFormat("dd.mm.yyyy").parse(etExpenseDate.getText().toString());
                        setExpenseForDate(view, date);
                    }
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Неверно введена дата", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button toMenuBtn = view.findViewById(R.id.toMenuBtn);
        toMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_expenseStatsFragment_to_homeFragment);
            }
        });

        return view;
    }

    private void setFullExpense(View view){
        TextView subtitleText = view.findViewById(R.id.subtitleText);
        viewModel.getExpenses().observe(getViewLifecycleOwner(), new Observer<List<Expense>>(){
            @Override
            public void onChanged(List<Expense> expenses) {
                subtitleText.setText("Общие расходы");
                setUI(view, expenses);
            }
        });
    }

    private void setExpenseForDate(View view, Date date){
        TextView subtitleText = view.findViewById(R.id.subtitleText);
        viewModel.getExpensesByDate(date).observe(getViewLifecycleOwner(), new Observer<List<Expense>>(){
            @Override
            public void onChanged(List<Expense> expenses) {
                subtitleText.setText(String.format("Расходы на %s", new SimpleDateFormat("dd.mm.yyyy").format(date)));
                setUI(view, expenses);
            }
        });
    }

    private void setUI(View view, List<Expense> expenses){
        TextView tvFoodFullExpense = view.findViewById(R.id.tvFoodFullExpense);
        TextView tvHomeFullExpense = view.findViewById(R.id.tvHomeFullExpense);
        TextView tvActivityFullExpense = view.findViewById(R.id.tvActivityFullExpense);
        TextView tvTransportFullExpense = view.findViewById(R.id.tvTransportFullExpense);
        TextView tvFullExpense = view.findViewById(R.id.tvFullExpense);

        Map<ExpenseType, Double> grouped = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getExpenseType,
                        Collectors.summingDouble(Expense::getSpent)));

        if (grouped.containsKey(ExpenseType.FOOD)){
            tvFoodFullExpense.setText(String.format("%s руб.", grouped.get(ExpenseType.FOOD)));
        } else {
            tvFoodFullExpense.setText("0 руб.");
        }

        if (grouped.containsKey(ExpenseType.HOME)){
            tvHomeFullExpense.setText(String.format("%s руб.", grouped.get(ExpenseType.HOME)));
        } else {
            tvHomeFullExpense.setText("0 руб.");
        }

        if (grouped.containsKey(ExpenseType.ACTIVITY)){
            tvActivityFullExpense.setText(String.format("%s руб.", grouped.get(ExpenseType.ACTIVITY)));
        } else {
            tvActivityFullExpense.setText("0 руб.");
        }

        if (grouped.containsKey(ExpenseType.TRANSPORT)){
            tvTransportFullExpense.setText(String.format("%s руб.", grouped.get(ExpenseType.TRANSPORT)));
        } else {
            tvTransportFullExpense.setText("0 руб.");
        }

        if (expenses.size() == 0){
            tvFullExpense.setText("0 руб.");
        } else {
            tvFullExpense.setText(String.format("%s руб.", grouped.values().stream().mapToDouble(i -> i).sum()));
        }
    }
}