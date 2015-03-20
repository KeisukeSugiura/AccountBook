package com.example.FunctionCalculator.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import com.example.FunctionCalculator.R;

/**
 * Created by yoshida keisuke on 2015/03/17.
 */
public class MonthFragment extends Fragment {
    View mView;
    TextView mDate;
    CalendarView mCalendar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_monthaccount_fragment,container,false);
        mCalendar = (CalendarView) mView.findViewById(R.id.monthaccount_calendarView);
        mDate= (TextView) mView.findViewById(R.id.monthaccount_month);
        MonthFragmentClickListener listener = new MonthFragmentClickListener();
        mCalendar.setOnClickListener(listener);
        return mView;
    }


    private class MonthFragmentClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            long date=mCalendar.getDate();
            mDate.setText(String.valueOf(date));
        }
    }
}