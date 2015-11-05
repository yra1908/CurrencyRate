package com.testtask.currency;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.testtask.currency.domain.Currency;
import com.testtask.currency.service.CurrencyJSONParser;
import com.testtask.currency.service.HttpManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class CurrencyGraphActivity extends AppCompatActivity {

    private static final String MINFIN_USD_API = "http://minfin.com.ua/data/currency/ib/usd.ib.stock.json";
    private static final String MINFIN_EUR_API = "http://minfin.com.ua/data/currency/ib/eur.ib.stock.json";
    private static final String MINFIN_RUB_API = "http://minfin.com.ua/data/currency/ib/rub.ib.stock.json";
    private static final String USD = "USD";
    private static final String EUR = "EUR";
    private static final String RUB = "RUB";

    private static final int DATE_DIALOG_ID_1 = 999;
    private static final int DATE_DIALOG_ID_2 = 998;

    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    private String currencyType;
    private TreeMap<Date, Currency> mapData;

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
        /*graph.setVisibility(View.INVISIBLE);*/

        //Spinner (dropdown)
        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        String[] items = new String[]{USD, EUR, RUB};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                currencyType = (String) parent.getItemAtPosition(position);
                getMinfinData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currencyType = USD;
                getMinfinData();
            }
        });
    }

    private void getMinfinData() {
        if (isOnline()) {
            if (currencyType.equals(USD)) {
                requestData(MINFIN_USD_API);
            }
            if (currencyType.equals(EUR)) {
                requestData(MINFIN_EUR_API);
            }
            if (currencyType.equals(RUB)) {
                requestData(MINFIN_RUB_API);
            }
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (series != null) {
            graph.addSeries(series);
            graph.getViewport().setXAxisBoundsManual(true);
        }
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
        Intent thirdActivity = new Intent(this, CurrencyGraphActivity.class);
        startActivity(thirdActivity);
    }

    public void btnClickHandlerSecondActivity2(MenuItem item) {
        Intent secondActivity = new Intent(this, CurrencyLogActivity.class);
        startActivity(secondActivity);
    }

    public void btnClickHandlerFirstActivity2(MenuItem item) {
        Intent firstActivity = new Intent(this, MainActivity.class);
        startActivity(firstActivity);
    }

    public void buildGraph(View view) {

        if (!isOnline()) {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
            return;
        }
        Date startDate = null;
        Date endDate = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
            startDate = df.parse(startYear + "-" + startMonth + "-" + startDay);
            endDate = df.parse(endYear + "-" + endMonth + "-" + endDay);
            Log.d("map dates startDate-", startDate.toString());
            Log.d("map dates endDate-", endDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(startDate.after(endDate)){
            Toast.makeText(this, "Date input Error. Start Date can't be after End Date.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        SortedMap<Date, Currency> mapDataToShow = mapData.subMap(startDate, endDate);

        int count = mapDataToShow.size();

        DataPoint[] values = new DataPoint[count];
        int i = 0;

        for (Map.Entry<Date, Currency> entry : mapDataToShow.entrySet()) {
            Date d = entry.getKey();
            double v = entry.getValue().getBuyCoef();
            DataPoint temp = new DataPoint(d, v);
            values[i] = temp;
            i++;
        }

        series = new LineGraphSeries<DataPoint>(values);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(mapDataToShow.firstKey().getTime());
        graph.getViewport().setMaxX(mapDataToShow.lastKey().getTime());
        graph.getViewport().setXAxisBoundsManual(true);


        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);

        if (currencyType.equals(EUR)) {
            series.setColor(Color.RED);
        }
        if (currencyType.equals(RUB)) {
            series.setColor(Color.BLACK);
        }
        graph.addSeries(series);


    }

    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        endYear = c.get(Calendar.YEAR);
        endMonth = c.get(Calendar.MONTH)+1;
        endDay = c.get(Calendar.DAY_OF_MONTH);
        startDay = endDay;
        startMonth = endMonth;
        startYear = endYear - 1;

        tvDisplayEndDate.setText(new StringBuilder()
                .append(endMonth).append("-").append(endDay).append("-")
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
                /*//DatePicker theme
                return new DatePickerDialog(this, R.style.datePickerTheme, datePickerListener,*/
                return new DatePickerDialog(this, datePickerListener,
                        startYear, startMonth-1, startDay);
        }
        switch (id) {
            case DATE_DIALOG_ID_2:
               /* return new DatePickerDialog(this, R.style.datePickerTheme, datePickerListener2,*/
                return new DatePickerDialog(this, datePickerListener2,
                        endYear, endMonth-1, endDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    startYear = selectedYear;
                    startMonth = selectedMonth+1;
                    startDay = selectedDay;

                    tvDisplayStartDate.setText(new StringBuilder().append(startMonth)
                            .append("-").append(startDay).append("-").append(startYear)
                            .append(" "));

                }
            };

    private DatePickerDialog.OnDateSetListener datePickerListener2 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    endYear = selectedYear;
                    endMonth = selectedMonth+1;
                    endDay = selectedDay;

                    tvDisplayEndDate.setText(new StringBuilder().append(endMonth)
                            .append("-").append(endDay).append("-").append(endYear)
                            .append(" "));

                }
            };

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);
            Log.d("map content -", content);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            mapData = CurrencyJSONParser.parseMinfinFeed(result);

        }

    }
}
