package com.example.app_api_2_12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_api_2_12.R;
import com.example.app_api_2_12.model.Product;

import java.util.List;

// Step 4.1: Rebuilding the adapter to correctly display data from the vercel API.
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnProductClickListener onProductClickListener;

    public interface OnProductClickListener {
        void onEditClick(Product product);
        void onDeleteClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener onProductClickListener) {
        this.context = context;
        this.productList = productList;
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productTitle.setText(product.getTitle());
        // The price is a String, so we can set it directly.
        holder.productPrice.setText("$" + product.getPrice());

        Glide.with(context)
                .load(product.getImage())
                .into(holder.productImage);

        holder.buttonEdit.setOnClickListener(v -> onProductClickListener.onEditClick(product));
        holder.buttonDelete.setOnClickListener(v -> onProductClickListener.onDeleteClick(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        Button buttonEdit;
        Button buttonDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }
}
