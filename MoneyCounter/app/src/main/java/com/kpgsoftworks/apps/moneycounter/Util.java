package com.kpgsoftworks.apps.moneycounter;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

public class Util extends Application {

    private static final String TAG = "mc.Util";

    private static final String NOTES = "Notes";
    private static final String COINS = "Coins";
    private static final String CHECKS = "Checks";

    private static final int TOTAL_CHECKS = 30;

    private static final Map<String, int[]> editTextIds = new HashMap<String, int[]>();

    private static Context context;
    private static Bundle savedInstanceState;

    public void onCreate() {
        super.onCreate();
        Util.context = getApplicationContext();
        Util.savedInstanceState = new Bundle();
        Util.editTextIds.put(NOTES, new int[]{
                R.id.note_1_quantity, R.id.note_2_quantity, R.id.note_5_quantity, R.id.note_10_quantity, R.id.note_20_quantity,
                R.id.note_50_quantity, R.id.note_100_quantity, R.id.note_500_quantity, R.id.note_1000_quantity,
                R.id.note_5000_quantity, R.id.note_10000_quantity}
        );
        Util.editTextIds.put(COINS, new int[]{
                R.id.coin_1_quantity, R.id.coin_5_quantity, R.id.coin_10_quantity, R.id.coin_25_quantity,
                R.id.coin_50_quantity, R.id.coin_100_quantity}
        );
        Util.editTextIds.put(CHECKS, new int[]{
                R.id.check_1_quantity,
                R.id.check_2_quantity,
                R.id.check_3_quantity,
                R.id.check_4_quantity,
                R.id.check_5_quantity,
                R.id.check_6_quantity,
                R.id.check_7_quantity,
                R.id.check_8_quantity,
                R.id.check_9_quantity,
                R.id.check_10_quantity,
                R.id.check_11_quantity,
                R.id.check_12_quantity,
                R.id.check_13_quantity,
                R.id.check_14_quantity,
                R.id.check_15_quantity,
                R.id.check_16_quantity,
                R.id.check_17_quantity,
                R.id.check_18_quantity,
                R.id.check_19_quantity,
                R.id.check_20_quantity,
                R.id.check_21_quantity,
                R.id.check_22_quantity,
                R.id.check_23_quantity,
                R.id.check_24_quantity,
                R.id.check_25_quantity,
                R.id.check_26_quantity,
                R.id.check_27_quantity,
                R.id.check_28_quantity,
                R.id.check_29_quantity,
                R.id.check_30_quantity}
        );
    }

    public static String getNOTES() {
        return NOTES;
    }

    public static String getCOINS() {
        return COINS;
    }

    public static String getCHECKS() {
        return CHECKS;
    }

    public static int getTotalChecks() {
        return TOTAL_CHECKS;
    }

    public static Map<String, int[]> getEditTextIds() {
        return editTextIds;
    }

    public static int[] getEditTextIds(String key) {
        return editTextIds.get(key);
    }

    public static Context getAppContext() {
        return context;
    }

    public static Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    public static void setSavedInstanceState(Bundle savedInstanceState) {
        Util.savedInstanceState = savedInstanceState;
    }

    public static void saveValues(LinearLayout layout, String key) {
        int[] ids = editTextIds.get(key);
        logDebug(TAG, "saveValues." + key + " ids: " + ids.length);
        if (layout != null) {
            for (int id : ids) {
                EditText editText = (EditText) layout.findViewById(id);
                if (editText != null) {
                    CharSequence userText = editText.getText();
                    String saveKey = key.toLowerCase() + editText.getTag().toString();
                    logDebug(TAG, saveKey + ": " + userText);
                    savedInstanceState.putCharSequence(saveKey, userText);
                } else {
                    logDebug(TAG, "saveValues." + key + "--editText null");
                }
            }
        } else {
            logDebug(TAG, "saveValues." + key + "--layout null");
        }
    }

    public static void restoreNotes(Bundle savedState) {
        logDebug(TAG, "restoreNotes");
        if (savedState != null && !savedState.isEmpty()) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View fragmentView = inflater.inflate(R.layout.fragment_notes, null, false);
            View layoutView = fragmentView.findViewById(R.id.notes_layout);
            if (layoutView instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) layoutView;
                for (int id : editTextIds.get(NOTES)) {
                    final EditText editText = (EditText) layout.findViewById(id);
                    CharSequence userText = savedState.getCharSequence("notesEditText" + id);
                    editText.setText(userText);
                }
            }
            logDebug(TAG, "Notes Restored");
        }
    }

    public static void logDebug(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }
}
