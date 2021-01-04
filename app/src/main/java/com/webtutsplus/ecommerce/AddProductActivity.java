package com.webtutsplus.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    private EditText etId, etName, etImageURL, etPrice, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etId = findViewById(R.id.etId1);
        etName = findViewById(R.id.etName1);
        etImageURL = findViewById(R.id.etImageURL1);
        etPrice = findViewById(R.id.etPrice1);
        etDescription = findViewById(R.id.etDescription1);

        findViewById(R.id.btnAddProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });
    }

    private void addProduct() {
        long id = Long.parseLong(etId.getText().toString().trim());
        String name = etName.getText().toString().trim();
        String imageURL = etImageURL.getText().toString().trim();
        double price = Double.parseDouble(etPrice.getText().toString().trim());
        String description = etDescription.getText().toString().trim();

        API api = RetrofitClient.getInstance().getAPI();
        Call<ResponseBody> call = api.addProduct(new Product(id, name, imageURL, price, description));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String test = response.body().string();
                    Toast.makeText(AddProductActivity.this, "Successfully Added!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        etId.getText().clear();
        etName.getText().clear();
        etImageURL.getText().clear();
        etPrice.getText().clear();
        etDescription.getText().clear();
    }
}