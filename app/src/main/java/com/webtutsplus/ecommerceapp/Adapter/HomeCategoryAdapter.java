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
import com.webtutsplus.ecommerceapp.Model.Category;
import com.webtutsplus.ecommerceapp.R;
import com.webtutsplus.ecommerceapp.Utility.OnCategoryItemClickListener;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder>{

    private Context context;
    private List<Category> categories;

    public HomeCategoryAdapter (Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listitem_home_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category c = categories.get(position);
        String url = c.getImageUrl();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.catImage);
        holder.catName.setText(c.getCategoryName());
        holder.catDescription.setText(c.getDescription());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView catImage;
        TextView catId, catName, catDescription;
        OnCategoryItemClickListener onCategoryItemClickListener;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catImage = (ImageView) itemView.findViewById(R.id.catImage);
            catName = (TextView) itemView.findViewById(R.id.catName);
            catDescription = (TextView) itemView.findViewById(R.id.catDescription);
        }

        @Override
        public void onClick(View view) {
            onCategoryItemClickListener.onItemClick(categories.get(getAdapterPosition()));
        }
    }

}
