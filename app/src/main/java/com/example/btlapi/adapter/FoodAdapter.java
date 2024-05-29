package com.example.btlapi.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btlapi.R;
import com.example.btlapi.manager.CartManager;
import com.example.btlapi.model.Food;
import com.example.btlapi.onClick.OnClickMenu;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> implements Filterable {
    private List<Food> listFood;
    private List<Food> listFoodOld;
    private OnClickMenu onClickMenu;
    private String urlAPI = "http://192.168.1.100:3333/api/images/";

    public FoodAdapter(List<Food> listFood, OnClickMenu onClickMenu) {
        this.listFood = listFood;
        this.onClickMenu = onClickMenu;
        this.listFoodOld = listFood;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        // Tạo Locale cho tiền tệ của Việt Nam
        Locale localeVN = new Locale("vi", "VN");

        // Tạo đối tượng NumberFormat với Locale
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);

        // Định dạng số tiền
        //String formattedAmount = vnFormat.format(amount);

        CartManager cartManager = CartManager.getInstance();
        Food mfood = listFood.get(position);
        if (mfood == null) {
            return;
        }
        holder.txtFoodName.setText(mfood.getNameFood());
        holder.txtRating.setText(String.valueOf(mfood.getRating()));
        holder.txtUsage.setText(String.valueOf(mfood.getUsage()));
        if (mfood.getCost() == mfood.getSalePrice()) {
            holder.txtCost.setVisibility(View.GONE);
        }else {
            holder.txtCost.setVisibility(View.VISIBLE);
            holder.txtCost.setText(vnFormat.format(mfood.getCost()));
            holder.txtCost.setPaintFlags(holder.txtCost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.txtSalePrice.setText(vnFormat.format(mfood.getSalePrice()));
        if (mfood.getImages() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(urlAPI + listFood.get(position).getImages())
                    .into(holder.imageFood);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMenu.onClickItem(mfood.getIdFood());
            }
        });
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), mfood.getIdFood()+"", Toast.LENGTH_SHORT).show();
                if (!cartManager.foodExists(mfood.getIdFood())) {
                    onClickMenu.onClickBtnAdd(mfood.getIdFood());
                }else {
                    cartManager.increaseAmountForFood(mfood.getIdFood());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listFood != null) {
            return listFood.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch =constraint.toString();
                if(strSearch.isEmpty()){
                    listFood = listFoodOld;
                }else {
                    List<Food> list = new ArrayList<>();
                    for (Food food: listFoodOld){
                        if(food.getNameFood().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(food);
                        }
                    }

                    listFood = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFood;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFood = (List<Food>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFoodName, txtRating, txtUsage, txtSalePrice, txtCost;
        private ImageView imageFood;
        private ImageButton btnAddToCart;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.text_food_name_menu);
            txtRating = itemView.findViewById(R.id.text_rating);
            txtUsage = itemView.findViewById(R.id.text_usage);
            txtSalePrice = itemView.findViewById(R.id.text_sale_price);
            txtCost = itemView.findViewById(R.id.text_cost);
            imageFood = itemView.findViewById(R.id.img_food_menu);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
