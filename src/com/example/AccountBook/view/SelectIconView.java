package com.example.AccountBook.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.*;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.AccountBook.R;

/**
 * Created by yoshida keisuke on 2015/03/27.
 */
public class SelectIconView extends GridLayout {

    private Context mContext;
    private int sWindowWidth;
    private int sWindowHeight;

    public SelectIconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SelectIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectIconView(Context context) {
        super(context);
        mContext = context;
        initView();
    }


    public void initView() {

        setColumnCount(3);
        setRowCount(3);
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


        OnClickItemListener listener = new OnClickItemListener();
        for (int i = 0; i < 9; i++) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);


            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(getDrawableId(i));
            imageView.setMaxWidth(sWindowWidth / 3);
            imageView.setMaxHeight(sWindowWidth / 3);
            imageView.setAdjustViewBounds(true);


            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setWidth(sWindowWidth / 3);
            textView.setText(getTextDetail(i));

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.setId(getLayoutid(i));
            linearLayout.setOnClickListener(listener);
            linearLayout.setBackgroundResource(R.drawable.select_panel);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setMinimumHeight(sWindowHeight/3);

            linearLayout.setClickable(true);
            linearLayout.setFocusable(true);
            addView(linearLayout);
        }


    }


    public static int getDrawableId(int count) {
        switch (count) {
            case 0:
                return R.drawable.debu;

            case 1:
                return R.drawable.katei;
            case 2:
                return R.drawable.skater;
            case 3:
                return R.drawable.campfire;
            case 4:
                return R.drawable.party;
            case 5:
                return R.drawable.kirakira;
            case 6:
                return R.drawable.shopping;
            case 7:
                return R.drawable.akuma;
            case 8:
                return R.drawable.hatena;
            default:
                return R.drawable.hatena;

        }

    }

    public int getLayoutid(int count) {
        switch (count) {
            case 0:
                return R.id.select_icon_eat;

            case 1:
                return R.id.select_icon_life;
            case 2:
                return R.id.select_icon_trans;
            case 3:
                return R.id.select_icon_play;
            case 4:
                return R.id.select_icon_conf;
            case 5:
                return R.id.select_icon_up;
            case 6:
                return R.id.select_icon_hobby;
            case 7:
                return R.id.select_icon_drag;
            case 8:
                return R.id.select_icon_others;
            default:
                return R.id.select_icon_others;

        }
    }

    public String getTextDetail(int count) {
        switch (count) {
            case 0:
                return "食事";

            case 1:
                return "生活";
            case 2:
                return "交通";
            case 3:
                return "遊び";
            case 4:
                return "交際";
            case 5:
                return "自分磨き";
            case 6:
                return "趣味";
            case 7:
                return "嗜好品";
            case 8:
                return "その他";
            default:
                return "";


        }
    }

    public class OnClickItemListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            int select=9;
            switch (view.getId()) {
                case R.id.select_icon_eat:
                    select=0;
                    break;
                case R.id.select_icon_life:
                    select=1;
                    break;
                case R.id.select_icon_trans:
                    select=2;
                    break;
                case R.id.select_icon_play:
                    select=3;
                    break;
                case R.id.select_icon_conf:
                    select=4;
                    break;
                case R.id.select_icon_up:
                    select=5;
                    break;
                case R.id.select_icon_hobby:
                    select=6;
                    break;
                case R.id.select_icon_drag:
                    select=7;
                    break;
                case R.id.select_icon_others:
                    select=8;
                    break;
                default:

            }
            if(mContext instanceof onIconItemClick){
                onIconItemClick listener = (onIconItemClick)mContext;
                listener.selectIcon(select);
            }
        }
    }


    public interface onIconItemClick{
        public void selectIcon(int select);
    }

}
