package com.example.app_api_2_12.retrofit;

import com.example.app_api_2_12.model.Product;
import com.example.app_api_2_12.model.ProductResponse;
import com.example.app_api_2_12.model.ProductsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

// Step 3.1: Rebuilding the API interface to match the vercel API response structure.
public interface ProductApi {

    @GET("products")
    Call<ProductsResponse> getProducts();

    @GET("products/{id}")
    Call<ProductResponse> getProduct(@Path("id") int id);

    @POST("products")
    Call<ProductResponse> createProduct(@Body Product product);

    @PUT("products/{id}")
    Call<ProductResponse> updateProduct(@Path("id") int id, @Body Product product);

    @DELETE("products/{id}")
    Call<ProductResponse> deleteProduct(@Path("id") int id);
}
