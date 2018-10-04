package com.example.admin.myapplication;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.content.ContentValues.TAG;


public class Filter extends Activity implements View.OnClickListener {

  private TextView tvToDate, tvFromDate;

    private DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
    private int callerId = -1;

    public static final String DATE_FORMAT = "EEE, MMM d, yyyy";

    private Context ctx = this;

    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove everything from window
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_filter);


        tvToDate = (TextView) findViewById(R.id.startDateDisplay);
        tvToDate.setOnClickListener(this);

        tvFromDate = (TextView) findViewById(R.id.endDateDisplay);
        tvFromDate.setOnClickListener(this);


       TextView tvFilter = (TextView) findViewById(R.id.TextFilter);
       tvFilter.setPaintFlags(tvFilter.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //do something
                finish();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startDateDisplay:
                TextView et = (TextView) view;
                showDatePickerDialog(view.getId(), et.getText().toString().trim());
                break;
            case R.id.endDateDisplay:
                TextView et1 = (TextView) view;
                showDatePickerDialog(view.getId(), et1.getText().toString().trim());
        }

    }

    public void showDatePickerDialog(int callerId, String dateText)
    {
        this.callerId = callerId;
        Date date = null;

        try
        {
            if (dateText.equals(""))
                date = new Date();
            else
                date = dateFormatter.parse(dateText);
        } catch (Exception exp)

        {
            // In case of expense initializa date with new Date
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // calendar month 0-11
        int day = calendar.get(Calendar.DATE);
        // date picker initialization
        DatePickerDialog datePicker = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener()

        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                handleOnDateSet(year, month, day);

            }
        }, year, month, day);
        datePicker.setTitle("Pick a date");
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Confirm", datePicker);
        datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cancel button clicked
            }
        });
            datePicker.getDatePicker().setMaxDate(new Date().getTime());
        datePicker.show();
    }

    public void handleOnDateSet(int year, int month, int day) {
        Date date = new GregorianCalendar(year, month, day).getTime();
        String formatedDate = dateFormatter.format(date);

        switch (callerId) {
            case R.id.startDateDisplay:
                tvToDate.setText(formatedDate);

                break;
            case R.id.endDateDisplay:
                tvFromDate.setText(formatedDate);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.TextViewAboutInfo:
                Log.i(TAG, "start settings activity");
                Intent intent = new Intent(Filter.this, History.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


