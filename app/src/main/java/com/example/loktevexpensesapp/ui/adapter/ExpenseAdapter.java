package com.example.loktevexpensesapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loktevexpensesapp.domain.Expense;
import com.example.loktevexpensesapp.ui.util.ExpenseTypeMapper;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private Context context;
    private List<Expense> expenses;
    private OnClickListener onClickListener;

    public ExpenseAdapter(Context context, List<Expense> expenses){
        this.context = context;
        this.expenses = expenses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView moneySpent;
        TextView categoryValue;
        TextView dateValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moneySpent = itemView.findViewById(com.example.loktevexpensesapp.R.id.moneySpent);
            categoryValue = itemView.findViewById(com.example.loktevexpensesapp.R.id.categoryvalue);
            dateValue = itemView.findViewById(com.example.loktevexpensesapp.R.id.dateValue);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(
                com.example.loktevexpensesapp.R.layout.fragment_expense_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenses.get(position);

        holder.moneySpent.setText(String.format("%s руб.", expense.getSpent().toString()));
        holder.categoryValue.setText(ExpenseTypeMapper.getValueFromExpenseType(expense.getExpenseType()));

        String formattedTime = new SimpleDateFormat("dd.mm.yyyy").format(expense.getDate());
        holder.dateValue.setText(formattedTime);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(position, expense);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    @Override
    public long getItemId(int position) {
        return expenses.get(position).getId();
    }

    public interface OnClickListener {
        void onClick(int position, Expense model);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}


