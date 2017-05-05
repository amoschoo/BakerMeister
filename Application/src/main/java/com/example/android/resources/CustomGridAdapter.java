package com.example.android.resources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.slidingtabsbasic.R;

import java.util.ArrayList;
import java.util.List;


public class CustomGridAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public CustomGridAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler
    {
        ImageView image;
        TextView nametv;
        TextView amounttv;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        DataHandler handler;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_grid_layout,parent,false);
            handler = new DataHandler();
            handler.image = (ImageView) row.findViewById(R.id.image);
            handler.nametv = (TextView) row.findViewById(R.id.nameTV);
            handler.amounttv = (TextView) row.findViewById(R.id.amountTV);
            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler) row.getTag();
        }
        CustomGridDataProvider deviceDataProvider;
        deviceDataProvider = (CustomGridDataProvider) this.getItem(position);

        handler.image.setImageResource(deviceDataProvider.getImage_id());
        handler.nametv.setText(deviceDataProvider.getName_id());
        handler.amounttv.setText(deviceDataProvider.getAmount_id());

        return row;
    }
}

