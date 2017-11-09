package com.kpgsoftworks.apps.moneycounter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kpgsoftworks.apps.tabsswipe.adapter.TabsPagerAdapter;

import layout.ChecksFragment;
import layout.CoinsFragment;
import layout.NotesFragment;

public class MainActivity extends AppCompatActivity
        implements NotesFragment.OnFragmentInteractionListener, CoinsFragment.OnFragmentInteractionListener, ChecksFragment.OnFragmentInteractionListener {

    private static final String TAG = "mc.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(Util.getNOTES()));
        tabLayout.addTab(tabLayout.newTab().setText(Util.getCOINS()));
        tabLayout.addTab(tabLayout.newTab().setText(Util.getCHECKS()));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void showCheckEntry() {
        Util.logDebug(TAG, "showCheckEntry");
        int totalChecks = Util.getTotalChecks();
        LinearLayout layout = (LinearLayout) findViewById(R.id.checks_layout);
        int count = (layout != null) ? totalChecks : 0;
        int shown = 1;
        for (int i = 0; i < count; i++) {
            View rView = layout.getChildAt(i);
            if (rView instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) rView;
                if (rl.getVisibility() == View.GONE) {
                    rl.setVisibility(View.VISIBLE);
                    // Set focus on new entry
                    EditText editText = (EditText) rl.getChildAt(0);
                    editText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Util.getAppContext().INPUT_METHOD_SERVICE
                    );
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    shown = i + 1;
                    break;
                }
            }
        }
        // Change Buttons
        if (shown == totalChecks) {
            View lView = layout.getChildAt(totalChecks);
            if (lView instanceof LinearLayout) {
                LinearLayout ll = (LinearLayout) lView;
                // Hide Add Button
                Button button = (Button) ll.getChildAt(0);
                button.setVisibility(View.GONE);
                // Enlarge Total Button
                button = (Button) ll.getChildAt(1);
                ViewGroup.LayoutParams params = button.getLayoutParams();
                params.width = layout.getWidth();
                button.setLayoutParams(params);
            }
        }
    }

    /** Called when the user clicks the Total button */
    public void openTotal(View view) {
        Util.logDebug(TAG, "openTotal");
        startActivity(new Intent(this, TotalActivity.class));
    }

    /** Called when the user clicks the Add button */
    public void addCheck(View view) {
        showCheckEntry();
    }
}