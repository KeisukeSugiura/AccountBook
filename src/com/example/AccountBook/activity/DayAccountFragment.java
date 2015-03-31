package com.example.AccountBook.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.AccountBook.R;
import com.example.AccountBook.database.AccountDatabase;
import com.example.AccountBook.database.DatabaseFactory;
import com.example.AccountBook.model.DayAccountModel;
import com.example.AccountBook.model.OnedayListAdapter;
import com.example.AccountBook.view.InputPriceView;
import com.example.AccountBook.view.SelectIconView;

import java.util.ArrayList;

/**
 * Created by yoshida keisuke on 2015/03/17.
 */
public class DayAccountFragment extends Fragment implements OnedayListAdapter.DeleteListener {

    //TODO 入力機構、ビューの作成

    public Context mContext;
    private FrameLayout mView;
    private OnedayListAdapter mTodayAdapter;
    private ArrayList<DayAccountModel> mTodayAccountData;
    public int mYear;
    public int mMonth;
    public int mDay;

    private int mInputType;
    private String mInputPrice;

    private ListView mListView;
    private TextView mDateTextView;
    private TextView mSumAccountTextView;
    public TextView mPriceEditText;
    private ImageView mTypeButton;
    private ImageView mUpdateButton;
    private AccountDatabase mAccountDatabase;
    private SelectIconView mSelectionIconView;
    private InputPriceView mInputPriceView;

    /*
    ライフサイクル
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();

        mView = new FrameLayout(mContext);
        LinearLayout baseLayout = (LinearLayout) inflater.inflate(R.layout.layout_dayaccount_fragment, container, false);

        //mTodayAccountData の部分はデータベース参照に変更
        mTodayAccountData = new ArrayList<DayAccountModel>();
        /*mTodayAccountData.add(new DayAccountModel(Long.getLong("1"),1,1,1,1000,1));
        mTodayAccountData.add(new DayAccountModel(Long.getLong("2"),2,2,2,2000,2));
        mTodayAccountData.add(new DayAccountModel(Long.getLong("3"),3,3,3,3000,3));*/
        //
        //listview
        mTodayAdapter = new OnedayListAdapter(this, mTodayAccountData);
        mListView = (ListView) baseLayout.findViewById(R.id.dayaccount_listView);
        mListView.setAdapter(mTodayAdapter);

        mDateTextView = (TextView) baseLayout.findViewById(R.id.dayaccount_day);
        mSumAccountTextView = (TextView) baseLayout.findViewById(R.id.dayaccount_sum);
        mTypeButton = (ImageView) baseLayout.findViewById(R.id.dayaccount_type_button);
        mUpdateButton = (ImageView) baseLayout.findViewById(R.id.dayaccount_update_button);
        mPriceEditText = (TextView) baseLayout.findViewById(R.id.dayaccount_editprice);
        mSelectionIconView = new SelectIconView(mContext);
        mInputPriceView = new InputPriceView(mContext);


        //button
        DayAccountOnClickListener listener = new DayAccountOnClickListener();
        mTypeButton.setOnClickListener(listener);
        mUpdateButton.setOnClickListener(listener);
        mPriceEditText.setOnClickListener(listener);

        mInputType = 8;
        mInputPrice = "";
        mPriceEditText.setText("金額を入力してください");

        mView.addView(baseLayout);
        mView.setFocusable(true);


        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAccountDatabase = DatabaseFactory.getAccountDatabase(mContext);
        //Log.d("account", mAccountDatabase.toString());
        acceptArgments();
        query2Database();
        setDate();
        setSum();
        //mSumAccountTextView.setText("合計 "+String.valueOf(getSumOfAccount())+"円");

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
     *
     * @return
     */
    private int getSumOfAccount() {
        int sum = 0;

        for (DayAccountModel aAcountData : mTodayAccountData) {
            sum += aAcountData.getPrice();
        }


        return sum;

    }


    /**
     * 引数をバンドルを介して受け入れる
     */
    public void acceptArgments() {
        if (getArguments() != null && getArguments().containsKey("Year")) {
            mYear = getArguments().getInt("Year");
        } else {
            mYear = 2014;
        }

        if (getArguments() != null && getArguments().containsKey("Month")) {
            mMonth = getArguments().getInt("Month");
        } else {
            mMonth = 3;
        }

        if (getArguments() != null && getArguments().containsKey("Day")) {
            mDay = getArguments().getInt("Day");
        } else {
            mDay = 20;
        }
       // Log.i("timed", String.valueOf(mYear));
      //  Log.i("timed", String.valueOf(mMonth));
      //  Log.i("timed", String.valueOf(mDay));


    }

    /**
     * データチェンジしたら呼ぶ
     *
     * @param year
     * @param month
     * @param day
     */
    public void changeDate(int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mTodayAccountData.clear();
        mTodayAdapter.clearAccouunt();
        query2Database();
        setSum();
        setDate();
    }


    /**
     * データベースに指定している日のデータを要求する
     */
    private void query2Database() {

        mTodayAccountData = mAccountDatabase.getOneDayAccount(mYear, mMonth, mDay);
        for (DayAccountModel data : mTodayAccountData) {
            mTodayAdapter.addAccount(data);
        }
        mTodayAdapter.notifyDataSetChanged();

    }


    /**
     * データベースに追加する
     *
     * @param price
     * @param type
     */
    private void addNewData(int type, int price) {
        DayAccountModel data = new DayAccountModel(mYear, mMonth, mDay, price, type);

        // データベース更新処理
        mAccountDatabase.insertData(data);
        mTodayAccountData.add(data);
        mTodayAdapter.addAccount(data);
        mTodayAdapter.notifyDataSetChanged();
        mListView.setSelection(mTodayAccountData.size() - 1);
        setSum();
    }

    private void deleteData(DayAccountModel model) {
        mAccountDatabase.deleteData(model);
        mTodayAccountData.remove(model);
        mTodayAdapter.deleteAccounrt(model);
        mTodayAdapter.notifyDataSetChanged();
        setSum();
        Toast.makeText(mContext,"削除しました",Toast.LENGTH_SHORT).show();
    }


    private void setSum() {
        mSumAccountTextView.setText("合計 " + String.valueOf(getSumOfAccount()) + "円");

    }

    private void setDate() {
        mDateTextView.setText(String.valueOf(mYear) + "年 " + String.valueOf(mMonth) + "月 " + String.valueOf(mDay) + "日");
    }

    public void setIcon(int select) {
        mTypeButton.setImageResource(mSelectionIconView.getDrawableId(select));
        mView.removeView(mSelectionIconView);
        mInputType = select;
    }

    public void setNum(int num) {
        if (num < 10) {
            mInputPrice += String.valueOf(num);
        } else if (num == 10) {
            if (!mInputPrice.isEmpty()) {
                mInputPrice = (String) mInputPrice.subSequence(0, mInputPrice.length() - 1);
            }
        } else if (num == 11) {
            mView.removeView(mInputPriceView);
        }

        if (mInputPrice.isEmpty()) {
            mPriceEditText.setText("金額を入力してください");
        } else {
            mPriceEditText.setText(mInputPrice + " 円");
        }
    }

    public void setPrice(int num) {
        if (num == 0) {
            mInputPrice="";
            mPriceEditText.setText("金額を入力してください");
        } else {
            mInputPrice = String.valueOf(num);
            mPriceEditText.setText(mInputPrice+" 円");
        }
    }


    /**
     * このフラグメントにおけるクリックリスナー
     */
    private class DayAccountOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //TODO 処理の記述

            int id = view.getId();
            switch (id) {
                case R.id.dayaccount_editprice:
                    mView.removeView(mInputPriceView);
                    mView.removeView(mSelectionIconView);
                    setPrice(0);
                    mView.addView(mInputPriceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    break;
                case R.id.dayaccount_type_button:
                    mView.removeView(mSelectionIconView);
                    mView.removeView(mInputPriceView);
                    mView.addView(mSelectionIconView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    break;
                case R.id.dayaccount_update_button:
               //     Log.d("step1", "click ok---");
                    mView.removeView(mSelectionIconView);
                    mView.removeView(mInputPriceView);
                   /* String editPrice = mPriceEditText.getText().toString();
                    if(editPrice != null && !editPrice.isEmpty()) {
                        addNewData(mInputType, Integer.valueOf(editPrice));
                        notifyDataChanged();
                    }*/
                    if (!mInputPrice.isEmpty() && mInputPrice.length() <8) {
                        addNewData(mInputType, Integer.valueOf(mInputPrice));
                        notifyDataChanged();
                    }else if(mInputPrice.isEmpty()){
                        Toast.makeText(mContext,"金額を入力してください",Toast.LENGTH_SHORT).show();
                    }else if(mInputPrice.length() >= 8){
                        mInputPrice="";
                        mPriceEditText.setText("金額を入力してください");
                        Toast.makeText(mContext,"7桁まででお願いします",Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }


    @Override
    public void deleteAccount(DayAccountModel model) {
        //listAdapterからのinterfaceメソッド
      //  Log.d("delete", "okeeee");

        deleteData(model);
        notifyDataChanged();
    }

    /**
     * アクティビティに通知する
     */
    private void notifyDataChanged() {
        Activity activity = getActivity();
        if (activity instanceof onDatasetChanged) {
            onDatasetChanged listener = (onDatasetChanged) activity;
            listener.onChanged();
        }

    }

    public interface onDatasetChanged {
        public void onChanged();
    }


}

