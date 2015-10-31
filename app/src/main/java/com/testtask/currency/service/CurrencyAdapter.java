package com.testtask.currency.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.testtask.currency.R;
import com.testtask.currency.domain.Currency;

import java.util.List;


public class CurrencyAdapter extends ArrayAdapter<Currency>{

    private  Context context;
    private List<Currency> objects;


    public CurrencyAdapter(Context context, int resource, List<Currency> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Currency currency = objects.get(position);

        LayoutInflater inflator =
                (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.list_item_currency, null);

        TextView tv1 = (TextView) view.findViewById(R.id.curName);
        tv1.setText(currency.getName());

        TextView tv2 = (TextView) view.findViewById(R.id.saleCoef);
        tv2.setText(String.valueOf(currency.getSaleCoef()));

        TextView tv3 = (TextView) view.findViewById(R.id.buyCoef);
        tv3.setText(String.valueOf(currency.getBuyCoef()));

        ImageView image = (ImageView) view.findViewById(R.id.imageView5);
        String imageName = currency.getName().toLowerCase();

        int res = context.getResources().getIdentifier(
                imageName, "drawable", context.getPackageName());

        image.setImageResource(res);

        return view;
    }
}
