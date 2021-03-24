package com.mobile.agri10x.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.Fragments.AddStockFragment;
import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetFeaturesbyCommodityDatum;
import com.mobile.agri10x.models.GetStockByIDDatum;
import com.mobile.agri10x.utils.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddStockFeatuesAdapter  extends RecyclerView.Adapter<AddStockFeatuesAdapter.MyViewHolder> {

    public  static ArrayList<String> featuresids = new ArrayList<>();
    private List<GetFeaturesbyCommodityDatum> featurelist;
    Context context;
    OnItemClickListener onItemClickListener;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_stock_featues_adapter, parent, false);
        context = parent.getContext();
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_featurename.setText(featurelist.get(position).getFeatureName());

        holder.checkbox_features.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    featuresids.add(featurelist.get(position).getId());
                    Toast.makeText(context, "isChecked - " + featurelist.get(position).getId(), Toast.LENGTH_SHORT).show();
                    Log.d("idss", String.valueOf(featuresids));
                    onItemClickListener.OnItemClick(featuresids);

                }

            }
        });

    }


    public AddStockFeatuesAdapter(FragmentActivity activity, List<GetFeaturesbyCommodityDatum> featurelist, OnItemClickListener onItemClickListener) {
        this.featurelist = featurelist;
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return featurelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_featurename;
        CheckBox checkbox_features;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_featurename=itemView.findViewById(R.id.txt_featurename);
            checkbox_features=itemView.findViewById(R.id.checkbox_features);



        }
    }
}