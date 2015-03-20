package com.example.FunctionCalculator.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.FunctionCalculator.R;
import com.example.FunctionCalculator.database.AccountDatabase;
import com.example.FunctionCalculator.database.DatabaseFactory;
import com.example.FunctionCalculator.model.DayAccountModel;
import com.example.FunctionCalculator.model.OnedayListAdapter;

import java.util.ArrayList;

/**
 * Created by yoshida keisuke on 2015/03/17.
 */
public class DayAccountFragment extends Fragment implements OnedayListAdapter.DeleteListener{


    public Context mContext;
    private FrameLayout mView;
    private OnedayListAdapter mTodayAdapter;
    private ArrayList<DayAccountModel> mTodayAccountData;
    public int mYear;
    public int mMonth;
    public int mDay;

    private ListView mListView;
    private TextView mDateTextView;
    private TextView mSumAccountTextView;
    private EditText mPriceEditText;
    private Button mTypeButton;
    private Button mUpdateButton;
    private AccountDatabase mAccountDatabase;


    /*
    ライフサイクル
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();

        mView = new FrameLayout(mContext);
        LinearLayout baseLayout = (LinearLayout) inflater.inflate(R.layout.layout_dayaccount_fragment,container,false);

        //mTodayAccountData の部分はデータベース参照に変更
        mTodayAccountData = new ArrayList<DayAccountModel>();
        /*mTodayAccountData.add(new DayAccountModel(Long.getLong("1"),1,1,1,1000,1));
        mTodayAccountData.add(new DayAccountModel(Long.getLong("2"),2,2,2,2000,2));
        mTodayAccountData.add(new DayAccountModel(Long.getLong("3"),3,3,3,3000,3));*/
        //
        //listview
        mTodayAdapter = new OnedayListAdapter(mContext,mTodayAccountData);
        mListView = (ListView)baseLayout.findViewById(R.id.dayaccount_listView);
        mListView.setAdapter(mTodayAdapter);

        mDateTextView = (TextView)baseLayout.findViewById(R.id.dayaccount_day);
        mSumAccountTextView = (TextView)baseLayout.findViewById(R.id.dayaccount_sum);
        mTypeButton = (Button)baseLayout.findViewById(R.id.dayaccount_type_button);
        mUpdateButton = (Button)baseLayout.findViewById(R.id.dayaccount_update_button);
        mPriceEditText = (EditText)baseLayout.findViewById(R.id.dayaccount_editprice);

        //button
        DayAccountOnClickListener listener = new DayAccountOnClickListener();
        mTypeButton.setOnClickListener(listener);
        mUpdateButton.setOnClickListener(listener);


        mView.addView(baseLayout);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAccountDatabase = DatabaseFactory.getAccountDatabase(mContext);
        Log.d("account", mAccountDatabase.toString());
        acceptArgments();
        query2Database();
        setDate();
        setSum();
        mSumAccountTextView.setText("合計 "+String.valueOf(getSumOfAccount())+"円");

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /*
    メソッド
     */

    /**
     * 支出の合計を返すメソッド
     * @return
     */
    private int getSumOfAccount(){
        int sum = 0;

        for(DayAccountModel aAcountData : mTodayAccountData){
            sum += aAcountData.getPrice();
        }


        return sum;

    }


    /**
     * 引数をバンドルを介して受け入れる
     */
    public void acceptArgments(){
        if (getArguments() != null && getArguments().containsKey("year")) {
            mYear = getArguments().getInt("year");
        }else{
            mYear = 2014;
        }

        if (getArguments() != null && getArguments().containsKey("month")) {
            mMonth = getArguments().getInt("month");
        }else{
            mMonth = 3;
        }

        if (getArguments() != null && getArguments().containsKey("day")) {
            mDay = getArguments().getInt("day");
        }else {
            mDay = 20;
        }

    }

    /**
         データベースに指定している日のデータを要求する
     */
    private void query2Database(){
       mTodayAccountData = mAccountDatabase.getOneDayAccount(mYear,mMonth,mDay);
        for(DayAccountModel data: mTodayAccountData){
            mTodayAdapter.addAccount(data);
        }
        mTodayAdapter.notifyDataSetChanged();
    }


    /**
     * データベースに追加する
     * @param price
     * @param type
     */
    private void addNewData(int type,int price){
        DayAccountModel data = new DayAccountModel(mYear,mMonth,mDay,price,type);

        // データベース更新処理
        mAccountDatabase.insertData(data);
        mTodayAccountData.add(data);
        mTodayAdapter.addAccount(data);
        mTodayAdapter.notifyDataSetChanged();
        setSum();
    }

    private void deleteData(DayAccountModel model){
        mAccountDatabase.deleteData(model);
        mTodayAccountData.remove(model);
        mTodayAdapter.deleteAccounrt(model);
        mTodayAdapter.notifyDataSetChanged();
        setSum();
    }


    private void setSum(){
        mSumAccountTextView.setText("合計 "+String.valueOf(getSumOfAccount())+"円");

    }

    private void setDate(){
        mDateTextView.setText(String.valueOf(mYear)+"年 "+String.valueOf(mMonth)+"月 "+String.valueOf(mDay)+"日");
    }




    /**
     このフラグメントにおけるクリックリスナー
     */
    private class DayAccountOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //TODO 処理の記述

            int id = view.getId();
            switch (id){
                case R.id.dayaccount_type_button:
                    break;
                case R.id.dayaccount_update_button:
                    Log.d("step1", "click ok---");
                    String editPrice = mPriceEditText.getText().toString();
                    if(editPrice != null && !editPrice.isEmpty()) {
                        addNewData(1, Integer.valueOf(editPrice));
                    }
                    break;
            }
        }
    }

    @Override
    public void deleteAccount(DayAccountModel model) {
        //listAdapterからのinterfaceメソッド
        Log.d("delete","okeeee");
        deleteData(model);
    }


}

