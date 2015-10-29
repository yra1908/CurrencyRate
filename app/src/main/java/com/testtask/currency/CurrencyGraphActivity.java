package com.testtask.currency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class CurrencyGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_graph);
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
}
