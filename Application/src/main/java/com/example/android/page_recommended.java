package com.example.android;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.backend.Category;
import com.example.android.backend.Recipe;
import com.example.android.resources.DBHelper;
import com.example.android.resources.RecommendedAdapter;
import com.example.android.resources.RecommendedDataProvider;
import com.example.android.resources.globals;
import com.example.android.slidingtabsbasic.R;

import java.util.ArrayList;

/**
 * Created by Amos on 8/12/15.
 */
public class page_recommended extends android.support.v4.app.Fragment  {
    ListView listview;
    ArrayList<Recipe> recipe;
    String[] recipename = new String[7];
    String[] level = new String[7];
    String[] category = new String[7];
    RecommendedAdapter adapter;
    int image;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        globals.setRecommendedarray(recipe);
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.page_recommended, container, false);
        listview = (ListView) myView.findViewById(R.id.list_view);
        getlistview();
        return myView;
    }
    public void getlistview(){
        adapter=new RecommendedAdapter(this.getContext(),R.layout.row_layout);
        recipe = globals.getSkillTree().getRecommended();
        listview.setAdapter(adapter);
        int k=0;
        for (Recipe r:recipe){
            if (r!=null){
                recipename[k]=r.toString();
                level[k]=""+r.getDifficulty();
                category[k]= (r.getCategory()).toString();
            }
            else {
                recipename[k]="Completed";
                category[k]= String.valueOf(Category.values()[k]);
            }
            k++;
        }
        int i =0;
        for(String name:recipename){
            RecommendedDataProvider dataProvider=null;
            if (name!="Completed") {

                try {
                    dataProvider = new RecommendedDataProvider((Integer) globals.getImages().get(name),
                            name, category[i], level[i]);
                } catch (Exception e){
                    dataProvider = new RecommendedDataProvider(R.drawable.bakericon,
                            name, category[i], level[i]);
                }
            }else{
                dataProvider = new RecommendedDataProvider(R.drawable.baker,
                        name, category[i], level[i]);
            }
            adapter.add(dataProvider);
            i++;}
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (recipe.get(position)==null){
                    Toast.makeText(getActivity().getApplicationContext(),"Task Completed",Toast.LENGTH_SHORT).show();
                }else{
                    globals.setRecipename(recipename[position]);
                    Intent intent = new Intent(getActivity(),page_recipe.class);
                    startActivity(intent);}
            }
        });
    }

    @Override
    public void onResume() {
        getlistview();
        super.onResume();
    }
}
