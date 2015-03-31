package com.example.AccountBook.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.*;
import android.widget.GridLayout;
import android.widget.TextView;
import com.example.AccountBook.R;

/**
 * Created by yoshida keisuke on 2015/03/27.
 */
public class InputPriceView extends GridLayout {

    private Context mContext;
    private int sWindowWidth;
    private int sWindowHeight;

    public InputPriceView(Context context) {
        super(context);
        mContext=context;
        initView();
    }

    public void initView() {

        setColumnCount(3);
        setRowCount(4);
        setMinimumWidth(LayoutParams.MATCH_PARENT);
        setMinimumHeight(LayoutParams.WRAP_CONTENT);

        setBackgroundColor(Color.parseColor("#dd000000"));
        //setBackgroundColor(Color.WHITE);

        Window window = ((Activity) mContext).getWindow();
        // WindowManager manager = (WindowManager)mActivity.getSystemService(Context.WINDOW_SERVICE);
        WindowManager manager = window.getWindowManager();
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        sWindowWidth = size.x;
        sWindowHeight = size.y / 5 * 4;
        //determineComponentSize();

        //Log.d("size",sWindowHeight+":"+sWindowWidth);


        //ビューの設定
        setFocusable(true);
        requestFocus();


        OnClickNumberListener listener = new OnClickNumberListener();
        for (int i = 0; i < 12; i++) {

            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setWidth(sWindowWidth / 3);
            textView.setHeight(sWindowHeight / 4 );
            textView.setTextSize(30);
            textView.setText(getTextDetail(i));
            textView.setOnClickListener(listener);
            textView.setId(getPanelIds(i));
            textView.setBackgroundResource(R.drawable.select_panel);
            textView.setClickable(true);
            textView.setFocusable(true);
            addView(textView);
        }


    }

    public String getTextDetail(int count){
        switch (count) {
            case 0:
                return "7";
            case 1:
                return "8";
            case 2:
                return "9";
            case 3:
                return "4";
            case 4:
                return "5";
            case 5:
                return "6";
            case 6:
                return "1";
            case 7:
                return "2";
            case 8:
                return "3";
            case 9:
                return "0";
            case 10:
                return "←";
            case 11:
                return "終了";
            default:
                return "";


        }

    }

    public int getPanelIds(int count){
        switch (count){

            case 0:
                return R.id.select_numberpanel_7;
            case 1:
                return R.id.select_numberpanel_8;
            case 2:
                return R.id.select_numberpanel_9;
            case 3:
                return R.id.select_numberpanel_4;
            case 4:
                return R.id.select_numberpanel_5;
            case 5:
                return R.id.select_numberpanel_6;
            case 6:
                return R.id.select_numberpanel_1;
            case 7:
                return R.id.select_numberpanel_2;
            case 8:
                return R.id.select_numberpanel_3;
            case 9:
                return R.id.select_numberpanel_0;
            case 10:
                return R.id.select_numberpanel_clear;
            case 11:
                return R.id.select_numberpanel_back;
            default:
                return R.id.select_numberpanel_back;



        }

    }


    public class OnClickNumberListener implements OnClickListener{
        @Override
        public void onClick(View view) {
            int num=11;
            switch (view.getId()){
                case R.id.select_numberpanel_7:
                    num=7;
                    break;
                case R.id.select_numberpanel_8:
                    num=8;
                    break;
                case R.id.select_numberpanel_9:
                    num=9;
                    break;
                case R.id.select_numberpanel_4:
                    num=4;
                    break;
                case R.id.select_numberpanel_5:
                    num=5;
                    break;
                case R.id.select_numberpanel_6:
                    num=6;
                    break;
                case R.id.select_numberpanel_1:
                    num=1;
                    break;
                case R.id.select_numberpanel_2:
                    num=2;
                    break;
                case R.id.select_numberpanel_3:
                    num=3;
                    break;
                case R.id.select_numberpanel_0:
                    num=0;
                    break;
                case R.id.select_numberpanel_clear:
                    num=10;
                    break;
                case R.id.select_numberpanel_back:
                    num=11;
                    break;




            }

            if(mContext instanceof onClickNumberPanelInterface){
                onClickNumberPanelInterface listener=(onClickNumberPanelInterface)mContext;
                listener.onClickNumber(num);
            }
        }
    }


    public interface onClickNumberPanelInterface{
        public void onClickNumber(int num);
    }


}
