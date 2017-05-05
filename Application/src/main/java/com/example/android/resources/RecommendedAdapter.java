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

/**
 * Created by Amos on 31/10/15.
 */
public class RecommendedAdapter extends ArrayAdapter {
    DataHandler handler;
    List list = new ArrayList<>();
    public RecommendedAdapter(Context context, int resource) {
        super(context, resource);
    }


    static class DataHandler
    {
        ImageView Picture;
        TextView category;
        TextView level;
        TextView recipename;
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
        row=convertView;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row  = inflater.inflate(R.layout.row_layout,parent,false);
            handler=new DataHandler();
            handler.Picture=(ImageView)row.findViewById(R.id.picture);
            handler.category=(TextView)row.findViewById(R.id.category);
            handler.level=(TextView)row.findViewById(R.id.level);
            handler.recipename=(TextView)row.findViewById(R.id.recipename);
            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler)row.getTag();
        }
        RecommendedDataProvider dataProvider;
        dataProvider = (RecommendedDataProvider)this.getItem(position);
        handler.Picture.setImageResource(dataProvider.getPicture_resource());
        handler.category.setText(dataProvider.getCategory());
        handler.level.setText(dataProvider.getLevel());
        handler.recipename.setText(dataProvider.getRecipename());
        return row;
    }
}
