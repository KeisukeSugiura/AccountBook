package com.example.AccountBook.activity;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.WindowManager;
import com.example.AccountBook.R;
import com.example.AccountBook.view.CalculateView;
import com.example.AccountBook.view.InputPriceView;
import com.example.AccountBook.view.SelectIconView;

public class MainActivity extends FragmentActivity implements DayAccountFragment.onDatasetChanged,
		MonthFragment.onSelectedDateChangeListener
		,CalculateView.CalculateViewListener
		,CalculateFragment.onSetValueListener
,SelectIconView.onIconItemClick
,InputPriceView.onClickNumberPanelInterface
{


	ComPagerFragment comPagerFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
      //  setContentView(new CalculateView(this));

        //アクションバーを消す
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


		//フラグメントマネージャーの起動、及びカリキュレーターの呼び出し
		
		FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		//元の計算機
		//CalculateFragment calFrag = new CalculateFragment();
		
		//改良型計算機
		//AdCalculateFragment calFrag = new AdCalculateFragment();
		
		
		//fragmentTransaction.add(R.id.fragment_content,calFrag);
/*
        CalculateFragment calculateFragment = new CalculateFragment();
        fragmentTransaction.add(R.id.fragment_content,calculateFragment,"calculator");
*/

        //MonthFragment fragment = new MonthFragment();
        //DayAccountFragment fragment = new DayAccountFragment();

        comPagerFragment = new ComPagerFragment();
        fragmentTransaction.add(R.id.fragment_content,comPagerFragment,"pager");
		fragmentTransaction.commit();
		/*testFragment fragment = new testFragment();
		fragmentTransaction.add(R.id.fragment_content,fragment,"pager");
		fragmentTransaction.commit();*/

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onChanged() {
		//DayAccountFragmentから通知
		//
		comPagerFragment.getmMonthFragment().changeDataset();
	}


	@Override
	public void onDateChanged(int year, int month, int day) {
		//MonthAccountから通知
		comPagerFragment.getmDayAccountFragment().changeDate(year, month, day);
	}

	@Override
	public void onSelectNumberItem(int item) {
		//calculateview
		comPagerFragment.getmCalculateFragment().selectNumberItem(item);

	}

	@Override
	public void onSelectShiftNumberItem(int item) {
		//calculateview
		comPagerFragment.getmCalculateFragment().selectShiftNumberItem(item);
	}

	@Override
	public void onSelectActionItem(int item) {
		//calculateview
		comPagerFragment.getmCalculateFragment().selectActionItem(item);
	}

	@Override
	public void onSelectEqual() {
		//calculateview
		comPagerFragment.getmCalculateFragment().selectEqual();
	}

	@Override
	public void onSelectShift(boolean flg) {
		//calculateview
		comPagerFragment.setSwipe(!flg);
	}

	@Override
	public void onSetValue(int num) {
		comPagerFragment.getmDayAccountFragment().setPrice(num);
	}

	@Override
	public void selectIcon(int select) {
		comPagerFragment.getmDayAccountFragment().setIcon(select);
	}

	@Override
	public void onClickNumber(int num) {
		comPagerFragment.getmDayAccountFragment().setNum(num);
	}
}
