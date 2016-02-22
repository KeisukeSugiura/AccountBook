package com.example.AccountBook.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by yoshida keisuke on 2015/04/06.
 */
public class StartingActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.i("startingActivity", "onCreate");
        if(getState() == 0){
            //èââÒãNìÆéû
            startActivityForResult(new Intent(this,InitialActivity.class),0);
        }else{
            startActivity(new Intent(this,MainActivity.class));
            finish();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 1) {
            setState(1);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            finish();
        }

    }

    private void setState(int state){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putInt("INITIALSTATE",state).commit();


    }

    private int getState(){
        int state;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        state = sharedPreferences.getInt("INITIALSTATE",0);
        return state;
    }



}