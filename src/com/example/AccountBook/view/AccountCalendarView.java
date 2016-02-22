package com.example.AccountBook.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.AccountBook.R;
import com.example.AccountBook.model.DayAccountModel;
import com.example.AccountBook.module.MyCalendar;

import java.util.ArrayList;

/**
 * Created by yoshida keisuke on 2015/04/01.
 */
public class AccountCalendarView extends LinearLayout {

    private Context mContext;
    private TextView mYearMonthTitle;
    private GridLayout mDaysGridLayout;
    private ImageView mRightButton;
    private ImageView mLeftButton;
    private MyCalendar mCalendar;
    private View mColorItem;

    private LinearLayout mActButtonLinear;
    private LinearLayout mCallendarLinear[];

    public int mDay;
    public int mMonth;
    public int mYear;

    public ArrayList<DayAccountModel> mAccountList;

    private onDayItemClickListener mOnItemClickListener;

    private class ViewHolder {
        int itemNumber;
        TextView dayField;
        TextView accountField;
    }


    public AccountCalendarView(Context context) {
        super(context);
        mContext = context;
        initView();
        Log.i("sssss", "started");
        //setCalendar(2015, 12,1);
        Log.i("sssss", "started");
    }

    public AccountCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }


    /**
     * レイアウトの基本的な枠組みを設定する
     */
    public void initView() {

        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setOrientation(VERTICAL);

        mCalendar = MyCalendar.getInstance();

        mActButtonLinear = new LinearLayout(mContext);
        mActButtonLinear.setOrientation(HORIZONTAL);
        mRightButton = new ImageView(mContext);
        mRightButton.setBackgroundResource(R.drawable.select_panel);
        mRightButton.setAdjustViewBounds(true);
        mRightButton.setImageResource(R.drawable.rightbect);
        mRightButton.setOnClickListener(new OnClickItem());
        mRightButton.setId(R.id.month_right_button);
        mLeftButton = new ImageView(mContext);
        mLeftButton.setOnClickListener(new OnClickItem());
        mLeftButton.setId(R.id.month_left_button);
        mLeftButton.setAdjustViewBounds(true);
        mLeftButton.setBackgroundResource(R.drawable.select_panel);
        mLeftButton.setImageResource(R.drawable.leftbect);
        mYearMonthTitle = new TextView(mContext);
        mYearMonthTitle.setGravity(Gravity.CENTER);


        mActButtonLinear.addView(mLeftButton, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        mActButtonLinear.addView(mYearMonthTitle, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
        mActButtonLinear.addView(mRightButton, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

        this.addView(mActButtonLinear, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));

        //上のボタンとかの設定は以上
        mCallendarLinear = new LinearLayout[8];

        //入れ子の箱
        //縦
        LinearLayout linearLayout1 = new LinearLayout(mContext);
        linearLayout1.setOrientation(VERTICAL);


        LinearLayout linearLayout2 = new LinearLayout(mContext);
        for (int j = 0; j < 7; j++) {
            TextView textView = new TextView(mContext);
            textView.setText((CharSequence) mCalendar.DAY_OF_WEEK_JP[j]);
            textView.setGravity(Gravity.CENTER);
            linearLayout2.addView(textView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        }
        linearLayout2.setBackgroundColor(Color.parseColor("#002233"));
        linearLayout1.addView(linearLayout2, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));

        for (int j = 0; j < 6; j++) {
            mCallendarLinear[j] = new LinearLayout(mContext);
            for (int i = 0; i < 7; i++) {
                //TextView textView = new TextView(mContext);
                //textView.setText();
                //textView.setGravity(Gravity.CENTER);
                //mCallendarLinear[j].addView(textView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                LinearLayout layout = new LinearLayout(mContext);
                layout.setOrientation(VERTICAL);
                mCallendarLinear[j].addView(layout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            }
            linearLayout1.addView(mCallendarLinear[j], new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));

        }


        this.addView(linearLayout1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 5.0f));

        /*mDaysGridLayout = new GridLayout(mContext);
        mDaysGridLayout.setRowCount(6);
        mDaysGridLayout.setColumnCount(7);

        this.addView(mDaysGridLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 6.0f));*/


    }


    public void setCalendar(ArrayList<DayAccountModel> accountmodel, int year, int month, int day) {

        OnClickItem onClickItem = new OnClickItem();

        int daysCount = mCalendar.getDays(year, month);
        int startDayofWeek = mCalendar.getDayOfWeek(year, month, 0);

        int preDaysCount;


        mYearMonthTitle.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月" + String.valueOf(day) + "日");
        mDay = day;
        mYear = year;
        mMonth = month;

        /*if(month==1){
            preDaysCount=mCalendar.getDays(year-1,12);
        }else{
            preDaysCount=mCalendar.getDays(year,month-1);
        }*/

        // for (int i = 0; i < startDayofWeek; i++) {
        //前の月の処理
           /* TextView textView = new TextView(mContext);
            TextView textView1 = new TextView(mContext);
            textView.setText("");
            textView1.setText("");
            //textView.setTextColor(Color.rgb(50, 50, 50));

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            linearLayout.addView(textView1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            ViewHolder holder = new ViewHolder();
            holder.accountField = textView1;
            holder.dayField = textView;
            holder.itemNumber = -1;
            linearLayout.setTag(holder);
            linearLayout.setGravity(Gravity.CENTER);
            mDaysGridLayout.addView(linearLayout, 100, 100);*/
        // }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                ((LinearLayout) mCallendarLinear[i].getChildAt(j)).removeAllViews();
                ((LinearLayout) mCallendarLinear[i].getChildAt(j)).setOnClickListener(null);
            }
        }


        int count = startDayofWeek;
        int column = 0;
        for (int i = 0; i < daysCount; i++) {
            if (count < 7) {
                TextView dayText = new TextView(mContext);
                TextView priceText = new TextView(mContext);
                dayText.setText((CharSequence) String.valueOf(i + 1));
                int sum = getDaySum(accountmodel, i + 1);
                if (sum > 0) {
                    priceText.setText((CharSequence) String.valueOf(sum));
                } else {
                    priceText.setText("");
                }

                dayText.setGravity(Gravity.CENTER);
                dayText.setTextSize(15);
                priceText.setGravity(Gravity.CENTER);
                priceText.setTextSize(10);
                //mCallendarLinear[j].addView(textView, new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                ViewHolder holder = new ViewHolder();
                holder.accountField = priceText;
                holder.dayField = dayText;
                holder.itemNumber = i + 1;


                LinearLayout linearLayout = (LinearLayout) mCallendarLinear[column].getChildAt(count);
                linearLayout.addView(dayText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2.0f));
                linearLayout.addView(priceText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
                linearLayout.setTag(holder);
                linearLayout.setOnClickListener(onClickItem);
                if (i + 1 == day) {
                    linearLayout.setBackgroundColor(Color.argb(225, 0, 200, 200));
                    mColorItem = linearLayout;
                }

                count++;
            } else {
                count = 0;
                column++;
                i--;
            }

        }





/*

        for (int i = 0; i < daysCount; i++) {
            TextView textView = new TextView(mContext);
            TextView textView1 = new TextView(mContext);
            textView.setText(String.valueOf(i + 1));
            //TODO データベースを変更、追加 カレンダー上で使った金額がわかるよう設定
            //textView.setText(String.valueOf(getSum()));
            textView1.setText("aaaaaa");

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(VERTICAL);

            linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            linearLayout.addView(textView1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            ViewHolder holder = new ViewHolder();
            holder.accountField = textView1;
            holder.dayField = textView;
            holder.itemNumber = i;
            linearLayout.setTag(holder);
            linearLayout.setOnClickListener(onClickItem);
            mDaysGridLayout.addView(linearLayout, 100, 100);
        }

        for (int i = 0; i < 42 - daysCount - startDayofWeek; i++) {
            TextView textView = new TextView(mContext);
            TextView textView1 = new TextView(mContext);
            textView.setText("");
            textView1.setText("");
            //textView.setTextColor(Color.rgb(50, 50, 50));

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            linearLayout.addView(textView1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            ViewHolder holder = new ViewHolder();
            holder.accountField = textView1;
            holder.dayField = textView;
            holder.itemNumber = -1;
            linearLayout.setTag(holder);
            mDaysGridLayout.addView(linearLayout, 100, 100);
        }
*/
    }

    public void setDayText(int day) {
        mDay = day;
        mYearMonthTitle.setText(String.valueOf(mYear) + "年" + String.valueOf(mMonth) + "月" + String.valueOf(mDay) + "日");
    }


    public int getDaySum(ArrayList<DayAccountModel> model, int day) {
        int result = 0;
        for (DayAccountModel amodel : model) {
            if (amodel.getDay() == day) {
                result += amodel.getPrice();
            }
        }


        return result;
    }


    public void setDayItemClickListener(onDayItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    public interface onDayItemClickListener {
        void onItemClick(int i);

        void onNextClick();

        void onBackClick();
    }

    private class OnClickItem implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (mOnItemClickListener != null) {
                if (viewHolder != null) {
                    mColorItem.setBackgroundColor(Color.BLACK);
                    view.setBackgroundColor(Color.argb(225, 0, 200, 200));
                    mColorItem = view;
                    mOnItemClickListener.onItemClick(viewHolder.itemNumber);
                } else {
                    if (view.getId() == R.id.month_right_button) {
                        mColorItem.setBackgroundColor(Color.BLACK);
                        mOnItemClickListener.onNextClick();
                    } else if (view.getId() == R.id.month_left_button) {
                        mColorItem.setBackgroundColor(Color.BLACK);
                        mOnItemClickListener.onBackClick();
                    }

                }
            }
        }
    }


}
