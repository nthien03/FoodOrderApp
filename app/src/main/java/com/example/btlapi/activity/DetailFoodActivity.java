package com.example.btlapi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.btlapi.R;
import com.example.btlapi.adapter.CommentAdapter;
import com.example.btlapi.databinding.ActivityDetailFoodBinding;
import com.example.btlapi.fragment.MenuFragment;
import com.example.btlapi.manager.CartManager;
import com.example.btlapi.model.DetailShoppingCart;
import com.example.btlapi.model.Food;
import com.example.btlapi.model.Rate;
import com.example.btlapi.retrofit.FoodInterface;
import com.example.btlapi.retrofit.FoodUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFoodActivity extends AppCompatActivity {

    private ActivityDetailFoodBinding detailFoodBinding;
    private String urlAPI = "http://192.168.1.100:3333/api/images/";
    private FoodInterface foodInterface;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailFoodBinding = ActivityDetailFoodBinding.inflate(getLayoutInflater());
        setContentView(detailFoodBinding.getRoot());

        CartManager cartManager = CartManager.getInstance();

        foodInterface = FoodUtils.getFoodService();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);

        Intent intentDetail = getIntent();
        Food foodDetail = (Food) intentDetail.getSerializableExtra("detail");
        if (foodDetail != null){
            if (foodDetail.getImages() != null) {
                Glide.with(DetailFoodActivity.this)
                        .load(urlAPI + foodDetail.getImages())
                        .into(detailFoodBinding.imgFoodDetail);
            }
            detailFoodBinding.tvNameFoodDetail.setText(foodDetail.getNameFood());
            if (foodDetail.getCost() == foodDetail.getSalePrice()) {
                detailFoodBinding.textCostDetail.setVisibility(View.GONE);
            }else {
                detailFoodBinding.textCostDetail.setVisibility(View.VISIBLE);
                detailFoodBinding.textCostDetail.setText(vnFormat.format(foodDetail.getCost()));
                detailFoodBinding.textCostDetail.setPaintFlags(detailFoodBinding.textCostDetail.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            detailFoodBinding.textSalePriceDetail.setText(vnFormat.format(foodDetail.getSalePrice()));
            detailFoodBinding.tvUsageDetail.setText(String.valueOf(foodDetail.getUsage()));
            detailFoodBinding.tvDetail.setText("Gồm: " + foodDetail.getDetailFood());
            detailFoodBinding.tvIntro.setText(foodDetail.getIntro());
        }

        detailFoodBinding.rcvReviewDetail.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(DetailFoodActivity.this, DividerItemDecoration.VERTICAL);
        detailFoodBinding.rcvReviewDetail.addItemDecoration(itemDecoration);
        String idfood = foodDetail.getIdFood();
        foodInterface.getRateByFood(idfood).enqueue(new Callback<ArrayList<Rate>>() {
            @Override
            public void onResponse(Call<ArrayList<Rate>> call, Response<ArrayList<Rate>> response) {
                if(response.isSuccessful()){
                    ArrayList<Rate> rates = response.body();
                    //Toast.makeText(DetailFoodActivity.this, rates.size() + "", Toast.LENGTH_SHORT).show();
                    commentAdapter = new CommentAdapter(rates);
                    detailFoodBinding.rcvReviewDetail.setAdapter(commentAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rate>> call, Throwable t) {
                Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));
            }
        });




        detailFoodBinding.btnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTable = new Intent(DetailFoodActivity.this, MainActivity.class);
                startActivity(intentTable);
            }
        });

        detailFoodBinding.btnAddToCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailFoodActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Đã thêm vào giỏ hàng");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                if (!cartManager.foodExists(foodDetail.getIdFood())) {
                    cartManager.addDetail(new DetailShoppingCart(null, foodDetail, 1, true));
                }else {
                    cartManager.increaseAmountForFood(foodDetail.getIdFood());
                }

            }
        });



    }
}