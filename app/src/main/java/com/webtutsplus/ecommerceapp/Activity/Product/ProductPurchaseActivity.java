package com.webtutsplus.ecommerceapp.Activity.Product;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webtutsplus.ecommerceapp.Adapter.ProductFeaturesAdapter;
import com.webtutsplus.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductPurchaseActivity extends AppCompatActivity {

    private static final String TAG = "ProductPurchaseActivity";
    private RecyclerView revFeatures;

    ImageView productImage;
    TextView productName,titleBar,productPrice,productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_purchase);

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        titleBar = findViewById(R.id.title_page);
        productPrice = findViewById(R.id.product_original_price);
        productDescription = findViewById(R.id.description);

        String imageUrl = getIntent().getStringExtra("imageUrl");
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("desc");
        String price = getIntent().getStringExtra("price");

        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(productImage);
        if (description!=null)
            productDescription.setText(description);

        if (name!=null) {
            productName.setText(name);
            titleBar.setText(name);
        }
        if (price!=null)
            productPrice.setText(price);

        List<String> categories = getData();
        revFeatures = findViewById(R.id.rev_features);
        revFeatures.setLayoutManager(new LinearLayoutManager(ProductPurchaseActivity.this));
        revFeatures.setAdapter(new ProductFeaturesAdapter(ProductPurchaseActivity.this, categories));
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();

        list.add("Feature 1");
        list.add("Feature 2");
        list.add("Feature 3");
        list.add("Feature 4");
        list.add("Feature 6");
        list.add("Feature 7");
        return list;
    }
}