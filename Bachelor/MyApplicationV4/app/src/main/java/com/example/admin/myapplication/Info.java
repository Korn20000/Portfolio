package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 21/11/2017.
 */

public class Info extends Activity {

    private Button btOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove everything from window
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set the layout
        setContentView(R.layout.activity_info);
        //set the button


        btOk = (Button) findViewById(R.id.buttonOkNote);
        btOk.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //do something
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.TextViewAboutInfo:
                Log.i(TAG, "start settings activity");
                Intent intent = new Intent(Info.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


