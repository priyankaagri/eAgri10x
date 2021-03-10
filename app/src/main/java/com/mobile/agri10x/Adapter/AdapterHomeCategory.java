package com.mobile.agri10x.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetCategoriesData;

import java.util.List;

public class AdapterHomeCategory extends RecyclerView.Adapter<AdapterHomeCategory.ViewHolder> {
    Context context;
    private List<GetCategoriesData> dataList;


    public AdapterHomeCategory(List<GetCategoriesData> catArraylist, Context context1) {
        this.dataList = catArraylist;
        this.context = context1;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_catogery, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_title.setText(dataList.get(position).getCategoryName());
        Log.d("kjjjhdfh",dataList.get(position).getCategoryName());

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           this.txt_title =itemView.findViewById(R.id.txt_title);

        }
    }
}
