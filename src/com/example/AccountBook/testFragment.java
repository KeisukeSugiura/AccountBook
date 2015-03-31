package com.example.AccountBook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.AccountBook.view.InputPriceView;

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
        InputPriceView view = new InputPriceView((getActivity()));
        return view;
    }
}