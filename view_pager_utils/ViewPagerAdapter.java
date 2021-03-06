package com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pager_utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabFourConsignee;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabOneInvoiceInfo;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabSixTaxes;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabThreeReceiver;
import com.tranetech.gandhinagar.plshah.gst.salesbillplshah.view_pagers_tabs.TabTwoTransportInfo;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0)                   // if the position is 0 we are returning the First tab
        {
            TabOneInvoiceInfo tab1 = new TabOneInvoiceInfo();
            return tab1;
        } else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            TabTwoTransportInfo tab2 = new TabTwoTransportInfo();
            return tab2;
        } else if (position == 2) {
            TabThreeReceiver tab3 = new TabThreeReceiver();
            return tab3;
        } else if (position == 3)           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            TabFourConsignee tab4 = new TabFourConsignee();
            return tab4;
        } else {
            TabSixTaxes tab5 = new TabSixTaxes();
            return tab5;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}