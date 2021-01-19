package com.webtutsplus.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class CategoryCardView extends LinearLayout implements View.OnClickListener {
    private ImageView thumbnail;
    private TextView name;
    private Context context;

    public CategoryCardView(Context context) {
        super(context);
        init(context);
    }

    public CategoryCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CategoryCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.category_card_view1, this);
        thumbnail = findViewById(R.id.cat_thumb1);
        name = findViewById(R.id.cat_name1);
        thumbnail.setOnClickListener(this);
        this.context = context;
    }

    public void setupView(String imUrL, String catName) {
        Picasso.with(context)
                .load(imUrL)
                .transform(new ImageTransform(45, 0, true, false))
                .into(thumbnail);
        name.setText(catName);
    }

    public void setupView(String catName) {
        thumbnail.setBackgroundResource(R.drawable.no_image);
        name.setText(catName);
    }

    @Override
    public void onClick(View view) {
        Intent getCategoryDetails = new Intent(context, CategoryDetails.class);
        getCategoryDetails.putExtra("Category_Name", this.name.getText().toString());
        context.startActivity(getCategoryDetails);
    }
}
