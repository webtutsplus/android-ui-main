package com.webtutsplus.ecommerce;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ItemDetailCardView extends TableRow implements View.OnClickListener {

    TextView name, price, description;
    ImageView thumbnail;
    Context context;
    TableRow tr;

    public ItemDetailCardView(Context context) {
        super(context);
        init(context);
    }

    public ItemDetailCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_card_view2, this);
        thumbnail = findViewById(R.id.Item_thumb2);
        name = findViewById(R.id.Item_name2);
        price = findViewById(R.id.Item_price2);
        description = findViewById(R.id.Item_description2);
        tr = findViewById(R.id.item_card2);
        tr.setOnClickListener(this);
        this.context = context;
    }

    public void setupView(String imUrL, String itemName, String itemPrice, String itemDesc) {
        Picasso.with(context)
                .load(imUrL)
                .transform(new ImageTransform(45, 0, true, true))
                .into(thumbnail);
        name.setText(itemName);
        price.setText("$ " + itemPrice);
        description.setText(itemDesc);
    }

    public void setupView(String itemName, String itemPrice, String itemDesc) {
        Picasso.with(context)
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/No_image_3x4.svg/1200px-No_image_3x4.svg.png")
                .transform(new ImageTransform(45, 0, true, true))
                .into(thumbnail);
        name.setText(itemName);
        price.setText("$ " + itemPrice);
        description.setText(itemDesc);
    }

    @Override
    public void onClick(View view) {

    }
}
