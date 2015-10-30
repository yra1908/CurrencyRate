package com.testtask.currency.service;

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
}
