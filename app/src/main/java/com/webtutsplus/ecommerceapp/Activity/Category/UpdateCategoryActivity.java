package com.webtutsplus.ecommerceapp.Activity.Category;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    private static final int PICK_IMAGE_REQUEST = 9544;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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

        findViewById(R.id.checkUpload4).setOnClickListener(new View.OnClickListener() {
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

    public void pick() {
        verifyStoragePermissions(UpdateCategoryActivity.this);
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
                                    Toast.makeText(UpdateCategoryActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                    try {
                                        etImageURL.setText(response.body().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(UpdateCategoryActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
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