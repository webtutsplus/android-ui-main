package com.webtutsplus.ecommerceapp.Activity.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.webtutsplus.ecommerceapp.Network.API;
import com.webtutsplus.ecommerceapp.Model.Category;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Network.RetrofitClient;

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

        etId.setText(String.valueOf(getIntent().getIntExtra("id",0)));
        etName.setText(getIntent().getStringExtra("name"));
        etImageURL.setText(getIntent().getStringExtra("imageUrl"));
        etDescription.setText(getIntent().getStringExtra("desc"));

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
//        newCategory.setId(null);
        newCategory.setImageUrl(imageURL);
        newCategory.setDescription(description);
//        newCategory.setProducts(null);


        API api = RetrofitClient.getInstance().getAPI();
        Call<ResponseBody> call = api.updateCategory(id, newCategory);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String test = response.body().string();
                    Toast.makeText(UpdateCategoryActivity.this, "Successfully Updated!", Toast.LENGTH_LONG).show();
                    //Toast.makeText(UpdateCategoryActivity.this, test, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(UpdateCategoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Update Category Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UpdateCategoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Update Throwable", t.getMessage());
            }
        });

        etId.getText().clear();
        etName.getText().clear();
        etImageURL.getText().clear();
        etDescription.getText().clear();
    }


}