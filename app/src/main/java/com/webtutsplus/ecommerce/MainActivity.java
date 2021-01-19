package com.webtutsplus.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TableRow tr1, tr2;
    List<Category> categories = new ArrayList<>();
    List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tr1 = findViewById(R.id.category);
        tr2 = findViewById(R.id.item);

        findViewById(R.id.explore_products).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AllProducts.class));
            }
        });

        findViewById(R.id.explore_categories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AllCategories.class));
            }
        });

        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Category>> data = api.getCategories();
        data.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categories = response.body();
                products = categories.get(0).getProducts();
                tr1.removeAllViews();
                tr2.removeAllViews();
                createPopularCategories(tr1);
                createPopularProducts(tr2);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }



    private void createPopularCategories(TableRow tr) {
        CategoryCardView c1, c2, c3, c4, c5, c6;

        c1 = new CategoryCardView(MainActivity.this);
        c2 = new CategoryCardView(MainActivity.this);
        c3 = new CategoryCardView(MainActivity.this);
        c4 = new CategoryCardView(MainActivity.this);
        c5 = new CategoryCardView(MainActivity.this);
        c6 = new CategoryCardView(MainActivity.this);

        c1.setupView(categories.get(0).getImageUrl(), categories.get(0).getCategoryName());
        c2.setupView(categories.get(1).getImageUrl(), categories.get(1).getCategoryName());
        c3.setupView(categories.get(2).getImageUrl(), categories.get(2).getCategoryName());
        c4.setupView(categories.get(3).getImageUrl(), categories.get(3).getCategoryName());
        c5.setupView(categories.get(4).getImageUrl(), categories.get(4).getCategoryName());
        c6.setupView(categories.get(6).getImageUrl(), categories.get(6).getCategoryName());

        tr.addView(c1);
        tr.addView(c2);
        tr.addView(c3);
        tr.addView(c4);
        tr.addView(c5);
        tr.addView(c6);
    }

    private void createPopularProducts(TableRow tr) {
        ItemCardView i1, i2, i3, i4, i5, i6;

        i1 = new ItemCardView(MainActivity.this);
        i2 = new ItemCardView(MainActivity.this);
        i3 = new ItemCardView(MainActivity.this);
        i4 = new ItemCardView(MainActivity.this);
        i5 = new ItemCardView(MainActivity.this);
        i6 = new ItemCardView(MainActivity.this);

        i1.setupView(products.get(0).getImageURL());
        i2.setupView(products.get(1).getImageURL());
        i3.setupView(products.get(2).getImageURL());
        i4.setupView(products.get(3).getImageURL());
        i5.setupView(products.get(4).getImageURL());
        i6.setupView(products.get(5).getImageURL());

        tr.addView(i1);
        tr.addView(i2);
        tr.addView(i3);
        tr.addView(i4);
        tr.addView(i5);
        tr.addView(i6);
    }

}