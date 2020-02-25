package project.akshay.finalyear;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    final private static String KEY_IS_LOGGED_IN = "KEY_IS_LOGGED_IN";
    final private static String KEY_USER_ID = "KEY_USER_ID";
    final private static String KEY_USER_NAME = "KEY_USER_NAME";
    final private static String KEY_USER_TYPE = "KEY_USER_TYPE";

    public PreferencesManager(Context context) {

        int PRIVATE_MODE = 0;
        String PREF_NAME = "PREFERENCE_MANAGER";
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        this.editor = sharedPreferences.edit();
        editor.apply();

    }

    public void setUserType(int userType) {
        editor.putInt(KEY_USER_TYPE, userType);
        editor.apply();
    }

    public int getUserType() {
        return sharedPreferences.getInt(KEY_USER_TYPE, 0);
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN,isLoggedIn);
        editor.apply();
    }

    public boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setUserID(String userID) {
        editor.putString(KEY_USER_ID, userID);
        editor.apply();
    }

    public String getUserID() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public void setUserName(String name) {
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public void clearSharedPreferences() {
        editor.clear();
        editor.apply();
    }

}
