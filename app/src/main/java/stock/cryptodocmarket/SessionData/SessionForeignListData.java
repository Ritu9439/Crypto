package stock.cryptodocmarket.SessionData;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 29-10-2017.
 */

public class SessionForeignListData {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "prefforeign";
    private static final String ForeignMarketbittrex = "foreignMarketbittrex";
    private static final String ForeignMarkebitfinex = "foreignMarkebitfinex";
    private static final String ForeignMarketbitstamp = "foreignMarketbitstamp";
    private static final String ForeignMarketpoloniex = "foreignMarketpoloniex";
    private static final String ForeignMarketKraken = "foreignMarketKraken";
    private static final String ForeignMarketBTCE = "foreignMarketbtce";
    
    public SessionForeignListData(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveBittrex(String scoreString) {
        editor.putString(ForeignMarketbittrex, scoreString);
        editor.commit();
    }
    public void saveBitfinex(String scoreString) {
        editor.putString(ForeignMarkebitfinex, scoreString);
        editor.commit();
    }
    public void savePoloniex(String scoreString) {
        editor.putString(ForeignMarketpoloniex, scoreString);
        editor.commit();
    }
    public void saveBitStamp(String scoreString) {
        editor.putString(ForeignMarketbitstamp, scoreString);
        editor.commit();
    } public void saveBitKraken(String scoreString) {
        editor.putString(ForeignMarketKraken, scoreString);
        editor.commit();
    }
    public void saveBTCE(String scoreString) {
        editor.putString(ForeignMarketBTCE, scoreString);
        editor.commit();
    }

    public String getHighScoreListbittrex() {
        return pref.getString(ForeignMarketbittrex, "");
    }
    public String getHighScoreListbitfinex() {
        return pref.getString(ForeignMarkebitfinex, "");
    }
    public String getHighScoreListpoloniex() {
        return pref.getString(ForeignMarketpoloniex, "");
    }
    public String getHighScoreListbitstamp() {
        return pref.getString(ForeignMarketbitstamp, "");
    }
    public String getHighScoreListKraken() {
        return pref.getString(ForeignMarketKraken, "");
    }

    public String getHighScoreListBTCE() {
        return pref.getString(ForeignMarketBTCE, "");
    }


}
