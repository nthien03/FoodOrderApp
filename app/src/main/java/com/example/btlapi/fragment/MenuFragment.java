package com.example.btlapi.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.btlapi.activity.DetailFoodActivity;
import com.example.btlapi.activity.MainActivity;
import com.example.btlapi.R;
import com.example.btlapi.activity.TableActivity;
import com.example.btlapi.adapter.FoodAdapter;
import com.example.btlapi.manager.CartManager;
import com.example.btlapi.model.DetailShoppingCart;
import com.example.btlapi.model.Food;
import com.example.btlapi.onClick.OnClickMenu;
import com.example.btlapi.retrofit.FoodInterface;
import com.example.btlapi.retrofit.FoodUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuFragment extends Fragment {
    private RecyclerView rcvFoodMenu;
    private View view;

    private MainActivity mainActivity;
    private FoodAdapter foodAdapter;

    private FoodInterface foodInterface;

    private ImageView imgBack;
    private SearchView searchView;

    private CartManager cartManager;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        mainActivity = (MainActivity) getActivity();
        initIU();

        // Khởi tạo CartManager
        cartManager = CartManager.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcvFoodMenu.setLayoutManager(linearLayoutManager);

        foodInterface = FoodUtils.getFoodService();
        foodInterface.getAllFood().enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse
                    (Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                if (response.isSuccessful()) // đã thực thi và gọi kết quả thành công
                {
                    ArrayList<Food> kqListFood = response.body();

                    Map<String, Food> foodMap = new HashMap<>();
                    for (Food food : kqListFood) {
                        foodMap.put(food.getIdFood(), food);
                    }

                    foodAdapter = new FoodAdapter(kqListFood, new OnClickMenu() {
                        @Override
                        public void onClickItem(String idFood) {
                            Intent intentDetailFood = new Intent(getContext(), DetailFoodActivity.class);

                            intentDetailFood.putExtra("detail", (Serializable) foodMap.get(idFood));
                            startActivity(intentDetailFood);

                        }

                        @Override
                        public void onClickBtnAdd(String idFood) {
                            Food foodAdd = foodMap.get(idFood);
                            if (foodAdd != null) {
                                cartManager.addDetail(new DetailShoppingCart(null, foodAdd, 1, true));
                            }

                        }
                    });

                    rcvFoodMenu.setAdapter(foodAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {
                Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TableActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                foodAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                foodAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return view;
    }

    private void initIU() {
        rcvFoodMenu = view.findViewById(R.id.rcv_list_food_menu);
        imgBack = view.findViewById(R.id.btn_back);
        searchView = view.findViewById(R.id.search_view_menu);
    }
    /*public ArrayList<DetailShoppingCart> getDetailShoppingCartList() {
        return cartManager.getDetailList();
    }*/
}