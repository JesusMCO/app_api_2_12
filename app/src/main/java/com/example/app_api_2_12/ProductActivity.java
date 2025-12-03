package com.example.app_api_2_12;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_api_2_12.model.Product;
import com.example.app_api_2_12.model.ProductResponse;
import com.example.app_api_2_12.retrofit.ProductApi;
import com.example.app_api_2_12.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Step 4.3: Rebuilding ProductActivity from scratch.
public class ProductActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextPrice, editTextDescription, editTextImage, editTextCategory;
    private Button buttonSave;
    private ProductApi productApi;
    private int productId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextImage = findViewById(R.id.edit_text_image_url);
        editTextCategory = findViewById(R.id.edit_text_category);
        buttonSave = findViewById(R.id.button_save);

        // Using the correct and definitive base URL for the vercel API.
        productApi = RetrofitClient.getClient("https://fakestores.vercel.app/api/").create(ProductApi.class);

        if (getIntent().hasExtra("PRODUCT_ID")) {
            productId = getIntent().getIntExtra("PRODUCT_ID", -1);
            buttonSave.setText("Update");
            fetchProductDetails();
        }

        buttonSave.setOnClickListener(v -> saveProduct());
    }

    private void fetchProductDetails() {
        productApi.getProduct(productId).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Product product = response.body().getData();
                    editTextTitle.setText(product.getTitle());
                    editTextPrice.setText(product.getPrice()); // Price is a String
                    editTextDescription.setText(product.getDescription());
                    editTextImage.setText(product.getImage());
                    editTextCategory.setText(product.getCategory());
                } else {
                    Toast.makeText(ProductActivity.this, "Failed to get product details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProduct() {
        String title = editTextTitle.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String imageUrl = editTextImage.getText().toString().trim();
        String category = editTextCategory.getText().toString().trim();

        if (title.isEmpty() || priceStr.isEmpty() || description.isEmpty() || imageUrl.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product();
        product.setTitle(title);
        product.setPrice(priceStr); // Price is a String
        product.setDescription(description);
        product.setImage(imageUrl);
        product.setCategory(category);

        if (productId == -1) {
            createProduct(product);
        } else {
            updateProduct(product);
        }
    }

    private void createProduct(Product product) {
        productApi.createProduct(product).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(ProductActivity.this, "Product created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ProductActivity.this, "Failed to create product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProduct(Product product) {
        productApi.updateProduct(productId, product).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(ProductActivity.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ProductActivity.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
