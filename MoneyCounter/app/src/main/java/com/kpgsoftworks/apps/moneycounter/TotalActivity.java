package com.kpgsoftworks.apps.moneycounter;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class TotalActivity extends AppCompatActivity {

    private static final String TAG = "mc.TotalActivity";
    private static final BigDecimal ZERO = new BigDecimal("0");

    private static Decimal grandTotal = new Decimal(ZERO.toString());
    private static Bundle mSavedInstanceState;
    private static LinearLayout totalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.logDebug(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSavedInstanceState = Util.getSavedInstanceState();
        totalLayout = (LinearLayout) findViewById(R.id.total_layout);

        totalNotes();
        totalCoins();
        totalChecks();
        totalAll();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void totalNotes() {
        Util.logDebug(TAG, "totalNotes");
        String notes = Util.getNOTES().toLowerCase();
        int notesTotal = 0;
        for (String key : mSavedInstanceState.keySet()) {
            if (key.contains(notes)) {
                Object entry = mSavedInstanceState.get(key);
                if (entry != null) {
                    String value = entry.toString();
                    int quantity = (!value.equals("")) ? Integer.parseInt(value) : 0;
                    Util.logDebug(TAG, "key: " + key + " quantity: " + quantity);
                    int noteValue = Integer.parseInt(key.replace(notes, ""));
                    if (quantity > 0) {
                        int total = quantity * noteValue;
                        showTotalEntry(key, Integer.toString(quantity), total);
                        notesTotal += total;
                    }
                }
            }
        }
        if (notesTotal > 0) {
            TextView textView = (TextView) totalLayout.findViewWithTag("notes_total");
            textView.setText(NumberFormat.getCurrencyInstance().format(notesTotal));
            TableRow tableRow = (TableRow) textView.getParent();
            tableRow.setVisibility(View.VISIBLE);

            textView = (TextView) totalLayout.findViewWithTag("notes_heading");
            tableRow = (TableRow) textView.getParent();
            tableRow.setVisibility(View.VISIBLE);

            TableLayout tableLayout = (TableLayout) tableRow.getParent();
            tableLayout.setVisibility(View.VISIBLE);
        }
        grandTotal.add(new BigDecimal(String.valueOf(notesTotal)));
    }

    private void totalCoins() {
        Util.logDebug(TAG, "totalCoins");
        String coins = Util.getCOINS().toLowerCase();
        Decimal coinsTotal = new Decimal(ZERO.toString());
        for (String key : mSavedInstanceState.keySet()) {
            if (key.contains(coins)) {
                Object entry = mSavedInstanceState.get(key);
                if (entry != null) {
                    String value = entry.toString();
                    int quantity = (!value.equals("")) ? Integer.parseInt(value) : 0;
                    Util.logDebug(TAG, "key: " + key + " quantity: " + quantity);
                    value = key.replace(coins, "");
                    BigDecimal coinValue = new BigDecimal((value.equals("100")) ? "1.00" : ("0." + value));
                    if (quantity > 0) {
                        BigDecimal total = coinValue.multiply(new BigDecimal(quantity));
                        showTotalEntry(key, Integer.toString(quantity), total);
                        coinsTotal.add(total);
                    }
                }
            }
        }
        if (coinsTotal.compareTo(ZERO) == 1) {
            TextView textView = (TextView) totalLayout.findViewWithTag("coins_total");
            textView.setText(NumberFormat.getCurrencyInstance().format(coinsTotal.getBigDecimal()));
            TableRow tableRow = (TableRow) textView.getParent();
            tableRow.setVisibility(View.VISIBLE);

            textView = (TextView) totalLayout.findViewWithTag("coins_heading");
            tableRow = (TableRow) textView.getParent();
            tableRow.setVisibility(View.VISIBLE);

            TableLayout tableLayout = (TableLayout) tableRow.getParent();
            tableLayout.setVisibility(View.VISIBLE);
        }
        grandTotal.add(coinsTotal);
    }

    private void totalChecks() {
        Util.logDebug(TAG, "totalChecks");
        String checks = Util.getCHECKS().toLowerCase();
        Decimal checksTotal = new Decimal(ZERO.toString());
        for (String key : mSavedInstanceState.keySet()) {
            if (key.contains(checks)) {
                Object entry = mSavedInstanceState.get(key);
                if (entry != null) {
                    String value = entry.toString();
                    value = value.equals("") ? "0" : value;
                    Util.logDebug(TAG, "key: " + key + " entry: " + value);
                    BigDecimal checkValue = new BigDecimal(value);
                    if (checkValue.compareTo(ZERO) == 1) {
                        showTotalEntry(key, checkValue);
                        checksTotal.add(checkValue);
                    }
                }
            }
        }
        if (checksTotal.compareTo(ZERO) == 1) {
            TextView textView = (TextView) totalLayout.findViewWithTag("checks_total");
            textView.setText(NumberFormat.getCurrencyInstance().format(checksTotal.getBigDecimal()));
            TableRow tableRow = (TableRow) textView.getParent();
            tableRow.setVisibility(View.VISIBLE);

            textView = (TextView) totalLayout.findViewWithTag("checks_heading");
            tableRow = (TableRow) textView.getParent();
            tableRow.setVisibility(View.VISIBLE);

            TableLayout tableLayout = (TableLayout) tableRow.getParent();
            tableLayout.setVisibility(View.VISIBLE);
        }
        grandTotal.add(checksTotal);
    }

    private void totalAll() {
        Util.logDebug(TAG, "totalAll: " + grandTotal);
        if (grandTotal.compareTo(ZERO) == 0) {
            TextView textView = (TextView) totalLayout.findViewById(R.id.no_entry);
            textView.setVisibility(View.VISIBLE);
        } else {
            TextView textView = (TextView) totalLayout.findViewWithTag("grand_total");
            textView.setText(NumberFormat.getCurrencyInstance().format(grandTotal.getBigDecimal()));
            TableRow tableRow = (TableRow) textView.getParent();
            tableRow.setVisibility(View.VISIBLE);
        }
    }

    private void showTotalEntry(String tag, Object total) {
        String totalOut = NumberFormat.getCurrencyInstance().format(total);
        Util.logDebug(TAG, "showTotalEntry: " + tag + " = " + totalOut);
        TextView textView = (TextView) totalLayout.findViewWithTag(tag);
        textView.setText(totalOut);
        TableRow tableRow = (TableRow) textView.getParent();
        tableRow.setVisibility(View.VISIBLE);
    }

    private void showTotalEntry(String tag, String quantity, Object total) {
        String tagQ = tag + "_q";
        Util.logDebug(TAG, "showTotalEntry: " + tagQ + " = " + quantity);
        TextView textView = (TextView) totalLayout.findViewWithTag(tagQ);
        textView.setText(quantity);
        showTotalEntry(tag, total);
    }
}
