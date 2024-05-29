package com.example.btlapi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.btlapi.R;
import com.example.btlapi.adapter.TableAdapter;
import com.example.btlapi.databinding.ActivityTableBinding;
import com.example.btlapi.manager.TableManager;
import com.example.btlapi.model.Food;
import com.example.btlapi.model.NumberTable;
import com.example.btlapi.onClick.OnClickTable;
import com.example.btlapi.retrofit.FoodInterface;
import com.example.btlapi.retrofit.FoodUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableActivity extends AppCompatActivity {

    private ActivityTableBinding binding;
    private TableAdapter tableApdapter;

    private FoodInterface foodInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TableManager tableManager = TableManager.getInstance();

        binding.recyTable.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        foodInterface = FoodUtils.getFoodService();
        foodInterface.getAllTable().enqueue(new Callback<ArrayList<NumberTable>>() {
            @Override
            public void onResponse(Call<ArrayList<NumberTable>> call, Response<ArrayList<NumberTable>> response) {
                if (response.isSuccessful()) {
                    ArrayList<NumberTable> tables = response.body();
                    for (NumberTable table: tables){
                        Log.e("status", table.getStatus()+"");
                    }
                    tableApdapter = new TableAdapter(tables, new OnClickTable() {
                        @Override
                        public void onClickNumberTable(int pos) {
                            tableManager.setNumberTable(tables.get(pos));
                            Intent intentTable = new Intent(TableActivity.this, MainActivity.class);
                            int numberTable = tables.get(pos).getNumberTable();
                            Bundle bundleTable = new Bundle();
                            bundleTable.putInt("numberTable", numberTable);
                            intentTable.putExtras(bundleTable);
                            startActivity(intentTable);
                        }
                    });
                    binding.recyTable.setAdapter(tableApdapter);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<NumberTable>> call, Throwable t) {
                Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));

            }
        });
    }
}