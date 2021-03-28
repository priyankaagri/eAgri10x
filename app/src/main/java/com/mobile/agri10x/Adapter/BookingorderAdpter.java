package com.mobile.agri10x.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;

public class BookingorderAdpter extends RecyclerView.Adapter<BookingorderAdpter.ViewHolers> {

    Dialog bookingdetaildialog;
    ImageView cancle_btn;
    Context context;
    Button btn_purchase;
    TextView txt_delivery_notes,txt_shipping_address,txt_billing_address,txt_product_name,
            txt_product_price,txt_grade,txt_price_per_kg,txt_quantity,txt_packaging_size,txt_total_weight,txt_total_amount,txt_paid_amount,txt_pending_amount;
    @NonNull
    @Override
    public ViewHolers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_order_list_layout, parent, false);
       ViewHolers viewHolder = new ViewHolers(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolers holder, int position) {

        holder.layout_for_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookingdetaildialog = new Dialog(context);
                bookingdetaildialog.setContentView(R.layout.layout_detailof_bookingorder);
                bookingdetaildialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                bookingdetaildialog.setCancelable(true);
                bookingdetaildialog.setCanceledOnTouchOutside(true);
                bookingdetaildialog.getWindow().getAttributes().windowAnimations = R.style.animation;


                cancle_btn = bookingdetaildialog.findViewById(R.id.cancle_btn);

                txt_product_name = bookingdetaildialog.findViewById(R.id.txt_product_name);
                txt_product_price = bookingdetaildialog.findViewById(R.id.txt_product_price);
                txt_grade = bookingdetaildialog.findViewById(R.id.txt_grade);
                txt_price_per_kg = bookingdetaildialog.findViewById(R.id.txt_price_per_kg);
                txt_quantity = bookingdetaildialog.findViewById(R.id.txt_quantity);
                txt_packaging_size = bookingdetaildialog.findViewById(R.id.txt_packaging_size);
                txt_total_weight = bookingdetaildialog.findViewById(R.id.txt_total_weight);
                txt_total_amount = bookingdetaildialog.findViewById(R.id.txt_total_amount);
                txt_billing_address = bookingdetaildialog.findViewById(R.id.txt_billing_address);
                txt_shipping_address = bookingdetaildialog.findViewById(R.id.txt_shipping_address);
                txt_delivery_notes = bookingdetaildialog.findViewById(R.id.txt_delivery_notes);

                txt_pending_amount = bookingdetaildialog.findViewById(R.id.txt_pending_amount);
                txt_paid_amount = bookingdetaildialog.findViewById(R.id.txt_paid_amount);
                btn_purchase = bookingdetaildialog.findViewById(R.id.btn_purchase);

                cancle_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookingdetaildialog.dismiss();
                    }
                });
                bookingdetaildialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolers extends RecyclerView.ViewHolder {
        LinearLayout layout_for_booking;
        public ViewHolers(@NonNull View itemView) {
            super(itemView);
            layout_for_booking=itemView.findViewById(R.id.layout_for_booking);
        }
    }
}
