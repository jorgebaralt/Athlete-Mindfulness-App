package com.jorgebaralt.athlete_mindful_app.Util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by User on 10/23/2017.
 */

public class HideKeyboard {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
