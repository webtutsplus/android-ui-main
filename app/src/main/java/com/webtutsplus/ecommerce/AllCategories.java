package com.webtutsplus.ecommerce;

import android.os.Build;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategories extends AppCompatActivity {

    TableLayout tl;
    TextView name;
    List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_details);
        tl = findViewById(R.id.cat_list);
        name = findViewById(R.id.name);
        name.setText("All Categories");
        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Category>> data = api.getCategories();
        data.enqueue(new Callback<List<Category>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categories = response.body();
                tl.removeAllViews();
                for (int i = 0; i < categories.size(); i++) {
                    CategoryDetailCardView cd = new CategoryDetailCardView(AllCategories.this);
                    if (categories.get(i).getImageUrl() == null) {
                        cd.setupView(categories.get(i).getCategoryName(), categories.get(i).getDescription());
                    } else {
                        cd.setupView(categories.get(i).getImageUrl(), categories.get(i).getCategoryName(), categories.get(i).getDescription());
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
