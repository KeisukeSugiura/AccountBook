package com.example.AccountBook.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import com.example.AccountBook.R;
import com.example.AccountBook.database.AccountDatabase;
import com.example.AccountBook.database.DatabaseFactory;
import com.example.AccountBook.model.DayAccountModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yoshida keisuke on 2015/03/17.
 */
public class MonthFragment extends Fragment {
    private View mView;
    //private TextView mDate;
    private TextView mSumMonthText;
    private TextView mDateText;
    private CalendarView mCalendar;

    private int mYear;
    private int mMonth;
    private int mDay;

    private ArrayList<DayAccountModel> mAccountList;

    private AccountDatabase mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_monthaccount_fragment, container, false);
        mCalendar = (CalendarView) mView.findViewById(R.id.monthaccount_calendarView);
        //mDate = (TextView) mView.findViewById(R.id.monthaccount_month);
        mDateText = (TextView) mView.findViewById(R.id.monthaccount_dayprice);
        mSumMonthText = (TextView) mView.findViewById(R.id.monthaccount_sumprice);
        mCalendar.setOnDateChangeListener(new onDateClickListener());
        Calendar calendar =Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH)+1;
        mDay = calendar.get(Calendar.DATE);
       // mDate.setText(String.valueOf(mYear)+String.valueOf(mMonth)+String.valueOf(mDay));

        mDatabase = DatabaseFactory.getAccountDatabase(getActivity());
        queryMonth2Database();
        setDaySumText();




        return mView;
    }

    private void setStatus2View(){

    }


    /**
     * データベースに月のアカウントデータを要求し合計を表示
     */
    private void queryMonth2Database(){
        mAccountList= mDatabase.getOneMonthAccount(mYear,mMonth);
        setMonthSumText();
    }


    /**
     * 選択している日のモデルを返す
     * @return
     */
    private ArrayList<DayAccountModel> getListWithDay(){
        ArrayList<DayAccountModel> models = new ArrayList<DayAccountModel>();
        for(DayAccountModel amodel : mAccountList){
            if(amodel.getDay() == mDay){
                models.add(amodel);
            }
        }

        return models;
    }


    /**
     * 合計を表示
     */
    private void setMonthSumText(){
        int sum =0;
        for(DayAccountModel model: mAccountList){
            sum+=model.getPrice();
        }
        mSumMonthText.setText(String.valueOf(mMonth)+"月の合計: "+String.valueOf(sum));
    }


    /**
     * 合計を表示
     */
    private void setDaySumText(){
        ArrayList<DayAccountModel> models=getListWithDay();
        int sum =0;
        for(DayAccountModel model: models){
            sum+=model.getPrice();
        }
        mDateText.setText("この日の合計: "+String.valueOf(sum));

    }

    public void changeDataset(){
        queryMonth2Database();
        setMonthSumText();
        setDaySumText();
    }

    public void notifyDateChanged(int year,int month,int day){
        Activity activity = getActivity();
        if(activity instanceof onSelectedDateChangeListener){
            onSelectedDateChangeListener listener = (onSelectedDateChangeListener)activity;
            listener.onDateChanged(year,month,day);
        }
    }


    public interface onSelectedDateChangeListener{
       public void onDateChanged(int year,int month,int day);
    }


    private class onDateClickListener implements CalendarView.OnDateChangeListener {
        @Override
        public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
            mYear = i;
            mMonth = i1+1;
            mDay = i2;
        //    mDate.setText(String.valueOf(mYear) + String.valueOf(mMonth) + String.valueOf(mDay));
            notifyDateChanged(mYear, mMonth, mDay);
            queryMonth2Database();
            setDaySumText();
        }

    }


}