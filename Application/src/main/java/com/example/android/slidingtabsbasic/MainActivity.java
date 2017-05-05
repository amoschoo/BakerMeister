/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.slidingtabsbasic;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.example.android.backend.Bool;
import com.example.android.backend.Ingredient;
import com.example.android.backend.Inventory;
import com.example.android.backend.Recipe;
import com.example.android.backend.RecipeBook;
import com.example.android.backend.Shelf;
import com.example.android.backend.SkillTree;
import com.example.android.common.activities.SampleActivityBase;
import com.example.android.page_recipe;
import com.example.android.resources.DBHelper;
import com.example.android.resources.globals;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {
    RecipeBook recipeBook;
    RelativeLayout relativeLayout,background;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    String skilllevel="1";
    private Socket mSocket;
    private List<String> variables = Arrays.asList("weight","humidity");
    {
        try {
            mSocket = IO.socket("http://128.199.213.105:3000");
        } catch (URISyntaxException e) {
            System.out.println("error here");
            throw new RuntimeException(e);
        }
    }
    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(getApplicationContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();
        compile(this);
        relativeLayout = (RelativeLayout)findViewById(R.id.skillLayout);
        background = (RelativeLayout)findViewById(R.id.backgroundLayout);
        setskilllayout();
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        globals.setIdentifier(new HashMap<String, Shelf>());
        globals.getIdentifier().put("shelf1", new Shelf());
        globals.getIdentifier().put("shelf2", new Shelf());
        globals.getIdentifier().put("shelf3", new Shelf());
        globals.getIdentifier().get("shelf1").setItem(null);
        globals.getIdentifier().get("shelf2").setItem(null);
        globals.getIdentifier().get("shelf3").setItem(null);
        Log.i("Emitter",globals.getIdentifier().toString());

        //
        //

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("new message", onNewMessage);
        mSocket.connect();

        Log.i("Emitter","socket");
        startSignIn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }


    //DATA from txt file
    public void compile(Context context) {
        Ingredient.constructMap("Units.txt", context);
        recipeBook = new RecipeBook("Recipes.txt",context);
        globals.setRecipeBook(recipeBook);
        SkillTree.constructLinks(recipeBook, "SkillTree.txt", context);
        SkillTree mySkills;
        String S=null;
        String a=null;
        Cursor cursor2 = dbHelper.getItem("skilllevel", sqLiteDatabase);
        if (cursor2.moveToFirst()) {
            S = cursor2.getString(0);
            a = cursor2.getString(1);
        }
        if (S.equals("0")){
            mySkills = new SkillTree(recipeBook,1);}
        else {
            mySkills = new SkillTree(recipeBook, Integer.parseInt(S));

        }
        globals.setSkillTree(mySkills);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.android.slidingtabsbasic/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("getskilltree",globals.getSkillTree().getTree().toString());
        for (Recipe r: globals.getSkillTree().getTree().keySet()){ //From globals to database
            try {
                String RECIPE = r.toString();
                Bool b = globals.getSkillTree().getTree().get(recipeBook.getRecipe(RECIPE));
                if (b.toString().equals("COMPLETED")) {
                    dbHelper.updateskill(RECIPE, "1", sqLiteDatabase);
                } else if (b.toString().equals("ACCESSIBLE")) {
                    dbHelper.updateskill(RECIPE, "0", sqLiteDatabase);
                } else if (b.toString().equals("UNACCESSIBLE")) {
                    dbHelper.updateskill(RECIPE, "0", sqLiteDatabase);
                }
            } catch (NullPointerException e){

            }

        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.android.slidingtabsbasic/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void startSignIn() {
        mSocket.emit("add user", "App");
    }




    //DATA from Hardware
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
//            Toast.makeText(getApplicationContext(), "Error connecting to server", Toast.LENGTH_LONG).show();
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject rawdata = (JSONObject) args[0];
            Log.i("Emitter", "datarecieved");
            try{
                JSONObject message = rawdata.getJSONObject("message");
                String sensorName = message.getString("shelf");
                JSONObject data = message.getJSONObject("data");
                for (String var: variables) {
                    try {
                        String value = data.getString(var);
                        switch (var) {
                            case "weight":
                                globals.getIdentifier().get(sensorName).setWeight(Double.parseDouble(value));
                                break;
                            case "humidity":
                                globals.getIdentifier().get(sensorName).setHumidity(Double.parseDouble(value));
                                break;
                        }
                    }catch (JSONException e){

                    }
                }
                if (globals.getIdentifier().get(sensorName).getItem() != null) {
                    globals.getIdentifier().get(sensorName).updateInventory(globals.getInventory());
                }
                Log.i("Emitter", globals.getInventory().getIngredientsList().toString());
            } catch (JSONException e){
            }

        }
    };

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_beginner:
                if (checked)
                    skilllevel="1";
                    break;
            case R.id.radio_advance:
                if (checked)
                    skilllevel="2";
                    break;
            case R.id.radio_expert:
                if (checked)
                    skilllevel="3";
                break;
        }
    }
    public void setskilllayout(){
        String s=null;
        Cursor cursor =  dbHelper.getItem("skilllevel", sqLiteDatabase);
        if (cursor.moveToFirst()) {
            s = cursor.getString(0);
        }
        if (s.equals("0")){

        }
        else if (s.equals("1")){
            Toast.makeText(getApplicationContext(),"Skill level: Beginner Baker",Toast.LENGTH_SHORT).show();
        } else if (s.equals("2")) {
            Toast.makeText(getApplicationContext(), "Skill level: Advanced Baker", Toast.LENGTH_SHORT).show();
        }else if (s.equals("3")) {
            Toast.makeText(getApplicationContext(), "Skill level: Expert Backer", Toast.LENGTH_SHORT).show();
        }
        if (!s.equals("0")){
            relativeLayout.setVisibility(View.GONE);
            background.setVisibility(View.GONE);
            Cursor cursor1 = dbHelper.getskills(sqLiteDatabase);
            if (cursor1.moveToFirst()){
                do {
                    String recipename,status;
                    recipename=cursor1.getString(0);
                    status=cursor1.getString(1);
                    Log.i("BlaaaaaSETUP",recipename+": "+status);
                    if (status.equals("1")){
                        globals.getSkillTree().updateTrue(recipeBook.getRecipe(recipename));
                    }else if (status.equals("0")){
                        globals.getSkillTree().updateFalse(recipeBook.getRecipe(recipename));
                    }
                }while (cursor1.moveToNext());
            }

        }
    }
    public void update(View view){
        dbHelper.updateinventory("skilllevel", skilllevel, sqLiteDatabase);
        SkillTree mySkills = new SkillTree(recipeBook, Integer.parseInt(skilllevel));
        globals.setSkillTree(mySkills);
        relativeLayout.setVisibility(View.GONE);
        background.setVisibility(View.GONE);
        Log.i("PrintTree",globals.getSkillTree().getTree().toString());
        if (skilllevel.equals("1")){
            Toast.makeText(getApplicationContext(),"Skill level: Beginner Baker",Toast.LENGTH_SHORT).show();
        } else if (skilllevel.equals("2")) {
            Toast.makeText(getApplicationContext(), "Skill level: Advanced Baker", Toast.LENGTH_SHORT).show();
        }else if (skilllevel.equals("3")) {
            Toast.makeText(getApplicationContext(), "Skill level: Expert Backer", Toast.LENGTH_SHORT).show();
        }
        for (Recipe r: mySkills.getTree().keySet()){//Define the database
            String RECIPE = r.toString();
            dbHelper.addskills(RECIPE,"0",sqLiteDatabase);
        }
        onStart();
        onResume();


    }
    public void cancel(View view){
        SkillTree mySkills = new SkillTree(recipeBook, 1);
        globals.setSkillTree(mySkills);
        dbHelper.updateinventory("skilllevel", "1", sqLiteDatabase);
        relativeLayout.setVisibility(View.GONE);
        background.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(),"Skill level: Beginner Baker",Toast.LENGTH_SHORT).show();
        for (Recipe r: mySkills.getTree().keySet()){ //Define the database
            String RECIPE = r.toString();
            dbHelper.addskills(RECIPE,"0",sqLiteDatabase);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
