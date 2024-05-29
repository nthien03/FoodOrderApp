package com.example.btlapi.retrofit;

import com.example.btlapi.createID.IDBill;
import com.example.btlapi.createID.IDShoppingCart;
import com.example.btlapi.model.Bill;
import com.example.btlapi.model.DetailShoppingCart;
import com.example.btlapi.model.Food;
import com.example.btlapi.model.NumberTable;
import com.example.btlapi.model.Rate;
import com.example.btlapi.model.ShoppingCart;
import com.example.btlapi.post.DetailBillPost;
import com.example.btlapi.post.DetailShoppingCartPost;
import com.example.btlapi.post.ShoppingCartPost;

import java.sql.Date;
import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FoodInterface {
    @GET("/food/getall")
    Call<ArrayList<Food>> getAllFood();
    @GET("/table/getall")
    Call<ArrayList<NumberTable>> getAllTable();

/*    @PUT("/table/updateStatus/{idTable}")
    Call<Void> updateTable(@Path("idTable") String idTable, @Body String status);*/

    @GET("/shoppingcart/getIdCart/{idcart}")
    Call<ArrayList<IDShoppingCart>> getIdCart(@Path("idcart") String idcart);

    @GET("/bill/getidbill/{idbill}")
    Call<ArrayList<IDBill>> getIdBill(@Path("idbill") String idbill);

    @GET("/rate/getbyfood/{idfood}")
    Call<ArrayList<Rate>> getRateByFood(@Path("idfood") String idfood);

    @POST("/shoppingcart/insert")
    Call<ShoppingCartPost> insertShoppingCart(@Body ShoppingCartPost sc);

    @POST("/detailshoppingcart/insert")
    Call<ArrayList<DetailShoppingCartPost>> insertDetailShoppingCart(@Body ArrayList<DetailShoppingCartPost> listdetail);

    @POST("/bill/insert")
    Call<Bill> insertBill(@Body Bill bill);

    @POST("/detailbill/insert")
    Call<ArrayList<DetailBillPost>> insertDetailBill(@Body ArrayList<DetailBillPost> detailBills);

    @POST("/review/insertReview")
    Call<Rate> insertReview(@Body Rate rate);

}
