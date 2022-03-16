package com.msb.presentation.utils;

import android.content.Context;

/**
 * Created by teknol
 */
public class CircleModel {

    private static Context context;

    /**
     * Gets context.
     *
     * @return the context
     */
    public static Context getContext() {

        return context;
    }

    /**
     * Sets context.
     *
     * @param context the context
     */
    public static void setContext(Context context) {
        CircleModel.context = context;
    }

}
