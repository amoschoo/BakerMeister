package com.example.android;

import android.app.ActionBar;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.backend.Bool;
import com.example.android.backend.Recipe;
import com.example.android.resources.DBHelper;
import com.example.android.resources.globals;
import com.example.android.slidingtabsbasic.R;

public class page_recipe extends Activity {
    CheckBox bakedcheckbox;
    SeekBar quantityseekbar;
    TextView quantityedittext;
    int quantity=1;
    TextView fullrecipe;
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ActionBar actionBar = getActionBar();
        quantityedittext=(TextView)findViewById(R.id.quantityET);
        quantityseekbar=(SeekBar)findViewById(R.id.seekbar);
        quantityseekbar.setMax(90);
        quantityseekbar.setOnSeekBarChangeListener(seekbarlistener);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String recipename = globals.getRecipename();
        recipe = globals.getRecipeBook().getRecipe(recipename);
        TextView recipecategory = (TextView)findViewById(R.id.recipecategory);
        recipecategory.setText(recipe.getCategory().toString());
        TextView recipelevel = (TextView)findViewById(R.id.recipelevel);
        recipelevel.setText("" + recipe.getDifficulty());
        fullrecipe = (TextView)findViewById(R.id.fullrecipe);
        getquantiy();
        ImageView image = (ImageView)findViewById(R.id.recipeimage);
        try {
            image.setImageResource((Integer) globals.getImages().get(recipename));
        }catch (Exception e){
            image.setImageResource(R.drawable.bakericon);
        }

        getActionBar().setTitle(recipename);
        bakedcheckbox = (CheckBox)findViewById(R.id.checkBox);
        bakedcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Status: Baked!", Toast.LENGTH_SHORT).show();
                    globals.getSkillTree().updateTrue(globals.getRecipeBook().getRecipe(globals.getRecipename()));
                } else {
                    Toast.makeText(getApplicationContext(), "Status: Not Baked!", Toast.LENGTH_SHORT).show();
                    globals.getSkillTree().updateFalse(globals.getRecipeBook().getRecipe(globals.getRecipename()));
                }
            }
        });
        checkboxstatus();

    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }
    public void checkboxstatus(){
        Bool b = globals.getSkillTree().getTree().get(globals.getRecipeBook().getRecipe(globals.getRecipename()));
        if (b.toString().equals("COMPLETED")) {
            bakedcheckbox.setChecked(true);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        for (Recipe r : globals.getSkillTree().getTree().keySet()) { //From globals to database
            try{
            String RECIPE = r.toString();
                DBHelper dbHelper;
                SQLiteDatabase sqLiteDatabase;
                dbHelper = new DBHelper(getApplicationContext());
                sqLiteDatabase = dbHelper.getWritableDatabase();
                Bool b = globals.getSkillTree().getTree().get(globals.getRecipeBook().getRecipe(RECIPE));
                if (b.toString().equals("COMPLETED")) {
                    dbHelper.updateskill(RECIPE, "1", sqLiteDatabase);
                } else if (b.toString().equals("ACCESSIBLE")) {
                    dbHelper.updateskill(RECIPE, "0", sqLiteDatabase);
                } else if (b.toString().equals("UNACCESSIBLE")) {
                    dbHelper.updateskill(RECIPE, "0", sqLiteDatabase);
                }
            }catch(NullPointerException e){

            }
        }
    }
    private SeekBar.OnSeekBarChangeListener seekbarlistener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            quantity = (int)((quantityseekbar.getProgress()) * .1)+1;
            quantityedittext.setText(""+quantity);
            getquantiy();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private void getquantiy(){
        quantity = Integer.parseInt(quantityedittext.getText().toString());
        fullrecipe.setText(recipe.display(quantity)); //need to add slider
    }
    public void tobuy(View v){
        TextView buyTV= (TextView)findViewById(R.id.tobuyTV);
        String buyitems="Items to buy:\n";
        for (String s: globals.getInventory().getRequired(recipe,quantity).keySet()){
            buyitems+=s+": "+globals.getInventory().getRequired(recipe,quantity).get(s)+"\n";
        }
        buyTV.setText(buyitems);
    }
}
