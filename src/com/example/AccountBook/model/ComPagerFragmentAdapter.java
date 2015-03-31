package com.example.AccountBook.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by yoshida keisuke on 2015/03/21.
 */
public class ComPagerFragmentAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mComList;

    public ComPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        mComList = new ArrayList<Fragment>();
    }

    public ComPagerFragmentAdapter(FragmentManager fm,ArrayList<Fragment> fragments){
        super(fm);
        mComList = fragments;
    }


    @Override
    public Fragment getItem(int i) {
        Fragment fragment = mComList.get(i);
        return fragment;
    }

    @Override
    public int getCount() {
        return mComList.size();
    }

    public void addFragment(Fragment fragment){

        mComList.add(fragment);
    }


}
