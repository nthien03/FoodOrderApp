package com.example.btlapi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.btlapi.R;
import com.example.btlapi.adapter.ReviewAdapter;
import com.example.btlapi.manager.BillManager;
import com.example.btlapi.model.DetailBill;
import com.example.btlapi.model.Food;
import com.example.btlapi.model.Rate;
import com.example.btlapi.retrofit.FoodInterface;
import com.example.btlapi.retrofit.FoodUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewActivity extends AppCompatActivity {
    // Biến boolean để kiểm tra đã đánh giá hay chưa
    FoodInterface menuInterface;
    RecyclerView recyclerView;
    ReviewAdapter adapter;
    Button buttonReview, buttonSkip;

    ArrayList<Food> listFoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        BillManager billManager = BillManager.getInstance();

        recyclerView = findViewById(R.id.recyMenu);
        buttonReview = findViewById(R.id.buttonReview);
        buttonSkip = findViewById(R.id.buttonSkip);
        menuInterface = FoodUtils.getFoodService();
       /* menuInterface.getImagesProduct("12092024_01").enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kq = response.body();
                    adapter = new ReviewAdapter(ReviewActivity.this, kq);
                    recyclerView.setLayoutManager(new GridLayoutManager(ReviewActivity.this, 1));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

            }
        });*/
        for (DetailBill detailBill: billManager.getDetailBills()){
            Food f = detailBill.getFood();
            listFoods.add(f);
        }
        /*listFoods.add(new Food("F01","F01","Phở",35000, 35000,10,"Phở, gầu, nạm, gân","Quốc hồn quốc túy" ,true,4));
        listFoods.add(new Food("F02", "F02", "Cơm rang", 20000, 25000, 7,"Cơm rang, bò, dưa muối", "Nóng hổi, đầy đặn", true, 4));*/
        adapter = new ReviewAdapter(ReviewActivity.this, listFoods);
        recyclerView.setLayoutManager(new GridLayoutManager(ReviewActivity.this, 1));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        buttonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    // Lấy idFood từ mỗi Food ở vị trí thứ i
                    Food foodItem = adapter.getItem(i);
                    String idFood = foodItem.getIdFood();

                    // Tìm ViewHolder tương ứng với vị trí thứ i trong RecyclerView
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
                    if (viewHolder != null) {
                        CardView cardView = (CardView) viewHolder.itemView;
                        EditText editTextReview = cardView.findViewById(R.id.editTextReview);
                        RadioGroup radioGroup = cardView.findViewById(R.id.radio_group);
                        String comment = editTextReview.getText().toString().trim();
                        int valueRate = -1; // Giá trị mặc định
                        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        if (selectedRadioButtonId != -1) {
                            RadioButton selectedRadioButton = cardView.findViewById(selectedRadioButtonId);
                            valueRate = radioGroup.indexOfChild(selectedRadioButton) + 1;
                        }
                        if(valueRate == -1){
                            continue;
                        }
                        // Tạo đối tượng Rate từ dữ liệu lấy được
                        Rate rate = new Rate(idFood, comment, valueRate);

                        // Sử dụng Retrofit để gửi đối tượng Rate lên server để insert vào cơ sở dữ liệu
                        menuInterface.insertReview(rate).enqueue(new Callback<Rate>() {
                            @Override
                            public void onResponse(Call<Rate> call, Response<Rate> response) {
                                // Xử lý phản hồi từ server
                                if (response.isSuccessful()) {
                                    // Đánh giá đã được insert thành công
                                    Toast.makeText(ReviewActivity.this, "Đánh giá đã được gửi đi", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Xảy ra lỗi khi insert đánh giá
                                    Toast.makeText(ReviewActivity.this, "Đã xảy ra lỗi khi gửi đánh giá", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Rate> call, Throwable t) {
                                // Xử lý lỗi khi gửi yêu cầu insert đánh giá
                                Toast.makeText(ReviewActivity.this, "Đã xảy ra lỗi khi gửi yêu cầu đánh giá", Toast.LENGTH_SHORT).show();
                                Log.e("API", "Lỗi: " + t.getMessage());
                            }
                        });
                    }
                    billManager.clearBill();
                    Intent intentT = new Intent(ReviewActivity.this, TableActivity.class);
                    startActivity(intentT);
                    finish();
                }
            }
        });
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billManager.clearBill();
                Intent intentT = new Intent(ReviewActivity.this, TableActivity.class);
                startActivity(intentT);
                finish();
            }
        });


    }
}