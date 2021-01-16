package com.webtutsplus.ecommerceapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.webtutsplus.ecommerceapp.Activity.Category.AddCategoryActivity;
import com.webtutsplus.ecommerceapp.Activity.Category.ListCategoriesActivity;
import com.webtutsplus.ecommerceapp.Activity.Category.UpdateCategoryActivity;
import com.webtutsplus.ecommerceapp.Activity.Home.HomeActivity;
import com.webtutsplus.ecommerceapp.Activity.Product.AddProductActivity;
import com.webtutsplus.ecommerceapp.Activity.Product.ListProductsActivity;
import com.webtutsplus.ecommerceapp.Activity.Product.ProductPurchaseActivity;
import com.webtutsplus.ecommerceapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnToViewProducts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListProductsActivity.class));
            }
        });

        findViewById(R.id.btnToAddProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddProductActivity.class));
            }
        });


        findViewById(R.id.btnToAddCategories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddCategoryActivity.class));
            }
        });

        findViewById(R.id.btnToViewCategories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListCategoriesActivity.class));
            }
        });

    }
}