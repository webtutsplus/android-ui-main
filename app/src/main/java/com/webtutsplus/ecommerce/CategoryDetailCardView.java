package com.webtutsplus.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CategoryDetailCardView extends TableRow implements View.OnClickListener {

    TextView name, description;
    ImageView thumbnail;
    Context context;
    TableRow tr;

    public CategoryDetailCardView(Context context) {
        super(context);
        init(context);
    }

    public CategoryDetailCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.category_card_view2, this);
        thumbnail = findViewById(R.id.cat_thumb2);
        name = findViewById(R.id.cat_name2);
        description = findViewById(R.id.cat_description2);
        tr = findViewById(R.id.cat_card2);
        tr.setOnClickListener(this);
        this.context = context;
    }

    public void setupView(String imUrL, String itemName, String itemDesc) {
        Picasso.with(context)
                .load(imUrL)
                .transform(new ImageTransform(45, 0, true, true))
                .into(thumbnail);
        name.setText(itemName);
        description.setText(itemDesc);
    }

    public void setupView(String itemName, String itemDesc) {
        Picasso.with(context)
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/No_image_3x4.svg/1200px-No_image_3x4.svg.png")
                .transform(new ImageTransform(45, 0, true, true))
                .into(thumbnail);
        name.setText(itemName);
        description.setText(itemDesc);
    }

    @Override
    public void onClick(View view) {
        Intent getProducts = new Intent(context, CategoryDetails.class);
        getProducts.putExtra("Category_Name", this.name.getText().toString());
        context.startActivity(getProducts);
    }
}
