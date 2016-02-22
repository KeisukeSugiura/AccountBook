package com.example.AccountBook.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.AccountBook.R;

/**
 * Created by yoshida keisuke on 2015/04/06.
 */
public class InitialActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_initial);
        Button startButton = (Button)findViewById(R.id.startbutton_initialactivity);

        startButton.setOnClickListener(new StartButtonOnClickListener());
        Log.i("InitialActivity", "onCreate");

    }

    private void startActivity(){
        setResult(1);
        finish();
    }


    private class StartButtonOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            startActivity();
        }
    }
}