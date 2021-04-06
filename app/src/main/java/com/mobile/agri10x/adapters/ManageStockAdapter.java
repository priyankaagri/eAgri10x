package com.mobile.agri10x.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetStockByIDDatum;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ManageStockAdapter extends RecyclerView.Adapter<ManageStockAdapter.MyViewHolder> {


    private List<GetStockByIDDatum> stocklist;
    Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_stock_adapter, parent, false);
        context = parent.getContext();
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txt_product_name.setText(stocklist.get(position).getCommodityName());
        holder.txt_varity_name.setText(stocklist.get(position).getVarietyName());
        double number1 = stocklist.get(position).getPricePerKg();
        NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String currency1 = format1.format(number1);
        holder.txt_product_price.setText(currency1);
        holder.txt_product_location.setText(stocklist.get(position).getCity() + " , " + stocklist.get(position).getState());
        holder.txt_total_quantity.setText("Total Qty(KG) : " + stocklist.get(position).getStockQuantity());
        if (stocklist.get(position).getIsVerified() == true) {
            holder.varified_value.setBackgroundResource(R.drawable.featured_bg);
            holder.verified.setText("Verified");
        } else {
            holder.varified_value.setBackgroundResource(R.drawable.featured_red);
            holder.verified.setText("Non Verified");
        }
        String strimg = stocklist.get(position).getCommodityID()+".png";

        String imageurl = "https://data.agri10x.com/images/products/"+strimg;

        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                    }
                })
                .build();
        picasso.load(imageurl)
                .into(holder.product_image);
    }

    public ManageStockAdapter(FragmentActivity activity, List<GetStockByIDDatum> stocklist) {
        this.stocklist = stocklist;
    }

    @Override
    public int getItemCount() {
        return stocklist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView txt_product_name, txt_product_price, txt_varity_name, txt_product_location, verified, txt_total_quantity;
        RelativeLayout varified_value;

        public MyViewHolder(View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            txt_varity_name = itemView.findViewById(R.id.txt_varity_name);
            txt_product_location = itemView.findViewById(R.id.txt_product_location);
            varified_value = itemView.findViewById(R.id.varified_value);
            verified = itemView.findViewById(R.id.verified);
            txt_total_quantity = itemView.findViewById(R.id.txt_total_quantity);


        }
    }
}