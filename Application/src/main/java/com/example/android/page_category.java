package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.backend.Category;
import com.example.android.resources.globals;
import com.example.android.slidingtabsbasic.R;

/**
 * Created by Amos on 8/12/15.
 */
public class page_category extends android.support.v4.app.Fragment {
    Button cat1btn,cat2btn,cat3btn,cat4btn,cat5btn,cat6btn,cat7btn;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.page_category, container, false);
        buttonclicks(myView);
        return myView;
    }
    public void buttonclicks(final View myView){
        cat1btn = (Button) myView.findViewById(R.id.cat1);
        cat1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globals.setCategory(Category.COOKIE);
                Intent intent= new Intent(myView.getContext(),page_skilltree.class);
                startActivity(intent);
            }
        });
        cat2btn = (Button) myView.findViewById(R.id.cat2);
        cat2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globals.setCategory(Category.CAKE);
                Intent intent= new Intent(myView.getContext(),page_skilltree.class);
                startActivity(intent);
            }
        });
        cat3btn = (Button) myView.findViewById(R.id.cat3);
        cat3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globals.setCategory(Category.PASTRY);
                Intent intent= new Intent(myView.getContext(),page_skilltree.class);
                startActivity(intent);
            }
        });
        cat4btn = (Button) myView.findViewById(R.id.cat4);
        cat4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globals.setCategory(Category.CUSTARD);
                Intent intent= new Intent(myView.getContext(),page_skilltree.class);
                startActivity(intent);
            }
        });
        cat5btn = (Button) myView.findViewById(R.id.cat5);
        cat5btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globals.setCategory(Category.BREAD);
                Intent intent= new Intent(myView.getContext(),page_skilltree.class);
                startActivity(intent);
            }
        });
        cat6btn = (Button) myView.findViewById(R.id.cat6);
        cat6btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globals.setCategory(Category.CAC);
                Intent intent= new Intent(myView.getContext(),page_skilltree.class);
                startActivity(intent);
            }
        });
        cat7btn = (Button) myView.findViewById(R.id.cat7);
        cat7btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globals.setCategory(Category.PIE);
                Intent intent= new Intent(myView.getContext(),page_skilltree.class);
                startActivity(intent);
            }
        });
    }
}
