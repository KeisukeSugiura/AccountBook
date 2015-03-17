package com.example.FunctionCalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yoshida keisuke on 2015/02/23.
 */
public class testFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CalculateView view = new CalculateView(getActivity());

        return view;
    }
}