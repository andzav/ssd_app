package com.example.a1.myapplication.apihandlers;

import com.example.a1.myapplication.models.containerModel;
import com.example.a1.myapplication.models.loginModel;
import com.example.a1.myapplication.models.orderModel;
import com.example.a1.myapplication.models.updateContainerModel;
import com.example.a1.myapplication.models.updateOrderModel;
import com.example.a1.myapplication.models.userModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiCalls {

    @Headers("Content-Type: application/json")
    @POST("users")
    Call<userModel> loginUser(@Body loginModel user);

    @Headers("Content-Type: application/json")
    @PUT("orders")
    Call<ResponseBody> updateStatus(@Body updateOrderModel data); //SEND SID / TRACKID / STATUS

    @Headers("Content-Type: application/json")
    @GET("orders")
    Call<List<orderModel>> getOrderInfo(@Query("SID") String SID, @Query("trackID") int trackID); //SEND SID / TRACK ID

    @Headers("Content-Type: application/json")
    @GET("orders/containers")
    Call<List<containerModel>> getContainerInfo(@Query("SID") String SID, @Query("containerID") int containerID); //SEND SID / CONTAINER ID

    @Headers("Content-Type: application/json")
    @POST("orders/acceptContainer")
    Call<ResponseBody> acceptContainer(@Body updateContainerModel data); //SEND SID / CONTAINER ID

    @Headers("Content-Type: application/json")
    @POST("users/logout")
    Call<ResponseBody> logout(@Body userModel data);  //SEND SID
}