package com.webtutsplus.ecommerceapp.Activity.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.webtutsplus.ecommerceapp.Network.API;
import com.webtutsplus.ecommerceapp.Utility.OnItemClickListener;
import com.webtutsplus.ecommerceapp.Model.Product;
import com.webtutsplus.ecommerceapp.Adapter.ProductAdapter;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductsActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView revProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);

        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Product>> call = api.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ListProductsActivity.this, response.code() + "", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Product> products = response.body();
                revProducts = findViewById(R.id.revProducts);
                revProducts.setLayoutManager(new LinearLayoutManager(ListProductsActivity.this));
                revProducts.setAdapter(new ProductAdapter(ListProductsActivity.this, products, ListProductsActivity.this));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ListProductsActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(Product p) {
        Intent updateProduct = new Intent(getApplicationContext(), UpdateProductActivity.class);
        updateProduct.putExtra("id",p.getId());
        updateProduct.putExtra("categoryId",p.getCategoryId());
        updateProduct.putExtra("name",p.getName());
        updateProduct.putExtra("desc",p.getDescription());
        updateProduct.putExtra("imageUrl",p.getImageURL());
        updateProduct.putExtra("price",p.getPrice());
        startActivity(updateProduct);
    }
}