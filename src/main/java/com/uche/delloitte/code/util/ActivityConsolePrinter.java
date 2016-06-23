package com.uche.delloitte.code.util;

import com.uche.delloitte.code.model.Activity;

/**
 * Created by uuchey on 25/05/2016.
 */
public class ActivityConsolePrinter {

    public static void print(String time, Activity activity){
        System.out.println(time +" "+activity.getName() +" "+ activity.getFriendlyTime());
    }
}
