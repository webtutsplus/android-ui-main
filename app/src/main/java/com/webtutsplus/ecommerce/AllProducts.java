package com.webtutsplus.ecommerce;

import android.os.Build;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProducts extends AppCompatActivity {

    TableLayout tl;
    TextView name;
    List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_details);
        tl = findViewById(R.id.cat_list);
        name = findViewById(R.id.name);
        name.setText("All Products");
        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Category>> data = api.getCategories();
        data.enqueue(new Callback<List<Category>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categories = response.body();
                tl.removeAllViews();
                List<Product> products = new ArrayList<Product>();
                for (Category cat : categories) {
                    products = Stream.concat(products.stream(), cat.getProducts().stream()).collect(Collectors.toList());
                }
                for (int i = 0; i < products.size(); i++) {
                    ItemDetailCardView cd = new ItemDetailCardView(AllProducts.this);
                    if (products.get(i).getImageURL() == null) {
                        cd.setupView(products.get(i).getName(), String.valueOf(products.get(i).getPrice()), products.get(i).getDescription());
                    } else {
                        cd.setupView(products.get(i).getImageURL(), products.get(i).getName(), String.valueOf(products.get(i).getPrice()), products.get(i).getDescription());
                    }
                    tl.addView(cd);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }
}
