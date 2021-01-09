package com.webtutsplus.ecommerceapp.Activity.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.webtutsplus.ecommerceapp.Adapter.CategoryAdapter;
import com.webtutsplus.ecommerceapp.Model.Category;
import com.webtutsplus.ecommerceapp.Network.API;
import com.webtutsplus.ecommerceapp.Utility.OnCategoryItemClickListener;
import com.webtutsplus.ecommerceapp.Adapter.ProductAdapter;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoriesActivity extends AppCompatActivity implements OnCategoryItemClickListener {

    private RecyclerView revCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_categories);


        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Category>> call = api.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ListCategoriesActivity.this, response.code() + "", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Category> categories = response.body();
                revCategories = findViewById(R.id.revCategories);
                revCategories.setLayoutManager(new LinearLayoutManager(ListCategoriesActivity.this));
                revCategories.setAdapter(new CategoryAdapter(ListCategoriesActivity.this, categories, ListCategoriesActivity.this));



            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(ListCategoriesActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemClick(Category c) {
        Intent updateCategory = new Intent(getApplicationContext(), UpdateCategoryActivity.class);
        updateCategory.putExtra("id",c.getId());
        updateCategory.putExtra("name",c.getCategoryName());
        updateCategory.putExtra("desc",c.getDescription());
        updateCategory.putExtra("imageUrl",c.getImageUrl());
        startActivity(updateCategory);
    }
}