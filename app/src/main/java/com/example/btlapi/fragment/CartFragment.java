package com.example.btlapi.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlapi.activity.MainActivity;
import com.example.btlapi.R;
import com.example.btlapi.activity.ReviewActivity;
import com.example.btlapi.adapter.BillAdapter;
import com.example.btlapi.adapter.FoodAdapterInCart;
import com.example.btlapi.createID.IDBill;
import com.example.btlapi.createID.IDShoppingCart;
import com.example.btlapi.manager.BillManager;
import com.example.btlapi.manager.CartManager;
import com.example.btlapi.manager.TableManager;
import com.example.btlapi.model.Bill;
import com.example.btlapi.model.DetailBill;
import com.example.btlapi.model.DetailShoppingCart;
import com.example.btlapi.onClick.onClickCart;
import com.example.btlapi.post.DetailBillPost;
import com.example.btlapi.post.DetailShoppingCartPost;
import com.example.btlapi.post.ShoppingCartPost;
import com.example.btlapi.retrofit.FoodInterface;
import com.example.btlapi.retrofit.FoodUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    private RecyclerView rcvFood;
    private View view;
    private MainActivity mainActivity;
    private FoodAdapterInCart foodAdapterInCart;
    private TextView tvTotalMoney, tvCartEmpty;
    private LinearLayout viewTotal;
    private FoodInterface foodInterface;
    private Button btnOrder;
    private Boolean checkTable = false;
    private FloatingActionButton btnPayMoney;
    ArrayList<DetailShoppingCart> dList;
    ArrayList<DetailBill> detailBills;
    private BillAdapter billAdapter;

    private List<IDShoppingCart> lsIdCart;
    private List<Integer> hashId;
    private int next;
    private String nextIdCart;
    private String idStart;
    private List<IDBill> lsIdBill;
    private int nextBill;
    private List<Integer> hashIdBill;
    private String nextIdBill;
    private String idBillStart;

    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        mainActivity = (MainActivity) getActivity();
        initWidgets();
        foodInterface = FoodUtils.getFoodService();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
        // Định dạng số tiền vnFormat.format(amount);

        TableManager tableManager = TableManager.getInstance();

        BillManager billManager = BillManager.getInstance();
        detailBills = billManager.getDetailBills();

        CartManager cartManager = CartManager.getInstance();
        dList = cartManager.getDetailList();

        if (detailBills.isEmpty()) {
            btnPayMoney.hide();
        } else {
            btnPayMoney.show();
        }

        btnPayMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBill(Gravity.CENTER);
            }
        });


        if (dList.isEmpty()) {
            tvCartEmpty.setVisibility(View.VISIBLE);
        } else {
            tvCartEmpty.setVisibility(View.GONE);
            //Toast.makeText(mainActivity, dList.size() + "", Toast.LENGTH_SHORT).show();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
            rcvFood.setLayoutManager(linearLayoutManager);

            tvTotalMoney.setText(vnFormat.format(cartManager.calculateTotalPrice()));
            foodAdapterInCart = new FoodAdapterInCart(new onClickCart() {
                @Override
                public void onClickChangeAmount(int pos) {
                    tvTotalMoney.setText(vnFormat.format(cartManager.calculateTotalPrice()));
                }

                @Override
                public void onClickBtnDel(int pos) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainActivity);
                    alertDialog.setTitle("Thông báo");
                    //alertDialog.setIcon(R.mipmap.ic_launcher);
                    alertDialog.setMessage("Bạn chắc chắn muốn bỏ món này?");
                    alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dList.remove(pos);
                            foodAdapterInCart.notifyDataSetChanged();

                            tvTotalMoney.setText(vnFormat.format(cartManager.calculateTotalPrice()));
                        }
                    });
                    alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            });
            rcvFood.setAdapter(foodAdapterInCart);

            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dList.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Vui lòng thêm món vào giỏ hàng để đặt món!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {

                        for (DetailShoppingCart detailSC : dList) {
                            billManager.addDetailBill(new DetailBill(null, detailSC.getAmount(), detailSC.getFood()));
                        }

                        // insert Cart
                        // Sinh mã tự động
                        lsIdCart = new ArrayList<IDShoppingCart>();
                        hashId = new ArrayList<>();
                        next = 0;

                        Calendar today = Calendar.getInstance();
                        int year = today.get(Calendar.YEAR);
                        int month = today.get(Calendar.MONTH) + 1;
                        int day = today.get(Calendar.DATE);
                        idStart = String.valueOf(day) + String.valueOf(month) + String.valueOf(year);
                        String daynow = year + "-" + month + "-" + day;
                        foodInterface.getIdCart(idStart)
                                .enqueue(new Callback<ArrayList<IDShoppingCart>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<IDShoppingCart>> call, Response<ArrayList<IDShoppingCart>> response) {
                                        if (response.isSuccessful()) {
                                            lsIdCart = response.body();
                                            if (!lsIdCart.isEmpty()) {
                                                //int cnt = 0;
                                                for (IDShoppingCart idCart : lsIdCart) {
                                                    String[] count = idCart.getIdShoppingCart().split("_");
                                                    hashId.add(Integer.parseInt(count[1]));
                                                }
                                                next = hashId.get(hashId.size() - 1) + 1;
                                                nextIdCart = idStart + "_" + String.valueOf(next);
                                            } else {
                                                nextIdCart = idStart + "_" + "1";
                                            }

                                            // insert shoppingCart
                                            ShoppingCartPost shoppingCart = new ShoppingCartPost(nextIdCart, Date.valueOf(daynow), tableManager.getNumberTable().getIdTable());
                                            foodInterface.insertShoppingCart(shoppingCart).enqueue(new Callback<ShoppingCartPost>() {
                                                @Override
                                                public void onResponse(Call<ShoppingCartPost> call, Response<ShoppingCartPost> response) {
                                                    if(response.isSuccessful()){
                                                        Toast.makeText(mainActivity, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                        // set lai status table
                                                        /*foodInterface.updateTable(TableManager.getInstance().getNumberTable().getIdTable(), "false").enqueue(new Callback<Void>() {
                                                            @Override
                                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                                if (response.isSuccessful()){
                                                                    Log.e("status", response.message());
                                                                }
                                                            }
                                                            @Override
                                                            public void onFailure(Call<Void> call, Throwable t) {
                                                                Log.e("status", response.message());
                                                            }
                                                        });*/
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ShoppingCartPost> call, Throwable t) {
                                                    Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));
                                                }
                                            });


                                            ArrayList<DetailShoppingCartPost> arrayListdetail = new ArrayList<>();
                                            for (DetailShoppingCart d : dList) {
                                                DetailShoppingCartPost detailShoppingCart = new DetailShoppingCartPost(nextIdCart, d.getAmount(), d.getFood().getIdFood(), false);
                                                arrayListdetail.add(detailShoppingCart);
                                            }
                                            //insert detailShoppingCart
                                            foodInterface.insertDetailShoppingCart(arrayListdetail).enqueue(new Callback<ArrayList<DetailShoppingCartPost>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<DetailShoppingCartPost>> call, Response<ArrayList<DetailShoppingCartPost>> response) {
                                                    if(response.isSuccessful()){
                                                        Toast.makeText(mainActivity, "Thành công", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ArrayList<DetailShoppingCartPost>> call, Throwable t) {
                                                    Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));
                                                }
                                            });
                                            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                                            builder.setTitle("Thông báo");
                                            builder.setMessage("Đặt món thành công!");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    cartManager.clearCart();
                                                    foodAdapterInCart.notifyDataSetChanged();
                                                    tvTotalMoney.setText("0đ");
                                                    btnPayMoney.show();
                                                }
                                            });
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<IDShoppingCart>> call, Throwable t) {
                                        Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));
                                    }
                                });



                    }


                }
            });

        }

        return view;
    }

    private void showDialogBill(int gravity) {
        final Dialog dialog = new Dialog(mainActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_bill);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        Button btnComplete = dialog.findViewById(R.id.btn_complete);
        ImageButton btnClose = dialog.findViewById(R.id.btn_Close);
        RecyclerView rcvBill = dialog.findViewById(R.id.rcv_list_food_bill);
        TextView tvTotalBill = dialog.findViewById(R.id.tv_total_money_bill);

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);

        BillManager billManager = BillManager.getInstance();
        detailBills = billManager.getDetailBills();
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!detailBills.isEmpty()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
            rcvBill.setLayoutManager(linearLayoutManager);
            billManager.mergeItemsWithSameID();
            tvTotalBill.setText(vnFormat.format(billManager.calculateTotalBill()));
            billAdapter = new BillAdapter();
            rcvBill.setAdapter(billAdapter);

            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sinh mã tự động
                    lsIdBill = new ArrayList<>();
                    hashIdBill = new ArrayList<>();
                    nextBill = 0;

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DATE);
                    idBillStart = String.valueOf(day) + String.valueOf(month) + String.valueOf(year);
                    String dateNow = year + "-" + month + "-" + day;
                    foodInterface.getIdBill(idBillStart).enqueue(new Callback<ArrayList<IDBill>>() {
                        @Override
                        public void onResponse(Call<ArrayList<IDBill>> call, Response<ArrayList<IDBill>> response) {
                            if (response.isSuccessful()) {
                                lsIdBill = response.body();
                                if (!lsIdBill.isEmpty()) {
                                    for (IDBill idBill : lsIdBill) {
                                        String[] bCount = idBill.getIdBill().split("_");
                                        hashIdBill.add(Integer.parseInt(bCount[1]));
                                    }
                                    nextBill = hashIdBill.get(hashIdBill.size() - 1) + 1;
                                    nextIdBill = idBillStart + "_" + String.valueOf(nextBill);
                                } else {
                                    nextIdBill = idBillStart + "_" + "1";
                                }

                                // insert Bill
                                Bill bill = new Bill(nextIdBill, Date.valueOf(dateNow), true, billManager.calculateTotalBill());
                                foodInterface.insertBill(bill).enqueue(new Callback<Bill>() {
                                    @Override
                                    public void onResponse(Call<Bill> call, Response<Bill> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(mainActivity, "TC", Toast.LENGTH_SHORT).show();
                                            /*foodInterface.updateTable(TableManager.getInstance().getNumberTable().getIdTable(),"true").enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    if (response.isSuccessful()){
                                                        Log.e("status",response.message().toString());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Log.e("status",t.getMessage().toString());
                                                }
                                            });*/
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Bill> call, Throwable t) {
                                        Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));
                                    }
                                });

                                //insert DetailBill
                                ArrayList<DetailBillPost> detailBillList = new ArrayList<>();
                                for(DetailBill dBill: detailBills){
                                    DetailBillPost detailBillPost = new DetailBillPost(nextIdBill, dBill.getAmount(), dBill.getFood().getIdFood());
                                    detailBillList.add(detailBillPost);
                                }
                                foodInterface.insertDetailBill(detailBillList).enqueue(new Callback<ArrayList<DetailBillPost>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<DetailBillPost>> call, Response<ArrayList<DetailBillPost>> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(mainActivity, "TCD", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<DetailBillPost>> call, Throwable t) {
                                        Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));
                                    }
                                });

                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<IDBill>> call, Throwable t) {
                            Log.e("Lỗi khi gọi API", Objects.requireNonNull(t.getMessage()));

                        }
                    });

                    // cập nhật status table

                    // Chuyển sang màn đánh giá
                    Intent intentReview = new Intent(getContext(), ReviewActivity.class);
                    startActivity(intentReview);
                }
            });
        }

        dialog.show();

    }


    private void initWidgets() {
        rcvFood = view.findViewById(R.id.rcv_list_food);
        tvTotalMoney = (TextView) view.findViewById(R.id.tv_totalMoney);
        tvCartEmpty = (TextView) view.findViewById(R.id.tv_cart_empty);
        btnOrder = view.findViewById(R.id.btn_order);
        btnPayMoney = view.findViewById(R.id.btn_pay_money);
        //viewTotal = (LinearLayout) viewTotal.findViewById(R.id.view_total);
    }

    private void insertCart() {

    }
}