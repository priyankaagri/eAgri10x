package com.mobile.agri10x.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetHomeProductData;


import java.util.List;

public class Adapterfeatured extends RecyclerView.Adapter<Adapterfeatured.ViewHolders> {
    Context context;
    private List<GetHomeProductData> dataList;

    public Adapterfeatured(List<GetHomeProductData> featuredproductlist, Context context) {
        this.dataList=featuredproductlist;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fetaured_catogery, parent, false);
        ViewHolders viewHolder = new ViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        holder.txt_product_name.setText(dataList.get(position).getCommodityName());
        holder.product_price.setText("Rs"+dataList.get(position).getPricePerLot());


    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView txt_product_name,product_price;
        ImageView product_img;
        Button addcart;


        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            this.txt_product_name =itemView.findViewById(R.id.txt_product_name);
            this.product_price =itemView.findViewById(R.id.product_price);
            this.product_img =itemView.findViewById(R.id.product_img);
            this.addcart =itemView.findViewById(R.id.addcart);
        }
    }
}
