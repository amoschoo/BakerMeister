package com.example.android.backend;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hj on 30/11/15.
 */
public class Ingredient {
    public static HashMap<String, String> unitMap = new HashMap<>();
    private String name;
    private double amount;
    private String unit;

    Ingredient(String name, double amount, String unit){
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    Ingredient(String name, double amount){
        this.name = name;
        this.amount = amount;
        if (unitMap.containsKey(name)) {
            this.unit = unitMap.get(name);
        }else{
            this.unit = "g";
        }

    }

    public static void constructMap(String fileName,Context context){
        try {
            String line;
            String name = "";
            int counter = 0;
            boolean pass;

            InputStream inputStream = context.getResources().getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null){
                line = line.trim();
                pass = line.equals("");
                if (!pass) {
                    if (counter == 0){
                        name = line;
                        counter = 1;
                    }else{
                        if (!line.equals("nil")) {
                            unitMap.put(name, line);
                        }else{
                            unitMap.put(name, "");
                        }
                        counter = 0;
                    }
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isSubset(Ingredient other){
        return (this.name.equals(other.toString()) && other.getAmount() >= this.amount);
    }

    public String toString() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public Ingredient times(int number){
        return new Ingredient(this.toString(), this.getAmount()*number);
    }
}
