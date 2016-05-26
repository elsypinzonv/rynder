package com.elsy.rynder.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.elsy.rynder.R;

public class ElementViewHolder extends RecyclerView.ViewHolder {

    ImageView img_element;
    TextView tx_name;
    TextView tx_description;
    TextView tx_price;



    public ElementViewHolder(View view) {
        super(view);
        img_element = (ImageView) view.findViewById(R.id.element);
        tx_name = (TextView) view.findViewById(R.id.name);
        tx_description = (TextView) view.findViewById(R.id.description);
        tx_price = (TextView) view.findViewById(R.id.price);
    }
}
