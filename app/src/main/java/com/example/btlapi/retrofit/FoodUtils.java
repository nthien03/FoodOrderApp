package com.example.btlapi.retrofit;

public class FoodUtils {
    public static final String BASE_URL = "http://192.168.1.100:3333/";
    public static FoodInterface getFoodService(){
        return RetrofitClient.getClient(BASE_URL).create(FoodInterface.class);
    }
}
