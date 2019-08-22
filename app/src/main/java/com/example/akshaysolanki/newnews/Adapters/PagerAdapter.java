package com.example.akshaysolanki.newnews.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.akshaysolanki.newnews.Fragments.NewsIN;
import com.example.akshaysolanki.newnews.Fragments.NewsUS;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                NewsIN newsIN = new NewsIN();
                return newsIN;

            case 1:
                NewsUS newsUS = new NewsUS();
                return newsUS;

            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
