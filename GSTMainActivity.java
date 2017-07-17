package com.tranetech.gandhinagar.plshah.gst.salesbillplshah;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.badoualy.stepperindicator.StepperIndicator;
import com.tranetech.gandhinagar.plshah.R;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabFiveSalesCalc;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabFourConsignee;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabOneInvoiceInfo;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabSixTaxes;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabThreeReceiver;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabTwoTransportInfo;

import java.util.ArrayList;
import java.util.List;

public class GSTMainActivity extends FragmentActivity {

    public static ViewPager pager;
    String Phone_Number;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gst_main);

        // to hide soft keyboard until user interaction
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        Intent PhoneNumber = getIntent();

        Phone_Number = PhoneNumber.getStringExtra("phone");


        pager = (ViewPager) findViewById(R.id.pager);
        assert pager != null;
        setupViewPager(pager);

        final View touchView = findViewById(R.id.pager);
        touchView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        StepperIndicator indicator = (StepperIndicator) findViewById(R.id.stepper_indicator);
        assert indicator != null;
        // We keep last page for a "finishing" page
        indicator.setViewPager(pager, true);
        indicator.setStepCount(6);
//        indicator.setElevation((float) 9.0);

    }


    public void setupViewPager(ViewPager upViewPager) {
        try {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

            adapter.addFragment(new TabOneInvoiceInfo());
            adapter.addFragment(new TabTwoTransportInfo());
            adapter.addFragment(new TabThreeReceiver());
            adapter.addFragment(new TabFourConsignee());
            adapter.addFragment(new TabFiveSalesCalc());
            adapter.addFragment(new TabSixTaxes(Phone_Number));


            upViewPager.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }

    public static void setfrgmnt(int pos) {
        pager.setCurrentItem(pos);
    }


}
