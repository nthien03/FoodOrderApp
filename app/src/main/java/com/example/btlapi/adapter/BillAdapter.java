package com.example.btlapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlapi.R;
import com.example.btlapi.manager.BillManager;
import com.example.btlapi.model.DetailBill;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private ArrayList<DetailBill> detailBillList;

    public BillAdapter() {
        this.detailBillList = BillManager.getInstance().getDetailBills();
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
        // Định dạng số tiền
        //String formattedAmount = vnFormat.format(amount);

        DetailBill detailBill = detailBillList.get(position);
        if(detailBill == null){
            return;
        }
        holder.tvAmountBill.setText(String.valueOf(detailBill.getAmount()) + "x");
        holder.tvFoodNameBill.setText(detailBill.getFood().getNameFood());
        holder.tvMoneyBill.setText(vnFormat.format(detailBill.getFood().getSalePrice() * detailBill.getAmount()));

    }

    @Override
    public int getItemCount() {
        if (detailBillList != null) {
            return detailBillList.size();
        }
        return 0;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAmountBill, tvFoodNameBill, tvMoneyBill;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmountBill = (TextView) itemView.findViewById(R.id.tv_amount_bill);
            tvFoodNameBill = (TextView) itemView.findViewById(R.id.tv_food_name_bill);
            tvMoneyBill = (TextView) itemView.findViewById(R.id.tv_money_bill);

        }
    }
}
