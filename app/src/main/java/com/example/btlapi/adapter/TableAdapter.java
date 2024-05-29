package com.example.btlapi.adapter;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Visibility;

import com.example.btlapi.R;
import com.example.btlapi.model.NumberTable;
import com.example.btlapi.onClick.OnClickTable;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder>{
    private ArrayList<NumberTable> listTable;
    private OnClickTable onClickTable;

    public TableAdapter(ArrayList<NumberTable> listTable, OnClickTable onClickTable) {
        this.listTable = listTable;
        this.onClickTable = onClickTable;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        NumberTable table = listTable.get(position);
        if (table == null){
            return;
        }
        if (!table.getStatus()){
            holder.btnNumberTable.setBackgroundResource(R.drawable.custom_btn_table1);
        }
        else {
            holder.btnNumberTable.setBackgroundResource(R.drawable.custom_btn_table);
        }
        holder.btnNumberTable.setText(String.valueOf(table.getNumberTable()));
        holder.btnNumberTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTable.onClickNumberTable(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listTable != null){
            return listTable.size();
        }
        return 0;
    }

    public class TableViewHolder extends RecyclerView.ViewHolder{
        private AppCompatButton btnNumberTable;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            btnNumberTable = itemView.findViewById(R.id.btnNumberTable);
        }
    }
}
