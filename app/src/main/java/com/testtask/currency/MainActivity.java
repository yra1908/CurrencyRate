package com.testtask.currency;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.testtask.currency.domain.Currency;
import com.testtask.currency.service.CurrencyJSONParser;
import com.testtask.currency.service.HttpManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;
    List<MyTask> tasks;

    List<Currency> list;


    private final String pbAPI = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(list!=null){
            updateDisplay();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //not using yet
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


    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public void getCurrencyRate(View view) {

        if (isOnline()) {
            requestData(pbAPI);
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    protected void updateDisplay() {

        TextView resSaleUSD = (TextView) findViewById(R.id.saleUSD);
        TextView resBuyUSD = (TextView) findViewById(R.id.buyUSD);
        TextView resSaleEUR = (TextView) findViewById(R.id.saleEUR);
        TextView resBuyEUR = (TextView) findViewById(R.id.buyEUR);
        TextView resSaleRUR = (TextView) findViewById(R.id.saleRUR);
        TextView resBuyRUR = (TextView) findViewById(R.id.buyRUR);

        if (list != null){
            for (Currency cur:list) {
                if (cur.getName().equals("USD")){
                    resSaleUSD.append((String.valueOf(cur.getSaleCoef())));
                    resBuyUSD.append((String.valueOf(cur.getBuyCoef())));
                }
                if (cur.getName().equals("EUR")){
                    resSaleEUR.append((String.valueOf(cur.getSaleCoef())));
                    resBuyEUR.append((String.valueOf(cur.getBuyCoef())));
                }
                if (cur.getName().equals("RUR")){
                    resSaleRUR.append((String.valueOf(cur.getSaleCoef())));
                    resBuyRUR.append((String.valueOf(cur.getBuyCoef())));
                }
            }
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

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if (tasks.size()==0){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            list = CurrencyJSONParser.parseFeed(result);
            updateDisplay();

            tasks.remove(this);
            if (tasks.size()==0){
                pb.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }

    }
}
