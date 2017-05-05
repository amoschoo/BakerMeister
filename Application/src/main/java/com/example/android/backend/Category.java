package com.example.android.backend;

/**
 * Created by hj on 3/12/15.
 */
public enum Category {
    COOKIE, CAKE, PASTRY, CUSTARD, BREAD, CAC, PIE;
    @Override
    public String toString() {
        String init = super.toString();
        if (init.equals("CAC")){
            init = "CANDY & CONFECTIONERY";
        }
        return init;
    }
}

