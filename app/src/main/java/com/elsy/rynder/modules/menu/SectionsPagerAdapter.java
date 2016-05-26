package com.elsy.rynder.modules.menu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.elsy.rynder.domain.Menu;

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