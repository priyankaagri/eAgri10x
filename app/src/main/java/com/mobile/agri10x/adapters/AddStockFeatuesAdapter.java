package com.mobile.agri10x.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.GetFeaturesbyCommodityDatum;
import com.mobile.agri10x.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

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
                if (isChecked )
                {
// perform logic
                    featuresids.add(featurelist.get(position).getId());
                   // Toast.makeText(context, "isChecked - " + featurelist.get(position).getId(), Toast.LENGTH_SHORT).show();
                    Log.d("idss", String.valueOf(featuresids));
                    onItemClickListener.OnItemClick(featuresids);

                }else {
                    if(featuresids.size()>0){
                        if(featuresids.contains(featurelist.get(position).getId())){
                            featuresids.remove(featurelist.get(position).getId());
                        }else {

                        }
                        Log.d("idssize", String.valueOf(featuresids));
                    }


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