package com.mobile.agri10x.Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.agri10x.R;
import com.mobile.agri10x.models.FaqInfoModelDataInfo;

import java.util.ArrayList;

public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.ViewHolderFaq> {
    private Context mContext;
    private ArrayList<FaqInfoModelDataInfo> mFaqInfoModelArraylist;
    private LayoutInflater mLayoutInflater;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    public FaqListAdapter(Context context, ArrayList<FaqInfoModelDataInfo> faqInfoModelArrayList) {
        this.mContext = context;
        this.mFaqInfoModelArraylist = faqInfoModelArrayList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolderFaq onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_adapter_faqlist, parent, false);
        ViewHolderFaq viewHolderFaq = new ViewHolderFaq(view);
        return viewHolderFaq;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFaq viewHolder, int i) {
        viewHolder.setIsRecyclable(false);
        viewHolder.tvTitle.setText(mFaqInfoModelArraylist.get(i).getmTitle());
        viewHolder.tvDescription.setText(mFaqInfoModelArraylist.get(i).getmDescription());
        //check if view is expanded
        final boolean isExpanded = expandState.get(i);
        viewHolder.expandableLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        viewHolder.buttonLayout.setRotation(expandState.get(i) ? 180f : 0f);
        viewHolder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(viewHolder.expandableLayout, viewHolder.buttonLayout,viewHolder.expandBtn,  i);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (mFaqInfoModelArraylist.size() == 0) {
            return 0;
        } else {
            return mFaqInfoModelArraylist.size();
        }
    }

    public class ViewHolderFaq extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvDescription;
        public RelativeLayout buttonLayout;
        public LinearLayout expandableLayout;
        public View expandBtn;
        public ViewHolderFaq(@NonNull View itemView) {
            super(itemView);

            tvTitle = (TextView)itemView.findViewById(R.id.textView_name);
            tvDescription = (TextView)itemView.findViewById(R.id.textView_Owner);
            buttonLayout = (RelativeLayout) itemView.findViewById(R.id.button);
            expandableLayout = (LinearLayout) itemView.findViewById(R.id.expandableLayout);
            expandBtn = itemView.findViewById(R.id.button_view);
        }
    }

    private void onClickButton(final LinearLayout expandableLayout, final RelativeLayout buttonLayout,final View expandBtn, final  int i) {

        //Simply set View to Gone if not expanded
        //Not necessary but I put simple rotation on button layout
        if (expandableLayout.getVisibility() == View.VISIBLE){
            expandBtn.setBackgroundResource(R.drawable.ic_add);
            createRotateAnimator(buttonLayout, 180f, 0f).start();
            expandableLayout.setVisibility(View.GONE);
            expandState.put(i, false);
        }else{
            expandBtn.setBackgroundResource(R.drawable.ic_remove);
            createRotateAnimator(buttonLayout, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            expandState.put(i, true);
        }
    }

    //Code to rotate button
    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}

