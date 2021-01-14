package com.webtutsplus.ecommerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webtutsplus.ecommerceapp.R;

import java.util.List;

public class ProductFeaturesAdapter  extends RecyclerView.Adapter<ProductFeaturesAdapter.CategoryViewHolder> {

    private Context context;
    private List<String> categories;

    public ProductFeaturesAdapter (Context context, List<String> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listitem_product_features, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String c = categories.get(position);
        holder.catName.setText(c);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView  catName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catName =itemView.findViewById(R.id.product_feature);
        }

    }
}
