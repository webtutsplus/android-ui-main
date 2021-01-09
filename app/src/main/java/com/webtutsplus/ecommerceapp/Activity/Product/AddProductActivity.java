package com.webtutsplus.ecommerceapp.Activity.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import com.webtutsplus.ecommerceapp.Network.API;
import com.webtutsplus.ecommerceapp.Model.Category;
import com.webtutsplus.ecommerceapp.Model.Product;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Network.RetrofitClient;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    private EditText etId, etName, etImageURL, etPrice, etDescription;
    private Spinner spinner;
    private List<Category> categories;

    private static final int PICK_IMAGE_REQUEST = 9544;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        etId = findViewById(R.id.etId1);
        etName = findViewById(R.id.etName1);
        etImageURL = findViewById(R.id.etImageURL1);
        etPrice = findViewById(R.id.etPrice1);
        etDescription = findViewById(R.id.etDescription1);
        spinner = (Spinner) findViewById(R.id.etCategoryId1);

        //Api Call to fetch all the categories
        API api = RetrofitClient.getInstance().getAPI();
        Call<List<Category>> call = api.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddProductActivity.this, response.code() + "", Toast.LENGTH_LONG).show();
                    return;
                }

                categories = response.body();
                Log.e("Fetched Category",categories.get(0).getCategoryName());
                // Create an ArrayAdapter using the List of categories and a default spinner layout
                ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(AddProductActivity.this,
                        android.R.layout.simple_spinner_item, categories);

                // Specify the layout to use when the list of choices appears
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(AddProductActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });


        findViewById(R.id.checkUpload1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    etImageURL.setText("Click to upload");
                    etImageURL.setClickable(true);
                    etImageURL.setInputType(InputType.TYPE_NULL);
                    etImageURL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pick();
                        }
                    });
                }
                else {
                    etImageURL.setInputType(InputType.TYPE_CLASS_TEXT);
                    etImageURL.getText().clear();
                    etImageURL.setClickable(false);
                }
            }
        });


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
        int categoryId = ((Category)spinner.getSelectedItem()).getId();


        API api = RetrofitClient.getInstance().getAPI();
        Call<ResponseBody> call = api.addProduct(new Product(id, name, imageURL, price, description, categoryId));

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

    public void pick() {
        verifyStoragePermissions(AddProductActivity.this);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Open Gallery"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] imageProjection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, imageProjection, null, null, null);
                if(cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageProjection[0]);
                    String part_image = cursor.getString(indexImage);
                    if (part_image != null) {
                        File imageFile = new File(part_image);
                        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
                        MultipartBody.Part partImage = MultipartBody.Part.createFormData("file", imageFile.getName(), reqBody);
                        API api = RetrofitClient.getInstance().getAPI();
                        Call<ResponseBody> upload = api.uploadImage(partImage);
                        upload.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(AddProductActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                    try {
                                        etImageURL.setText(response.body().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(AddProductActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}