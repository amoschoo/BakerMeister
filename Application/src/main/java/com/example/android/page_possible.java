package com.example.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.backend.Category;
import com.example.android.backend.Recipe;
import com.example.android.resources.RecommendedAdapter;
import com.example.android.resources.RecommendedDataProvider;
import com.example.android.resources.globals;
import com.example.android.slidingtabsbasic.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;

public class page_possible extends Activity {
    ListView listview;
    RecommendedAdapter adapter;
    ArrayList<String> recipename = new ArrayList<>();
    ArrayList<String> level = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_possible);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Possible Recipes");
        listview = (ListView)findViewById(R.id.possiblelistView);
        getlistview();

    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }

    public void getlistview(){
        adapter=new RecommendedAdapter(getApplicationContext(),R.layout.row_layout);
        ArrayList<Recipe> recipe = globals.getInventory().getPossible(globals.getRecipeBook());
        listview.setAdapter(adapter);
        int k=0;
        if (recipe==null){
            Toast.makeText(getApplicationContext(),"No Possible Recipes",Toast.LENGTH_SHORT).show();
        }

        for (Recipe r:recipe){
            if (r!=null){
                recipename.add(r.toString());
                Toast.makeText(getApplicationContext(),r.toString(),Toast.LENGTH_SHORT).show();
                level.add("" + r.getDifficulty());
                category.add((r.getCategory()).toString());
            }
            else {
                Toast.makeText(getApplicationContext(),"No Recipes",Toast.LENGTH_SHORT).show();
                recipename.add("Completed");
                category.add(String.valueOf(Category.values()[k]));
            }
            k++;
        }
        int i =0;
        for(String name:recipename){
            RecommendedDataProvider dataProvider=null;
            if (name!="Completed") {

                try {
                    dataProvider = new RecommendedDataProvider((Integer) globals.getImages().get(name),
                            name, category.get(i), level.get(i));
                } catch (Exception e){
                    dataProvider = new RecommendedDataProvider(R.drawable.bakericon,
                            name, category.get(i), level.get(i));
                }
            }else{
                dataProvider = new RecommendedDataProvider(R.drawable.baker,
                        name, category.get(i), level.get(i));
            }
            adapter.add(dataProvider);
            i++;}
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    globals.setRecipename(recipename.get(position));
                    Intent intent = new Intent(page_possible.this, page_recipe.class);
                    startActivity(intent);
            }
        });
    }
}
