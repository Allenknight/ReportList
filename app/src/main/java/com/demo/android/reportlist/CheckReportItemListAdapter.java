package com.demo.android.reportlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AllenHan on 2018/3/27.
 */

public class CheckReportItemListAdapter extends RecyclerView.Adapter<CheckReportItemListAdapter.ViewHolder> {

    private Context mContext;
    private List<String> itemList = new ArrayList<>();

    private List<Boolean> itemCheckList = new ArrayList<>();

    private onClickListener mOnClickListener;

    public CheckReportItemListAdapter(Context context, List<String> itemList, List<Boolean> itemCheckList) {
        this.mContext = context;
        this.itemList = itemList;
        this.itemCheckList = itemCheckList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item_check_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvItemText.setText(itemList.get(position));
        holder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemCheckList.get(position)){
                    itemCheckList.set(position, false);
                    holder.ivCheck.setImageResource(R.mipmap.check_circle_s);
                }else{
                    itemCheckList.set(position, true);
                    holder.ivCheck.setImageResource(R.mipmap.check_circle_blue);
                }
                mOnClickListener.onCheckClick(v, position);
            }
        });
        holder.ivMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onMarkClick(v, position);
            }
        });
        if(itemCheckList.get(position)) {
            holder.ivCheck.setImageResource(R.mipmap.check_circle_blue);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface onClickListener {
        void onCheckClick(View view, int itemPosition);

        void onMarkClick(View view, int itemPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_click)
        ImageView ivCheck;
        @BindView(R.id.tv_item_text)
        TextView tvItemText;
        @BindView(R.id.iv_mark)
        ImageView ivMark;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
