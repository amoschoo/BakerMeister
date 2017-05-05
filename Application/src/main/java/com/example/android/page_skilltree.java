package com.example.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.backend.Bool;
import com.example.android.backend.Category;
import com.example.android.backend.Recipe;
import com.example.android.resources.CustomGridAdapter;
import com.example.android.resources.CustomGridAdapter2;
import com.example.android.resources.CustomGridDataProvider;
import com.example.android.resources.CustomGridDataProvider2;
import com.example.android.resources.globals;
import com.example.android.slidingtabsbasic.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class page_skilltree extends Activity {
    Category category = globals.getCategory();
    GridView gridView1;
    GridView gridView2;
    GridView gridView3;
    ArrayList<Recipe> recipes1;
    ArrayList<Recipe> recipes2;
    ArrayList<Recipe> recipes3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_skilltree);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(category.toString());
        gridviewsetup();


    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }
    public void gridviewsetup(){
        gridView1 = (GridView)findViewById(R.id.gridView1);
        gridView2 = (GridView)findViewById(R.id.gridView2);
        gridView3 = (GridView)findViewById(R.id.gridView3);
        recipes1=globals.getRecipeBook().getRecipes(globals.getCategory(),1);
        recipes2=globals.getRecipeBook().getRecipes(globals.getCategory(),2);
        recipes3=globals.getRecipeBook().getRecipes(globals.getCategory(),3);
        CustomGridAdapter2 adapter;
        int i =0;
        adapter=new CustomGridAdapter2(this,R.layout.custom_grid_layout2);
        gridView1.setAdapter(adapter);
        CustomGridDataProvider2 dataProvider=null;
        for(Recipe name:recipes1){
            try {
                if (globals.getImages().containsKey(name.toString())) {
                    dataProvider = new CustomGridDataProvider2(name.toString(), (Integer) globals.getImages().get(name.toString()));
                } else {
                    dataProvider = new CustomGridDataProvider2(name.toString(),R.drawable.bakericon);

                }
            } catch (Exception e){
                Log.i("dataprovider","exception");
                dataProvider = new CustomGridDataProvider2(name.toString(), (Integer) globals.getImages().get("Cookie"));

            }
            adapter.add(dataProvider);
            i++;
        }
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String RECIPE = recipes1.get(position).toString();
                Bool b = globals.getSkillTree().getTree().get(globals.getRecipeBook().getRecipe(RECIPE));
                if (b.toString().equals("UNACCESSIBLE")) {
                    Toast.makeText(getApplicationContext(), "UNACCESSIBLE", Toast.LENGTH_SHORT).show();
                } else {
                    globals.setRecipename(recipes1.get(position).toString());
                    Intent intent = new Intent(page_skilltree.this, page_recipe.class);
                    startActivity(intent);
                }
            }
        });
        i =0;
        adapter=new CustomGridAdapter2(this,R.layout.custom_grid_layout2);
        gridView2.setAdapter(adapter);
        CustomGridDataProvider2 dataProvider2=null;
        for(Recipe name:recipes2){
            try {
                if (globals.getImages().containsKey(name.toString())) {
                    dataProvider2 = new CustomGridDataProvider2(name.toString(), (Integer) globals.getImages().get(name.toString()));
                }else{
                    dataProvider2 = new CustomGridDataProvider2(name.toString(), R.drawable.bakericon);

                }
            } catch (Exception e){
                dataProvider2 = new CustomGridDataProvider2(name.toString(), (Integer) globals.getImages().get("Cookie"));
            }
            adapter.add(dataProvider2);
            i++;
        }
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String RECIPE = recipes2.get(position).toString();
                Bool b = globals.getSkillTree().getTree().get(globals.getRecipeBook().getRecipe(RECIPE));
                if (b.toString().equals("UNACCESSIBLE")) {
                    Toast.makeText(getApplicationContext(),"UNACCESSIBLE",Toast.LENGTH_SHORT).show();
                } else {
                    globals.setRecipename(recipes2.get(position).toString());
                    Intent intent = new Intent(page_skilltree.this, page_recipe.class);
                    startActivity(intent);
                }
            }
        });
        i =0;
        adapter=new CustomGridAdapter2(this,R.layout.custom_grid_layout2);
        gridView3.setAdapter(adapter);
        CustomGridDataProvider2 dataProvider3=null;
        for(Recipe name:recipes3){
            try {
                if (globals.getImages().containsKey(name.toString())) {
                    dataProvider3 = new CustomGridDataProvider2(name.toString(), (Integer) globals.getImages().get(name.toString()));
                }else{
                    dataProvider3 = new CustomGridDataProvider2(name.toString(), R.drawable.bakericon);

                }
            } catch (Exception e){
                dataProvider3 = new CustomGridDataProvider2(name.toString(), (Integer) globals.getImages().get("Cookie"));

            }
            adapter.add(dataProvider3);
            i++;
        }
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String RECIPE = recipes3.get(position).toString();
                Bool b = globals.getSkillTree().getTree().get(globals.getRecipeBook().getRecipe(RECIPE));
                if (b.toString().equals("UNACCESSIBLE")) {
                    Toast.makeText(getApplicationContext(),"UNACCESSIBLE",Toast.LENGTH_SHORT).show();
                } else {
                    globals.setRecipename(RECIPE);
                    Intent intent = new Intent(page_skilltree.this, page_recipe.class);
                    startActivity(intent);
                }
            }
        });
    }
}
