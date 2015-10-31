package com.testtask.currency;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class CurrencyGraphActivity extends AppCompatActivity {

    private static final int DATE_DIALOG_ID_1 = 999;
    private static final int DATE_DIALOG_ID_2 = 998;

    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;

    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private TextView tvDisplayStartDate;
    private TextView tvDisplayEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_graph);

        tvDisplayStartDate = (TextView) findViewById(R.id.startDateRes);
        tvDisplayEndDate = (TextView) findViewById(R.id.endDateRes);
        setCurrentDateOnView();

        graph = (GraphView) findViewById(R.id.graph);
        fillStructureGraph();
        /*graph.setVisibility(View.INVISIBLE);*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(series!=null){
            graph.addSeries(series);
            graph.getViewport().setXAxisBoundsManual(true);
        }
    }

    //Temporary stub. Need working API for curency change data.
    private void fillStructureGraph() {

        // generate Dates //need API for getting them
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();

        series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(d1, 20),
                new DataPoint(d2, 25),
                new DataPoint(d3, 23),
                new DataPoint(d4, 25),
                new DataPoint(d5, 21)
        });

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d5.getTime());
        graph.getViewport().setXAxisBoundsManual(true);


        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_currency_graph) {
            return true;
        }

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

    public void buildGraph(View view) {
        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
    }

    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        endYear = c.get(Calendar.YEAR);
        endMonth = c.get(Calendar.MONTH);
        endDay = c.get(Calendar.DAY_OF_MONTH);
        startDay=endDay;
        startMonth=endMonth;
        startYear=endYear-1;

        tvDisplayEndDate.setText(new StringBuilder()
                .append(endMonth + 1).append("-").append(endDay).append("-")
                .append(endYear).append(" "));

        tvDisplayStartDate.setText(new StringBuilder()
                .append(startMonth).append("-").append(startDay).append("-")
                .append(startYear).append(" "));

    }


    @SuppressWarnings("deprecation")
    public void setStartDate(View view) {
        showDialog(DATE_DIALOG_ID_1);
    }

    @SuppressWarnings("deprecation")
    public void setEndDate(View view) {
        showDialog(DATE_DIALOG_ID_2);
    }
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID_1:
                return new DatePickerDialog(this, R.style.datePickerTheme, datePickerListener,
                        startYear, startMonth, startDay);
        }switch (id) {
            case DATE_DIALOG_ID_2:
                return new DatePickerDialog(this, R.style.datePickerTheme, datePickerListener2,
                        endYear, endMonth, endDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    startYear = selectedYear;
                    startMonth = selectedMonth;
                    startDay = selectedDay;

                    tvDisplayStartDate.setText(new StringBuilder().append(startMonth + 1)
                            .append("-").append(startDay).append("-").append(startYear)
                            .append(" "));

                }
            };

    private DatePickerDialog.OnDateSetListener datePickerListener2 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    endYear = selectedYear;
                    endMonth = selectedMonth;
                    endDay = selectedDay;

                    tvDisplayEndDate.setText(new StringBuilder().append(endMonth + 1)
                            .append("-").append(endDay).append("-").append(endYear)
                            .append(" "));

                }
            };
}
