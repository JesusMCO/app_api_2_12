package com.example.app_api_2_12;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_api_2_12.adapter.ProductAdapter;
import com.example.app_api_2_12.model.Product;
import com.example.app_api_2_12.model.ProductResponse;
import com.example.app_api_2_12.model.ProductsResponse;
import com.example.app_api_2_12.retrofit.ProductApi;
import com.example.app_api_2_12.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Step 4.2: Rebuilding MainActivity from scratch with the correct logic and URL.
public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private ProductApi productApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(this, productList, this);
        recyclerView.setAdapter(productAdapter);

        // Using the correct and definitive base URL for the vercel API.
        productApi = RetrofitClient.getClient("https://fakestores.vercel.app/api/").create(ProductApi.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fetch products every time the activity is resumed to ensure data is fresh.
        fetchProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchProducts() {
        Log.d(TAG, "Fetching products...");
        Call<ProductsResponse> call = productApi.getProducts();
        // Log the actual URL to prove it is correct
        Log.d(TAG, "Request URL: " + call.request().url().toString());
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Request failed with code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                ProductsResponse apiResponse = response.body();
                if (apiResponse != null && apiResponse.isStatus()) {
                    productList.clear();
                    productList.addAll(apiResponse.getData());
                    productAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Products loaded successfully: " + productList.size() + " items.");
                } else {
                    String msg = (apiResponse != null) ? apiResponse.getMessage() : "Response body is null or status is false";
                    Toast.makeText(MainActivity.this, "Failed to get products: " + msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Network Error", t);
            }
        });
    }

    @Override
    public void onEditClick(Product product) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("PRODUCT_ID", product.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteProduct(product.getId()))
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteProduct(int productId) {
        productApi.deleteProduct(productId).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(MainActivity.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                    fetchProducts(); // Refresh the list
                } else {
                    Toast.makeText(MainActivity.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
