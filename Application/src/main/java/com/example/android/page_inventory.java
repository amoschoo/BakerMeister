package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.backend.Ingredient;
import com.example.android.resources.CustomGridAdapter;
import com.example.android.resources.CustomGridDataProvider;
import com.example.android.resources.DBHelper;
import com.example.android.resources.globals;
import com.example.android.slidingtabsbasic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class page_inventory extends android.support.v4.app.Fragment {
    SQLiteDatabase sqLiteDatabase;
    DBHelper DBHelper;
    Cursor cursor;
    GridView GridView;
    HashMap<String,Integer> image= new HashMap<>();
    String[] ingredients = {"empty","empty","empty"};
    String[] amount = {"0g","0g","0g"};
    String[] shelves = {"Select Shelf","shelf1", "shelf2", "shelf3"};
    int shelfno=0;
    AutoCompleteTextView item;
    Spinner spinner;
    View myView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.page_inventory, container, false);
        inventoryimages();
        DBHelper = new DBHelper(getActivity().getApplicationContext());
        sqLiteDatabase = DBHelper.getWritableDatabase();
        data();
        // Inflate the layout for this fragment
        GridView = (GridView) myView.findViewById(R.id.gridView);
        item = (AutoCompleteTextView) myView.findViewById(R.id.shelfitem);
        ArrayList<String> suggestions = globals.getInventory().getinventorylist();
        ArrayAdapter<String> suggestion = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_list_item_1,suggestions);
        item.setAdapter(suggestion);
        spinner = (Spinner)myView.findViewById(R.id.spinner);
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, shelves);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);
        Button button = (Button)myView.findViewById(R.id.shelfbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shelf = spinner.getSelectedItem().toString();
                if (!shelf.equals("Select Shelf")) {
                    String itemname = item.getText().toString();
                    if (itemname.equals("")){
                        itemname="empty";
                    }
                    globals.getIdentifier().get(shelf).setItem(itemname);
                    DBHelper.updateinventory(shelf, itemname, sqLiteDatabase);
                    DBHelper.updateadjustment(shelf, "" + amount[shelfno], sqLiteDatabase);
                    data();
                    setGridView();
                    Toast.makeText(getActivity().getApplicationContext(),"Inventory Updated",Toast.LENGTH_SHORT).show();
                    hide_keyboard(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),"Shelf not selected",Toast.LENGTH_SHORT).show();
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinner.getSelectedItem().toString().equals("Select Shelf")) {

                    String shelfname = "shelf1";
                    shelfno=0;
                    if (position == 2) {
                        shelfname = "shelf2";
                        shelfno=1;
                    } else if (position == 3) {
                        shelfno=2;
                        shelfname = "shelf3";
                    }
//                    String item_old = globals.getIdentifier().get(shelfname).getItem();
//                    item.setText(item_old);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Timer myTimer = new Timer();
        //Set the schedule function and rate
        myTimer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            getActivity().runOnUiThread(setRunnable);
                                            //Called at every 1000 milliseconds (1 second)
                                            getActivity().runOnUiThread(setRunnable);
                                        }
                                    },
                //set the amount of time in milliseconds before first execution
                0,
                //Set the amount of time between each execution (in milliseconds)
                2000);
//        Button possiblebtn = (Button)myView.findViewById(R.id.possiblebtn);
//        possiblebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                possible(myView);
//            }
//        });

        setGridView();
        return myView;
    }
    public void data(){
        cursor = DBHelper.getinventory(sqLiteDatabase);
        if (cursor.moveToFirst()){
            do {

                String shelf,item;
                shelf=cursor.getString(0);
                item=cursor.getString(1);
                if (shelf.equals("shelf1")){
                    ingredients[0]=item;
                    globals.getIdentifier().get("shelf1").setItem(item);
                }
                else if (shelf.equals("shelf2")){
                    ingredients[1]=item;
                    globals.getIdentifier().get("shelf2").setItem(item);
                }
                else if (shelf.equals("shelf3")){
                    ingredients[2]=item;
                    globals.getIdentifier().get("shelf3").setItem(item);
                }

            }while (cursor.moveToNext());
        }
    }
    public void setGridView(){
        CustomGridAdapter adapter;
        int i =0;
        adapter=new CustomGridAdapter(this.getContext(),R.layout.custom_grid_layout);
        GridView.setAdapter(adapter);

        for(String name:ingredients){
            amount[i]=""+globals.getInventory().getIngredientsList().get(name);
            if (Ingredient.unitMap.get(name) != null) {
                amount[i] += Ingredient.unitMap.get(name);
            }
            CustomGridDataProvider dataProvider=null;
            if (name.equals("empty")){
                dataProvider = new CustomGridDataProvider(name, amount[i], R.drawable.empty);
            } else {
                if (image.containsKey(name.toLowerCase())) {
                    dataProvider = new CustomGridDataProvider(name, amount[i], image.get(name.toLowerCase()));
                } else{
                    dataProvider = new CustomGridDataProvider(name, amount[i], R.drawable.bakericon);
                }
            }
            adapter.add(dataProvider);
            i++;
        }
        GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position+1);
            }
        });
    }
    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void inventoryimages(){
        image.put("sugar",R.drawable.sugar);
        image.put("icing sugar",R.drawable.sugar);
        image.put("flour",R.drawable.flour);
        image.put("salt",R.drawable.salt);
        image.put("baking powder",R.drawable.bakingpowder);
        image.put("butter",R.drawable.butter);
        image.put("egg",R.drawable.egg);
        image.put("egg(s)",R.drawable.egg);
        image.put("chocolate",R.drawable.chocolate);
        image.put("banana(s)",R.drawable.banana);
        image.put("banana",R.drawable.banana);
        image.put("baileys",R.drawable.baileys);
        image.put("baking soda",R.drawable.bakingsoda);

    }
    final Runnable setRunnable = new Runnable() {
        public void run() {
            setGridView();
        }
    };

    public void possible(View myView){
        Intent intent= new Intent(myView.getContext(),page_possible.class);
        startActivity(intent);
    }
}

