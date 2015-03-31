package com.example.AccountBook.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yoshida keisuke on 2015/03/24.
 */
public class CompagerView extends ViewPager {

    private boolean mEnableSwipe;

    public CompagerView(Context context) {

        super(context);
        mEnableSwipe=true;
    }

    public CompagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mEnableSwipe=true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return super.onTouchEvent(ev);
        if(mEnableSwipe){
            return super.onTouchEvent(ev);
        }else{
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return super.onInterceptTouchEvent(ev);
        if(mEnableSwipe){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }

    public void setEnableSwipe(boolean enable){
        mEnableSwipe=enable;
    }


}
