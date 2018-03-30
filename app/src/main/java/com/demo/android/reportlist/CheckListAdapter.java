package com.demo.android.reportlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AllenHan on 2018/3/26.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder>{

    Context mContext;
    //父列表list
    List<String> mTitleList = new ArrayList<>();
    //子列表list
    List<String[]> mItemList = new ArrayList<>();
    //标记list
    List<Boolean> mCheckList = new ArrayList<>();
    OnClickListener mOnClickListener;
    List<CheckReportItemListAdapter> mItemAdpList = new ArrayList<>();
    CheckReportItemListAdapter tempadp;
    //子列表标记list
    List<List<Boolean>> mItemCheckList = new ArrayList<>();


    public CheckListAdapter(Context context, List<String> titleList, List<String[]> itemList, List<Boolean> checkList, List<List<Boolean>> mItemCheckList){
        this.mContext = context;
        this.mTitleList = titleList;
        this.mItemList = itemList;
        this.mCheckList = checkList;
        this.mItemCheckList = mItemCheckList;
        mItemAdpList.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item_check_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//        holder.tvItemText.setText(mContext.getResources().getStringArray(R.array.rbs_implementation_check_list_title)[position]);
        holder.tvItemText.setText(mTitleList.get(position));

        if(position >= mItemAdpList.size())
            mItemAdpList.add( new CheckReportItemListAdapter(mContext, Arrays.asList(mItemList.get(position)), mItemCheckList.get(position)));
        holder.rvItemList.setLayoutManager(new LinearLayoutManager(mContext));
        mItemAdpList.set(position , new CheckReportItemListAdapter(mContext, Arrays.asList(mItemList.get(position)), mItemCheckList.get(position)));
        holder.rvItemList.setAdapter(mItemAdpList.get(position));
        holder.rvItemList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mItemAdpList.get(position).setOnClickListener(new CheckReportItemListAdapter.onClickListener() {

            @Override
            public void onCheckClick(View view, int itemPosition) {
                mOnClickListener.onItemCheckClick(view, position, itemPosition);
            }

            @Override
            public void onMarkClick(View view, int itemPosition) {
                mOnClickListener.onItemMarkClick(view, position, itemPosition);
            }
        });

        holder.tvItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.rvItemList.getVisibility() == View.VISIBLE){
                    holder.rvItemList.setVisibility(View.GONE);
                }else{
                    holder.rvItemList.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheckList.get(position)){
                    mCheckList.set(position, false);
                    holder.ivCheck.setImageResource(R.mipmap.check_circle_s);
                }else{
                    mCheckList.set(position, true);
                    holder.ivCheck.setImageResource(R.mipmap.check_circle_blue);
                }
                mOnClickListener.onCheckClick(v, position);
                if(mCheckList.get(position))
                    mItemAdpList.get(position).notifyDataSetChanged();
            }
        });
        holder.ivMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onMarkClick(v, position);
            }
        });

    }

    public void setOnClickListener(OnClickListener onClickListener){
        mOnClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onCheckClick(View view, int position);

        void onItemCheckClick(View view, int fatherPosition, int itemPosition);

        void onMarkClick(View view, int position);

        void onItemMarkClick(View view, int fatherPosition, int itemPosition);
    }

    @Override
    public int getItemCount() {
//        return mContext.getResources().getStringArray(R.array.rbs_implementation_check_list_title).length;
        return mTitleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_item_click)
        ImageView ivCheck;
        @BindView(R.id.tv_item_text)
        TextView tvItemText;
        @BindView(R.id.iv_mark)
        ImageView ivMark;
        @BindView(R.id.rv_check_item_list)
        RecyclerView rvItemList;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
