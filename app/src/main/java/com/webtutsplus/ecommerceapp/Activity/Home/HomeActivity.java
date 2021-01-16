package com.webtutsplus.ecommerceapp.Activity.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webtutsplus.ecommerceapp.Activity.Category.ListCategoriesActivity;
import com.webtutsplus.ecommerceapp.Activity.MainActivity;
import com.webtutsplus.ecommerceapp.Activity.Product.ProductPurchaseActivity;
import com.webtutsplus.ecommerceapp.Adapter.HomeCategoryAdapter;
import com.webtutsplus.ecommerceapp.Adapter.HomeProductAdapter;
import com.webtutsplus.ecommerceapp.Model.Category;
import com.webtutsplus.ecommerceapp.Model.Product;
import com.webtutsplus.ecommerceapp.Network.API;
import com.webtutsplus.ecommerceapp.Network.RetrofitClient;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Utility.OnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    Button startShoppingButton;
    TextView adminOptionsButton;

    private RecyclerView revCategories;
    private RecyclerView revProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startShoppingButton = findViewById(R.id.start_shopping_btn);
        revCategories = findViewById(R.id.revCategories);
        revProducts = findViewById(R.id.revProducts);
        adminOptionsButton = findViewById(R.id.btn_admin_options);

        startShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ListCategoriesActivity.class));
            }
        });
        adminOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });


        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Category>> getCategoryList = api.getCategory();
        getCategoryList.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, response.code() + "", Toast.LENGTH_LONG).show();
                    return;
                }

                //Getting the response from the api call
                List<Category> categories = response.body();

                revCategories.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL,false));
                revCategories.setAdapter(new HomeCategoryAdapter(HomeActivity.this, categories));



            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });



        Call<List<Product>> getProductList = api.getProducts();

        getProductList.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, response.code() + "", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Product> products = response.body();

                revProducts.setLayoutManager(new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false));
                revProducts.setAdapter(new HomeProductAdapter(HomeActivity.this, products, HomeActivity.this));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onItemClick(Product p) {
        Intent updateProduct = new Intent(getApplicationContext(), ProductPurchaseActivity.class);
        updateProduct.putExtra("id",p.getId());
        updateProduct.putExtra("categoryId",p.getCategoryId());
        updateProduct.putExtra("name",p.getName());
        updateProduct.putExtra("desc",p.getDescription());
        updateProduct.putExtra("imageUrl",p.getImageURL());
        updateProduct.putExtra("price",p.getPrice());
        startActivity(updateProduct);
    }
}