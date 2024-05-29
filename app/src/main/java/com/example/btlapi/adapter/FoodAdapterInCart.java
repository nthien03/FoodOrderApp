package com.example.btlapi.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btlapi.R;
import com.example.btlapi.manager.CartManager;
import com.example.btlapi.model.DetailShoppingCart;
import com.example.btlapi.model.Food;
import com.example.btlapi.onClick.onClickCart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodAdapterInCart extends RecyclerView.Adapter<FoodAdapterInCart.CartViewHolder> {
    private ArrayList<DetailShoppingCart> listDetail;
    private onClickCart onClick;
    int amount = 0;
    private String urlAPI = "http://192.168.1.100:3333/api/images/";

    public FoodAdapterInCart(onClickCart onClick) {
        this.listDetail = CartManager.getInstance().getDetailList();
        this.onClick = onClick;
    }



    /*public void setData(List<Food> list) {
        this.foodList = list;
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
        // Định dạng số tiền
        //String formattedAmount = vnFormat.format(amount);

        DetailShoppingCart detailCart = listDetail.get(position);
        if (detailCart == null) {
            return;
        }
        holder.tvFoodName.setText(detailCart.getFood().getNameFood());
        holder.tvAmount.setText(String.valueOf(detailCart.getAmount()));
        holder.tvMoney.setText(vnFormat.format(detailCart.getFood().getSalePrice()));

        if (detailCart.getFood().getImages() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(urlAPI + detailCart.getFood().getImages())
                    .into(holder.imgFood);
        }
        holder.imgReduce.setOnClickListener(v -> {
            amount = Integer.parseInt((String) holder.tvAmount.getText());
            if (amount > 1) {
                amount -= 1;
            }
            holder.tvAmount.setText(String.valueOf(amount));
            //holder.tvMoney.setText(vnFormat.format(detailCart.getFood().getSalePrice() * amount));
            CartManager.getInstance().updateDetailAmount(holder.getAdapterPosition(), amount);
            onClick.onClickChangeAmount(holder.getAdapterPosition());
        });
        holder.imgIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Integer.parseInt((String) holder.tvAmount.getText());
                amount += 1;
                holder.tvAmount.setText(String.valueOf(amount));
                //holder.tvMoney.setText(vnFormat.format(detailCart.getFood().getSalePrice() * amount));
                CartManager.getInstance().updateDetailAmount(holder.getAdapterPosition(), amount);
                onClick.onClickChangeAmount(holder.getAdapterPosition());
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickBtnDel(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listDetail != null) {
            return listDetail.size();
        }
        return 0;
    }

    public double getTotalMoney(int pos) {
        double total = 0;
        total = listDetail.get(pos).getFood().getSalePrice() * amount;
        return total;
    }

    public int getAmount(int pos) {
        return amount;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFood;
        private ImageView imgReduce;
        private ImageView imgIncrease;
        private ImageButton btnDelete;
        private TextView tvFoodName, tvMoney, tvAmount;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            imgReduce = itemView.findViewById(R.id.img_reduce);
            imgIncrease = itemView.findViewById(R.id.img_increase);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvFoodName = itemView.findViewById(R.id.tv_foodName);
            tvMoney = itemView.findViewById(R.id.tv_money);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }
    }
}
