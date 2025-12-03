package com.example.app_api_2_12.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Step 3.2: Rebuilding the Retrofit client.
public class RetrofitClient {

    public static Retrofit getClient(String baseUrl) {
        // Use a lenient Gson parser to prevent crashes on minor API inconsistencies.
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Always build a new instance to prevent caching of old (and wrong) base URLs.
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
