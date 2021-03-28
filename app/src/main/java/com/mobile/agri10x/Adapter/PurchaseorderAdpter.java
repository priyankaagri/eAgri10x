package com.mobile.agri10x.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.CheckoutListFromOrderList;
import com.mobile.agri10x.models.GetProductsInCartProductData;

import java.util.ArrayList;
import java.util.List;

public class PurchaseorderAdpter extends  RecyclerView.Adapter<PurchaseorderAdpter.ViewHolers> {

    Context context;
    List<CheckoutListFromOrderList> ProductsInPurchaseorderlist = new ArrayList<>();
    boolean check;
    Dialog purchasedetaildialog;
    ImageView cancle_btn;
    TextView txt_delivery_notes,txt_shipping_address,txt_billing_address,txt_product_name,
            txt_product_price,txt_grade,txt_price_per_kg,txt_quantity,txt_packaging_size,txt_total_weight,txt_total_amount;

    public PurchaseorderAdpter(List<CheckoutListFromOrderList> productsInOrderlist, Context context, boolean b) {
        this.context=context;
        this.ProductsInPurchaseorderlist=productsInOrderlist;
        this.check=b;
    }

    @NonNull
    @Override
    public ViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_order_list_layout, parent, false);
        ViewHolers viewHolder = new ViewHolers(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolers holder, int position) {

        holder.layout_for_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchasedetaildialog = new Dialog(context);
                purchasedetaildialog.setContentView(R.layout.layout_detailof_purchaseorder);
                purchasedetaildialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                purchasedetaildialog.setCancelable(true);
                purchasedetaildialog.setCanceledOnTouchOutside(true);
                purchasedetaildialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                cancle_btn = purchasedetaildialog.findViewById(R.id.cancle_btn);

                txt_product_name = purchasedetaildialog.findViewById(R.id.txt_product_name);
                txt_product_price = purchasedetaildialog.findViewById(R.id.txt_product_price);
                txt_grade = purchasedetaildialog.findViewById(R.id.txt_grade);
                txt_price_per_kg = purchasedetaildialog.findViewById(R.id.txt_price_per_kg);
                txt_quantity = purchasedetaildialog.findViewById(R.id.txt_quantity);
                txt_packaging_size = purchasedetaildialog.findViewById(R.id.txt_packaging_size);
                txt_total_weight = purchasedetaildialog.findViewById(R.id.txt_total_weight);
                txt_total_amount = purchasedetaildialog.findViewById(R.id.txt_total_amount);
                txt_billing_address = purchasedetaildialog.findViewById(R.id.txt_billing_address);
                txt_shipping_address = purchasedetaildialog.findViewById(R.id.txt_shipping_address);
                txt_delivery_notes = purchasedetaildialog.findViewById(R.id.txt_delivery_notes);

                cancle_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        purchasedetaildialog.dismiss();
                    }
                });

                purchasedetaildialog.show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return ProductsInPurchaseorderlist.size();
    }

    public class ViewHolers extends RecyclerView.ViewHolder {
        LinearLayout layout_for_purchase;
        public ViewHolers(@NonNull View itemView) {
            super(itemView);

            layout_for_purchase= itemView.findViewById(R.id.layout_for_purchase);


        }
    }
}
