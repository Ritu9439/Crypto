package stock.cryptodocmarket.SessionData;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 29-10-2017.
 */

public class SessionIndianListData {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "pref";
    private static final String IndianMarket = "indianmarket";
    public SessionIndianListData(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveHighScoreList(String scoreString) {
        editor.putString(IndianMarket, scoreString);
        editor.commit();
    }

    public String getHighScoreList() {
        return pref.getString(IndianMarket, "");
    }


}
