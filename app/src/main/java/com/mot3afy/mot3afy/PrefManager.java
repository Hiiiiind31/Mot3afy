package com.mot3afy.mot3afy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hind on 10/16/2017.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String user_name = "user_name";
    private static final String user_id = "user_id";
    private static final String user_email = "user_email";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserData (String id , String name , String email){

        editor.putString(user_id, id);
        editor.putString(user_name, name);
        editor.putString(user_email, email);
        editor.commit();
    }

    public String getUser_id() {
        return pref.getString(user_id,"");
    }
    public String getUser_name() {
        return pref.getString(user_name,"");
    }
    public String getUser_email() {
        return pref.getString(user_email,"");
    }

}
