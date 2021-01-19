package com.webtutsplus.ecommerce;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetails extends AppCompatActivity {

    TableLayout tl;
    TextView name, noProducts;
    String category_name;
    List<Category> categories;
    List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_details);
        tl = findViewById(R.id.cat_list);
        name = findViewById(R.id.name);
        noProducts = findViewById(R.id.no_products);
        category_name = getIntent().getStringExtra("Category_Name");
        name.setText(category_name);
        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Category>> data = api.getCategories();
        data.enqueue(new Callback<List<Category>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categories = response.body();
                tl.removeAllViews();
                Category current = categories.stream()
                        .filter(category -> category_name.equals(category.getCategoryName()))
                        .findAny()
                        .orElse(null);
                assert current != null;
                products = current.getProducts();
                if (products == null) {
                    noProducts.setVisibility(View.VISIBLE);
                }
                else {
                    if (products.size() == 0) {
                        noProducts.setVisibility(View.VISIBLE);
                    }
                    else {
                        for (int i = 0; i < products.size(); i++) {
                            ItemDetailCardView cd = new ItemDetailCardView(CategoryDetails.this);
                            if (products.get(i).getImageURL() == null) {
                                cd.setupView(products.get(i).getName(), String.valueOf(products.get(i).getPrice()), products.get(i).getDescription());
                            } else {
                                cd.setupView(products.get(i).getImageURL(), products.get(i).getName(), String.valueOf(products.get(i).getPrice()), products.get(i).getDescription());
                            }
                            tl.addView(cd);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }
}
