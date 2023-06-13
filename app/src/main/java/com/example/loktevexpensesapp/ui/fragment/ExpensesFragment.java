package com.example.loktevexpensesapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.loktevexpensesapp.R;
import com.example.loktevexpensesapp.databinding.FragmentExpensesBinding;
import com.example.loktevexpensesapp.domain.Expense;
import com.example.loktevexpensesapp.ui.adapter.ExpenseAdapter;
import com.example.loktevexpensesapp.ui.util.ArgumentManager;
import com.example.loktevexpensesapp.ui.viewmodel.ExpensesViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ExpensesFragment extends Fragment {
    private FragmentExpensesBinding binding;
    private ExpensesViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        viewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        viewModel.getExpenses().observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable final List<Expense> expenses) {
                // your code here
                ExpenseAdapter adapter = new ExpenseAdapter(getContext(), expenses);
                adapter.setOnClickListener(new ExpenseAdapter.OnClickListener() {
                    @Override
                    public void onClick(int position, Expense model) {
                        ArgumentManager.setArgument(String.valueOf(adapter.getItemId(position)));
                        Navigation.findNavController(view).navigate(R.id.action_expensesFragment_to_addNewExpenseFragment);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

        Button addNewExpenseBtn = view.findViewById(R.id.addNewExpenseBtn);
        addNewExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_expensesFragment_to_addNewExpenseFragment);
            }
        });

        Button toMenuBtn = view.findViewById(R.id.toMenuBtn);
        toMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_expensesFragment_to_homeFragment);
            }
        });

        return view;
    }
}