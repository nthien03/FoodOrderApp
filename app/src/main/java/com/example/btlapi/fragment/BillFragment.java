package com.example.btlapi.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.btlapi.R;
import com.example.btlapi.activity.MainActivity;
import com.example.btlapi.adapter.BillAdapter;
import com.example.btlapi.manager.BillManager;
import com.example.btlapi.model.DetailBill;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class BillFragment extends Fragment {
    private View view;
    private MainActivity mainActivity;

    private RecyclerView rcvFoodInBill;

    private BillAdapter billAdapter;
    private TextView tvTotalMoneyBill, tvBillEmpty;
    private Button btnPay;

    private ArrayList<DetailBill> detailBills;

    public BillFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bill, container, false);
        mainActivity = (MainActivity) getActivity();
        initWidgets();

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
        // Định dạng số tiền vnFormat.format(amount)

        BillManager billManager = BillManager.getInstance();
        detailBills = billManager.getDetailBills();

        if(detailBills.isEmpty()){
            tvBillEmpty.setVisibility(View.VISIBLE);
        }else {
            tvBillEmpty.setVisibility(View.GONE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
            rcvFoodInBill.setLayoutManager(linearLayoutManager);

            tvTotalMoneyBill.setText(vnFormat.format(billManager.calculateTotalBill()));
            billAdapter = new BillAdapter();
            rcvFoodInBill.setAdapter(billAdapter);
        }

        return view;
    }

    private void initWidgets() {
        /*rcvFoodInBill = view.findViewById(R.id.rcv_list_food_bill);
        tvTotalMoneyBill = view.findViewById(R.id.tv_total_money_bill);
        tvBillEmpty = view.findViewById(R.id.tv_bill_empty);
        btnPay = view.findViewById(R.id.btn_pay);*/
    }
}