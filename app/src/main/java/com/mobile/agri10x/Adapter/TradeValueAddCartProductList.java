package com.mobile.agri10x.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetProductsInCartData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TradeValueAddCartProductList extends RecyclerView.Adapter<TradeValueAddCartProductList.ViewHolers> {
    Context context;
    List<GetProductsInCartData> ProductsInCartlist = new ArrayList<>();
    boolean check;

    public TradeValueAddCartProductList(List<GetProductsInCartData> productsInCartlist, Context context, boolean check) {
        this.context=context;
        this.ProductsInCartlist=productsInCartlist;
        this.check =check;
    }

    @NonNull
    @Override
    public ViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_value_addcart_product_list_adpter, parent, false);
        ViewHolers viewHolder = new ViewHolers(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolers holder, int position) {


        holder.product_price.setText("₹ "+ProductsInCartlist.get(position).getPrice());
        holder.product_name.setText(ProductsInCartlist.get(position).getName());
        holder.product_garde.setText(ProductsInCartlist.get(position).getGrade());
        holder.product_quantity.setText(String.valueOf(ProductsInCartlist.get(position).getQuantity()));
        holder.product_varity.setText(ProductsInCartlist.get(position).getVariety());
        holder.total_price.setText(String.valueOf(ProductsInCartlist.get(position).getPrice()));
        holder.product_total_weight.setText(String.valueOf(ProductsInCartlist.get(position).getWeight()));

        String productimg =  ProductsInCartlist.get(position).getCommodityID()+".png";
        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d("exception", String.valueOf(exception));
                    }
                })
                .build();
        picasso.load("https://data.agri10x.com/images/products/"+productimg)
                .fit()
                .into(holder.product_img);

    }

    @Override
    public int getItemCount() {
        return ProductsInCartlist.size();
    }

    public class ViewHolers extends RecyclerView.ViewHolder {
        TextView product_total_weight,product_quantity,total_price,product_garde,product_varity,product_name,product_price;
        ImageView product_img;
        CardView cardview;
        public ViewHolers(@NonNull View itemView) {
            super(itemView);

            this.product_total_weight =itemView.findViewById(R.id.product_total_weight);
            this.product_quantity =itemView.findViewById(R.id.product_quantity);
            this.total_price =itemView.findViewById(R.id.total_price);
            this.product_garde =itemView.findViewById(R.id.product_garde);
            this.product_varity =itemView.findViewById(R.id.product_varity);
            this.product_name =itemView.findViewById(R.id.product_name);
            this.product_price =itemView.findViewById(R.id.product_price);
            this.product_img =itemView.findViewById(R.id.product_img);
            this.cardview =itemView.findViewById(R.id.cardview);
        }
    }
}
