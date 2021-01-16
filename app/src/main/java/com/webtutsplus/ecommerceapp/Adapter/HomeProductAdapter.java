package com.webtutsplus.ecommerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webtutsplus.ecommerceapp.Model.Product;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Utility.OnItemClickListener;

import java.util.List;

public class HomeProductAdapter  extends RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder>{

    private Context context;
    private List<Product> products;
    private OnItemClickListener clickListener;

    public HomeProductAdapter(Context context, List<Product> products, OnItemClickListener clickListener) {
        this.context = context;
        this.products = products;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listitem_home_product_item, parent, false);
        return new ProductViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = products.get(position);
        String url = p.getImageURL();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivProduct);

        holder.tvName.setText(p.getName());
        holder.tvDescription.setText(p.getDescription());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivProduct;
        TextView tvId, tvName, tvPrice, tvDescription;
        OnItemClickListener onItemClickListener;

        public ProductViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            ivProduct = (ImageView) itemView.findViewById(R.id.ivProduct);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(products.get(getAdapterPosition()));
        }
    }

}
