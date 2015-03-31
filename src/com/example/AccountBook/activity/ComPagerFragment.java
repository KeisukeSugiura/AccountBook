package com.example.AccountBook.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.AccountBook.R;
import com.example.AccountBook.model.ComPagerFragmentAdapter;
import com.example.AccountBook.view.CompagerView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yoshida keisuke on 2015/03/17.
 */
public class ComPagerFragment extends Fragment{

    View mView;
    CompagerView mViewPager;
    ArrayList<Fragment> mFragmentList;
    ComPagerFragmentAdapter mPagerAdapter;
    DayAccountFragment mDayAccountFragment;
    MonthFragment mMonthFragment;
    CalculateFragment mCalculateFragment;
    int mYear;
    int mMonth;
    int mDay;

    public MonthFragment getmMonthFragment() {
        return mMonthFragment;
    }

    public DayAccountFragment getmDayAccountFragment() {
        return mDayAccountFragment;
    }

    public CalculateFragment getmCalculateFragment() {
        return mCalculateFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.layout_compager_fragment,container,false);
        mViewPager = (CompagerView)mView.findViewById(R.id.compager_view);
        mPagerAdapter = new ComPagerFragmentAdapter(getFragmentManager(),mFragmentList);
        mViewPager.setOnPageChangeListener(new OnComPageChangeListener());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(1);

        setRetainInstance(true);
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH)+1;
        mDay=calendar.get(Calendar.DATE);
        mFragmentList = new ArrayList<Fragment>();

        Log.i("time",String.valueOf(mYear));
        Log.i("time",String.valueOf(mMonth));
        Log.i("time",String.valueOf(mDay));

        Bundle bundle = new Bundle();
        bundle.putInt("Year",mYear);
        bundle.putInt("Month",mMonth);
        bundle.putInt("Day",mDay);

        mMonthFragment = new MonthFragment();
        mDayAccountFragment = new DayAccountFragment();
        mCalculateFragment = new CalculateFragment();

        mDayAccountFragment.setArguments(bundle);
        mMonthFragment.setArguments(bundle);

       mFragmentList.add(mMonthFragment);
        mFragmentList.add(mDayAccountFragment);
        mFragmentList.add(mCalculateFragment);



    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setSwipe(boolean flg){
        mViewPager.setEnableSwipe(flg);
    }


    /**
     * このクラスだけのリスナー
     */
    private class OnComPageChangeListener implements ViewPager.OnPageChangeListener{

        int state = ViewPager.SCROLL_STATE_IDLE;
        int count =0;
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            if(i == 1 || i==2){
                if(state == ViewPager.SCROLL_STATE_DRAGGING && count<1){
                    mCalculateFragment.initView();
                    count++;
                }else{
                    count=0;
                }
                /*else if(state == ViewPager.SCROLL_STATE_SETTLING){
                    count = 0;
                }*/
            }
        }

        @Override
        public void onPageSelected(int i) {


        }

        @Override
        public void onPageScrollStateChanged(int i) {
                state =i;

        }

    }


}