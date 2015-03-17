package com.example.FunctionCalculator;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

public class MainActivity extends Activity {

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
		
		FragmentManager fragmentManager = getFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		//元の計算機
		//CalculateFragment calFrag = new CalculateFragment();
		
		//改良型計算機
		//AdCalculateFragment calFrag = new AdCalculateFragment();
		
		
		//fragmentTransaction.add(R.id.fragment_content,calFrag);

        CalculateFragment calculateFragment = new CalculateFragment();

        fragmentTransaction.add(R.id.fragment_content,calculateFragment,"calculator");

		fragmentTransaction.commit();
		
	
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
