package project.akshay.finalyear;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    final private static String KEY_IS_LOGGED_IN = "KEY_IS_LOGGED_IN";

    public PreferencesManager(Context context) {

        int PRIVATE_MODE = 0;
        String PREF_NAME = "PREFERENCE_MANAGER";
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        this.editor = sharedPreferences.edit();
        editor.apply();

    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN,isLoggedIn);
        editor.apply();
    }

    public boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

}
