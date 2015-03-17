package com.example.FunctionCalculator;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by yoshida keisuke on 2015/02/24.
 */
public class CalculateFragment extends Fragment implements CalculateView.CalculateViewListener {

    String buffer;
    Double result = 0.0;
    FrameLayout mView;
    TextView mTextView;
    CalculateView mCalculateView;
    Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        mView = new FrameLayout(mActivity);
        mCalculateView = new CalculateView(mActivity);
        mTextView = new TextView(mActivity);
        Rect rect = mCalculateView.getmControlPanelRect();
        RelativeLayout relativeLayout = new RelativeLayout(mActivity);
        relativeLayout.setBackgroundColor(Color.BLACK);
        relativeLayout.setPadding(10, 10, 10, 10);
        mTextView.setHeight(rect.bottom);
        mTextView.setWidth(rect.right);
        mTextView.setTextSize(30f);
        mTextView.setPadding(10, 10, 10, 10);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setBackgroundColor(R.drawable.cal_console);
        relativeLayout.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayout.addView(mTextView);
        mView.addView(mCalculateView);
        mView.addView(relativeLayout);

        initBuffer();
        showBuffer();

        return mView;
    }

    void showBuffer() {
        // 文字列として010などのように0が先頭についてしまう時，
        // これを一度Integerに変換すると10と認識される．
        // さらにこの10を文字列に変換しバッファにしまう．
        //buffer = Double.toString(Double.parseDouble(buffer));
        if (buffer.isEmpty()) {
            mTextView.setText("0");
        } else {
            mTextView.setText(buffer);
        }
    }

    void initBuffer() {
        buffer = null;
        buffer = new String("");
    }


    @Override
    public void onSelectNumberItem(int item) {
        buffer = buffer + String.valueOf(item);

        showBuffer();
    }

    @Override
    public void onSelectShiftNumberItem(int item) {
        switch (item) {
            case 0:
                buffer=buffer+".";
                break;
            case 1:
                buffer = buffer + "*";

                break;
            case 2:
                buffer = buffer + "/";

                break;
            case 3:
                buffer = buffer + "^";
                break;
            case 4:
                buffer = buffer + "log(";

                break;
            case 5:
                buffer = buffer + "tan(";

                break;
            case 6:
                buffer = buffer + "cos(";
                break;
            case 7:
                buffer = buffer + "sin(";
                break;
            case 8:
                buffer = buffer + "+";
                break;
            case 9:
                buffer = buffer + "-";
                break;
        }

        showBuffer();

    }

    @Override
    public void onSelectActionItem(int item) {

        switch (item) {
            case 0:
                initBuffer();
                break;
            case 1:
                if(!buffer.isEmpty()) {
                    buffer = buffer.substring(0, buffer.length() - 1);
                }
                    break;
            case 2:
                buffer = buffer + "(";

                break;
            case 3:
                buffer = buffer + ")";


                break;


        }

        showBuffer();

    }

    @Override
    public void onSelectEqual() {
        //ExcuteNode node = new ExcuteNode(buffer);
        if (!buffer.isEmpty()) {
            ExcuteFormula formula = new ExcuteFormula(buffer);
            /*ArrayList<String> a = node.getCheckFormula();
            String str = new String("");

			for(Iterator i = a.iterator(); i.hasNext();){
				str += i.next();
			}
			mTextView.setText(str);*/
            buffer = String.valueOf(formula.getResult());
            showBuffer();
            if (buffer.equals("NaN") || buffer.equals("Infinity")) {
                initBuffer();
            }
        }

    }
}