package com.webtutsplus.ecommerce;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

public class ItemCardView extends LinearLayout implements View.OnClickListener {
    private ImageView thumbnail;
    private Button add;
    private Context context;

    public ItemCardView(Context context) {
        super(context);
        init(context);
    }

    public ItemCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_card_view1, this);
        thumbnail = findViewById(R.id.item_thumb1);
        add = findViewById(R.id.add1);
        add.setOnClickListener(this);
        thumbnail.setOnClickListener(this);
        this.context = context;
    }

    public void setupView(String imUrL) {
        Picasso.with(context)
                .load(imUrL)
                .transform(new ImageTransform(45, 0, true, false))
                .into(thumbnail);
    }

    public void setupView() {
        thumbnail.setBackgroundResource(R.drawable.no_image);
    }

    @Override
    public void onClick(View view) {
    }
}
