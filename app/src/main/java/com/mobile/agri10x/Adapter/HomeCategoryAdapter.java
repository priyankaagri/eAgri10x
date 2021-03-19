package com.mobile.agri10x.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.Fragments.Product_Against_Categories_Fragment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetCategoriesData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    Context context;
    private List<GetCategoriesData> dataList;


    public HomeCategoryAdapter(List<GetCategoriesData> catArraylist, Context context1) {
        this.dataList = catArraylist;
        this.context = context1;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_title.setText(dataList.get(position).getCategoryName());
        Log.d("kjjjhdfh",dataList.get(position).getCategoryName());
        if(position==0){
            holder.img_category.setImageResource(R.drawable.grains_img);
        } else if(position==1){
            holder.img_category.setImageResource(R.drawable.pulses_img);
        }else if(position==2){
            holder.img_category.setImageResource(R.drawable.fruits_img);
        } else if(position==3){
            holder.img_category.setImageResource(R.drawable.vegetable_img);
        } else if(position==4){
            holder.img_category.setImageResource(R.drawable.spices_img);
        } else if(position==5){
            holder.img_category.setImageResource(R.drawable.spices_img);
        }
holder.lin_category.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Product_Against_Categories_Fragment fragment = new Product_Against_Categories_Fragment(); // replace your custom fragment class
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        bundle.putString("value",dataList.get(position).getId()); // use as per your need
        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
        fragmentTransaction.commit();
    }
});
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        ImageView img_category;
        LinearLayout lin_category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           this.txt_title =itemView.findViewById(R.id.txt_categoryname);
           this.img_category =itemView.findViewById(R.id.img_categories);
           this.lin_category =itemView.findViewById(R.id.lin_category);

        }
    }
}
