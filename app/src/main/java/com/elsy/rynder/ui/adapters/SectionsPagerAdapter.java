package com.elsy.rynder.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elsy.rynder.domain.Menu;
import com.elsy.rynder.ui.fragments.PlaceholderFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Menu menu;
        private Context context;

        public SectionsPagerAdapter(FragmentManager fm, Menu menu, Context context) {
            super(fm);
            this.menu = menu;
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position, menu.getSections().get(position));
        }

        @Override
        public int getCount() {
            return menu.getSections().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return menu.getSections().get(position).getName();
        }
    }