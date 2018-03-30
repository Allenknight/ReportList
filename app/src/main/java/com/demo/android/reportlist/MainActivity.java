package com.demo.android.reportlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.rv_check_list)
    RecyclerView rvCheckList;
    CheckListAdapter myAdp;

    //父列表list
    List<String> mTitleList = new ArrayList<>();
    //子列表list
    List<String[]> mItemList = new ArrayList<>();
    //父标题选中list
    List<Boolean> mCheckList = new ArrayList<>();
    //子标题选中item list
    List<List<Boolean>> mItemCheckList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public void initView(){
        rvCheckList.setLayoutManager(new LinearLayoutManager(this));
        myAdp = new CheckListAdapter(this, mTitleList, mItemList, mCheckList, mItemCheckList);
        rvCheckList.setAdapter(myAdp);
        rvCheckList.setNestedScrollingEnabled(false);
        myAdp.setOnClickListener(new CheckListAdapter.OnClickListener() {
            @Override
            public void onCheckClick(View view, int position) {
                if(mCheckList.get(position)) {
                    //父标题check 子标题全部check
                    for (int i = 0; i < mItemCheckList.get(position).size(); i++) {
                        mItemCheckList.get(position).set(i, true);
                    }
                }else{

                }
            }

            @Override
            public void onItemCheckClick(View view, int fatherPosition, int itemPosition) {

            }

            @Override
            public void onMarkClick(View view, int position) {

            }

            @Override
            public void onItemMarkClick(View view, int fatherPosition, int itemPosition) {

            }
        });
    }

    public void initData() {
        String[] dataArray = getResources().getStringArray(R.array.report_title);
        // 父列表赋值
        mTitleList.addAll(Arrays.asList(dataArray));
        // 子列表赋值
        for (int i = 0; i < dataArray.length; i++) {
            @SuppressLint("Recycle") TypedArray typedArray = getResources().obtainTypedArray(R.array.report_content_list);
            mItemList.add(getResources().getStringArray(typedArray.getResourceId(i, 0)));
        }
        // 标记列表赋值
        for (String aDataArray : dataArray) {
            mCheckList.add(false);
        }
        for (int i = 0; i < dataArray.length; i++) {
            List<Boolean> list = new ArrayList<>();
            for (int j = 0; j < mItemList.get(i).length; j++) {
                list.add(false);
            }
            mItemCheckList.add(list);
        }
    }
}
