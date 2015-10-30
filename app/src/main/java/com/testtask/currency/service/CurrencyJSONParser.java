package com.testtask.currency.service;

import android.util.Log;

import com.testtask.currency.domain.Currency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by konstr on 30.10.2015.
 */
public class CurrencyJSONParser {

    public static List<Currency> parseFeed(String content){

        try {
            JSONArray ar = new JSONArray(content);
            List<Currency> list = new ArrayList<>();

            for (int i=0; i<ar.length(); i++){

                JSONObject obj = ar.getJSONObject(i);
                Currency currency = new Currency();

                currency.setName(obj.getString("ccy"));
                currency.setSaleCoef(obj.getDouble("sale"));
                currency.setBuyCoef(obj.getDouble("buy"));

                list.add(currency);
            }

            return list;

        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }


    }

    public static List<Currency> parseLogFeed(String content) {

        try {

            JSONObject jsonObject = new JSONObject(content);
            JSONArray ar = jsonObject.getJSONArray("exchangeRate");
            Log.d("Parsing ar -", ar.toString());
            List<Currency> list = new ArrayList<>();

            for (int i=0; i<ar.length(); i++){

                JSONObject obj = ar.getJSONObject(i);

                if(obj.getString("currency").equals("EUR") ||
                        obj.getString("currency").equals("USD") ||
                        obj.getString("currency").equals("GBP") ||
                        obj.getString("currency").equals("PLZ") ||
                        obj.getString("currency").equals("RUB") ||
                        obj.getString("currency").equals("CAD") ||
                        obj.getString("currency").equals("CHF") ){

                    Currency currency = new Currency();

                    currency.setName(obj.getString("currency"));
                    currency.setShortName(obj.getString("currency"));
                    currency.setSaleCoef(obj.getDouble("saleRate"));
                    currency.setBuyCoef(obj.getDouble("purchaseRate"));
                    currency.setSaleCoefNB(obj.getDouble("saleRateNB"));
                    currency.setBuyCoefNB(obj.getDouble("purchaseRateNB"));

                    list.add(currency);
                }
            }

            return list;

        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
