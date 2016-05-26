package com.elsy.rynder.modules.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.elsy.rynder.R;
import com.elsy.rynder.domain.Section;
import com.elsy.rynder.ui.adapters.ElementAdapter;


public  class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION = "Section";
        private TextView tx_section;
        private Section section;
        private RecyclerView recy_elements;
        private ElementAdapter mAdapter;

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber, Section section) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putSerializable(ARG_SECTION, section);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

            section = (Section) getArguments().getSerializable(ARG_SECTION);

            tx_section = (TextView) rootView.findViewById(R.id.section);
            recy_elements = (RecyclerView) rootView.findViewById(R.id.elements);

            tx_section.setText(section.getName());
            mAdapter=new ElementAdapter(getContext(),section.getElements());
            recy_elements.setAdapter(mAdapter);
            recy_elements.setHasFixedSize(true);
            recy_elements.setLayoutManager(new LinearLayoutManager(getActivity()));

            return rootView;
        }
    }