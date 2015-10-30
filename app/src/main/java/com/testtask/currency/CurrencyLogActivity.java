package com.testtask.currency;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class CurrencyLogActivity extends AppCompatActivity {

    private TextView tvDisplayDate;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;

    private static final int DATE_DIALOG_ID = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_log);

        setCurrentDateOnView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void btnClickHandlerThirdActivity2(MenuItem item) {
        Intent thirdActivity = new Intent(this,  CurrencyGraphActivity.class);
        startActivity(thirdActivity);
    }

    public void btnClickHandlerSecondActivity2(MenuItem item) {
        Intent secondActivity = new Intent(this,  CurrencyLogActivity.class);
        startActivity(secondActivity);
    }

    public void btnClickHandlerFirstActivity2(MenuItem item) {
        Intent firstActivity = new Intent(this,  MainActivity.class);
        startActivity(firstActivity);
    }

    // display current date
    public void setCurrentDateOnView() {

        tvDisplayDate = (TextView) findViewById(R.id.tvDate);


        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

    }

    //setting date for query
    @SuppressWarnings("deprecation")
    public void setDate(View view) {

        showDialog(DATE_DIALOG_ID);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    };

    public void getCurrencyLogForSetDate(View view) {

    }
}
