package com.webtutsplus.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCategoryActivity extends AppCompatActivity {

    private EditText etId, etName, etImageURL, etDescription;
    private Category newCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);


        etId = findViewById(R.id.etId4);
        etName = findViewById(R.id.etName4);
        etImageURL = findViewById(R.id.etImageURL4);
        etDescription = findViewById(R.id.etDescription4);

        findViewById(R.id.btnUpdateCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategory();
            }
        });
    }

    private void updateCategory() {
        long id = Long.parseLong(etId.getText().toString().trim());
        String name = etName.getText().toString().trim();
        String imageURL = etImageURL.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        newCategory = new Category();
        newCategory.setCategoryName(name);
        newCategory.setId((int)id);
        newCategory.setImageUrl(imageURL);
        newCategory.setDescription(description);



        API api = RetrofitClient.getInstance().getAPI();
        Call<ResponseBody> call = api.updateCategory(id, newCategory);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String test = response.body().string();
                    Toast.makeText(UpdateCategoryActivity.this, "Successfully Added!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(UpdateCategoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Update Category Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UpdateCategoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Update Category Throwable", t.getMessage());
            }
        });

        etId.getText().clear();
        etName.getText().clear();
        etImageURL.getText().clear();
        etDescription.getText().clear();
    }
}