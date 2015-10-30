package com.testtask.currency.domain;

/**
 * Created by konstr on 30.10.2015.
 */
public class Currency {

    private String name;
    private String shortName;
    private double buyCoef;
    private double saleCoef;
    private double saleCoefNB;
    private double buyCoefNB;

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public double getBuyCoef() {
        return buyCoef;
    }

    public double getSaleCoef() {
        return saleCoef;
    }

    public double getSaleCoefNB() {
        return saleCoefNB;
    }

    public double getBuyCoefNB() {
        return buyCoefNB;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setBuyCoef(double buyCoef) {
        this.buyCoef = buyCoef;
    }

    public void setSaleCoef(double saleCoef) {
        this.saleCoef = saleCoef;
    }

    public void setSaleCoefNB(double saleCoefNB) {
        this.saleCoefNB = saleCoefNB;
    }

    public void setBuyCoefNB(double buyCoefNB) {
        this.buyCoefNB = buyCoefNB;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", buyCoef=" + buyCoef +
                ", saleCoef=" + saleCoef +
                ", saleCoefNB=" + saleCoefNB +
                ", buyCoefNB=" + buyCoefNB +
                '}';
    }
}
