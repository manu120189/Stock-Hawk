package com.sam_chordas.android.stockhawk.service;

public class HistoricalQuote {
    private String symbol;
    private String date;
    private String value;

    public HistoricalQuote(String symbol, String date, String value) {
        this.symbol = symbol;
        this.date = date;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
