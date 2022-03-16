package com.msb.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.msb.R;

import java.util.HashMap;
import java.util.Random;


/**
 * Created by teknol
 */
public class CircleUtils {
    /**
     * The Random android color.
     */
    static int randomAndroidColor;
    /**
     * The Color.
     */
    static int color = -1;
    private static int[] androidColors = CircleModel.getContext().getResources().getIntArray(R.array.androidcolors);

    private static HashMap<String, Integer> alphabetColors = new HashMap<String, Integer>() {
        {
            put("A", R.color.char_a);
            put("B", R.color.char_b);
            put("C", R.color.char_c);
            put("D", R.color.char_d);
            put("E", R.color.char_e);
            put("F", R.color.char_f);
            put("G", R.color.char_g);
            put("H", R.color.char_h);
            put("I", R.color.char_i);
            put("J", R.color.char_j);

            put("K", R.color.char_k);
            put("L", R.color.char_l);
            put("M", R.color.char_m);
            put("N", R.color.char_n);
            put("O", R.color.char_o);
            put("P", R.color.char_p);

            put("Q", R.color.char_q);
            put("R", R.color.char_r);
            put("S", R.color.char_s);
            put("T", R.color.char_t);
            put("U", R.color.char_u);
            put("V", R.color.char_v);
            put("W", R.color.char_w);
            put("X", R.color.char_x);
            put("Y", R.color.char_y);
            put("Z", R.color.char_z);

        }
    };

    /**
     * Gen random color string.
     *
     * @return the string
     */
//Generate Random Color
    public static String genRandomColor() {
        randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        String hexColor = "#" + Integer.toHexString(randomAndroidColor).substring(2);
        return hexColor.toString();
    }

    /**
     * Sets circle color.
     *
     * @param position the position
     * @param color    the color
     */
//Save Circle Color in Shared Prefs
    public static void setCircleColor(int position, int color) {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        preferences = CircleModel.getContext().getSharedPreferences("colors", Context.MODE_PRIVATE);
        editor = preferences.edit().putInt(Integer.toString(position), color);
        editor.commit();
    }

    /**
     * Load circle color int.
     *
     * @param position the position
     * @return the int
     */
//Retrieve Circle Color from Shared Prefs
    public static int LoadCircleColor(int position) {
        SharedPreferences preferences = CircleModel.getContext().getSharedPreferences("colors", Context.MODE_PRIVATE);
        color = preferences.getInt(Integer.toString(position), -1);
        return color;
    }

    /**
     * Gen color from alphabet int.
     *
     * @param alphabet the alphabet
     * @return the int
     */
    public static int genColorFromAlphabet(String alphabet) {
        try {
            return CircleModel.getContext().getResources().getColor(R.color.white);
//            return SmartCircleModel.getContext().getResources().getColor(alphabetColors.get(String.valueOf(alphabet.charAt(0))));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return Color.BLACK;
    }
}
