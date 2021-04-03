package com.mobile.agri10x.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetOrderListDatumCheckoutCartDataProduct;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailofProductOrderPurchase extends RecyclerView.Adapter<DetailofProductOrderPurchase.HolderView> {
    Context context;
String valueoftotalweight;
    List<GetOrderListDatumCheckoutCartDataProduct> ProductsInOrderlistData = new ArrayList<>();


    public DetailofProductOrderPurchase(List<GetOrderListDatumCheckoutCartDataProduct> productsInOrderlistData, Context context) {
        this.context = context;
        this.ProductsInOrderlistData = productsInOrderlistData;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_of_product_order, parent, false);
        HolderView viewHolder = new HolderView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {


        holder.txt_product_name.setText(ProductsInOrderlistData.get(position).getName());
        double number = Double.parseDouble(String.valueOf(ProductsInOrderlistData.get(position).getPrice()));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String currency = format.format(number);
        holder.txt_price_per_kg.setText("Price/KG : " + currency);


        holder.txt_quantity.setText("Qunatity : " + (ProductsInOrderlistData.get(position).getQuantity()));
        holder.txt_grade.setText("Grade : " + ProductsInOrderlistData.get(position).getGrade());
        holder.txt_packaging_size.setText("Packaging Size : 50 KG");
        String test = String.valueOf(ProductsInOrderlistData.get(position).getQuantity());
        holder.txt_total_weight.setText("Total Weight : "+Double.parseDouble(test) * ProductsInOrderlistData.get(position).getWeight());

            valueoftotalweight = String.valueOf(Double.parseDouble(test) * ProductsInOrderlistData.get(position).getWeight());
       // if(ProductsInOrderlistData.get(position).getPrice() != null){


            double number1 = ProductsInOrderlistData.get(position).getPrice() * Double.parseDouble(valueoftotalweight);
            NumberFormat format1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String currency1 = format1.format(number1);
            String pricepeoduct = String.format("%.2f", ProductsInOrderlistData.get(position).getPrice() * Double.parseDouble(valueoftotalweight));
            holder.txt_product_price.setText("Total: " + currency1);
      //  }

    }

    @Override
    public int getItemCount() {
        return ProductsInOrderlistData.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        TextView txt_product_name, txt_product_price, txt_grade, txt_price_per_kg, txt_quantity, txt_total_weight, txt_packaging_size;

        public HolderView(@NonNull View itemView) {
            super(itemView);

            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            txt_price_per_kg = itemView.findViewById(R.id.txt_price_per_kg);
            txt_grade = itemView.findViewById(R.id.txt_grade);
            txt_quantity = itemView.findViewById(R.id.txt_quantity);
            txt_packaging_size = itemView.findViewById(R.id.txt_packaging_size);
            txt_total_weight = itemView.findViewById(R.id.txt_total_weight);
        }
    }
}