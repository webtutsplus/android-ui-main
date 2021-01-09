package com.webtutsplus.ecommerceapp.Network;

import com.webtutsplus.ecommerceapp.Model.Category;
import com.webtutsplus.ecommerceapp.Model.Product;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface API {

    @GET("product/")
    Call<List<Product>> getProducts();

    @GET("category/")
    Call<List<Category>> getCategory();

    @POST("product/add/")
    Call<ResponseBody> addProduct(@Body Product p);

    @POST("category/create/")
    Call<ResponseBody> addCategory(@Body Category c);

    @POST("product/update/{id}")
    Call<ResponseBody> updateProduct(@Path("id") long productId, @Body Product p);

    @POST("category/update/{id}")
    Call<ResponseBody> updateCategory(@Path("id") long categoryId, @Body Category c);

    @Multipart
    @POST("fileUpload/")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);
}
