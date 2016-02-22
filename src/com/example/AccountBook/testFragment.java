package com.example.AccountBook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.AccountBook.view.AccountCalendarView;

/**
 * Created by yoshida keisuke on 2015/02/23.
 */
public class testFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

/*
        CalculateView view = new CalculateView(getActivity());
*/

       // SelectIconView view = new SelectIconView(getActivity());
        //InputPriceView view = new InputPriceView((getActivity()));
        AccountCalendarView view = new AccountCalendarView(getActivity());
        Log.i("sssss","started");
        view.setDayItemClickListener(new OnItemclick());

        return view;
    }

    public class OnItemclick implements AccountCalendarView.onDayItemClickListener{
        @Override
        public void onItemClick(int i) {
            Toast.makeText(getActivity(),String.valueOf(i),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNextClick() {

        }

        @Override
        public void onBackClick() {

        }
    }
}