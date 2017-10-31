package stock.cryptodocmarket.SessionData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import stock.cryptodocmarket.MainActivity;
import stock.cryptodocmarket.UserActivity;

import static stock.cryptodocmarket.SessionData.SessionManagement.KEY_EMAIL;
import static stock.cryptodocmarket.SessionData.SessionManagement.KEY_PHOTOURI;

/**
 * Created by Administrator on 24-10-2017.
 */

public class SessionMarket {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file password
    private static final String PREF_NAME = "SessionMarket";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "HasValue";


    // Email address (make variable public to access from outside)
    public static final String KEY_MARKET = "market";
    public static final String KEY_COIN = "coin";

    // Constructor
    public SessionMarket(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String market, String coin){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing password in pref


        // Storing email in pref
        editor.putString(KEY_MARKET, market);
        editor.putString(KEY_COIN, coin);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkCoin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, UserActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getCoinDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user password


        // user email id
        user.put(KEY_MARKET, pref.getString(KEY_MARKET, null));
        user.put(KEY_COIN, pref.getString(KEY_COIN, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void clearCoin(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
