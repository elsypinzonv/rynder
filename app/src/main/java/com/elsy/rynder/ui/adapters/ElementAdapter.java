package com.elsy.rynder.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elsy.rynder.R;
import com.elsy.rynder.domain.Element;
import com.elsy.rynder.utils.ResourceCompatMethod;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ElementAdapter extends RecyclerView.Adapter<ElementViewHolder> {

    private Context mContext;
    private List<Element> mElements;
    private ResourceCompatMethod rscCompat;

    public ElementAdapter(
            Context context,
            ArrayList<Element> elementList
    ) {
        mElements = elementList;
        mContext = context;
        rscCompat = new ResourceCompatMethod(context);
    }

    @Override
    public ElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_section_element, parent, false);
        ElementViewHolder vh = new ElementViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ElementViewHolder holder, int position) {

        final Element element = mElements.get(position);
        holder.tx_price.setText(element.getPrice()+" "+element.getCurrency());
        holder.tx_name.setText(element.getName());
        holder.tx_description.setText(element.getDescription());

        Picasso.with(mContext)
                .load(element.getImage())
                .placeholder(R.drawable.restaurant_image_placeholder)
                .error(R.drawable.restaurant_image_error)
                .into(holder.img_element);
     }


    public Element getItem(int position) {
        return mElements.get(position);
    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }


}
