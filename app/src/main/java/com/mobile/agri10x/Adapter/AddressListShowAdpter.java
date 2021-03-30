package com.mobile.agri10x.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.getAddressData;

import java.util.ArrayList;

public class AddressListShowAdpter extends RecyclerView.Adapter<AddressListShowAdpter.HolderView> {
    Context context;
    boolean check;


    ArrayList<getAddressData> addlist = new ArrayList<>();

    public AddressListShowAdpter(ArrayList<getAddressData> billingadd, Context context, boolean check) {
        this.context=context;
        this.check=check;
        this.addlist=billingadd;
    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list_adpter_view, parent, false);
        HolderView viewHolder = new HolderView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {


        holder.txt_type_adrres.setText(addlist.get(position).getAddressType());
        //  holder.txt_add1.setText(addlist.get(position).getCity());
        holder.txt_add2.setText(addlist.get(position).getAddressLine1()+"  "+addlist.get(position).getAddressLine2());
        holder.txt_add3.setText(addlist.get(position).getPincode());
        holder.txt_add4.setText(addlist.get(position).getCity()+" , "+addlist.get(position).getState());

    }

    @Override
    public int getItemCount() {
        return addlist.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        TextView txt_type_adrres, txt_add1,txt_add2,txt_add3,txt_add4;
        public HolderView(@NonNull View itemView) {
            super(itemView);

            this.txt_type_adrres = itemView.findViewById(R.id.txt_type_adrres);
            this.txt_add1 = itemView.findViewById(R.id.txt_add1);
            this.txt_add2 = itemView.findViewById(R.id.txt_add2);
            this.txt_add3 = itemView.findViewById(R.id.txt_add3);
            this.txt_add4 = itemView.findViewById(R.id.txt_add4);
        }
    }
}
