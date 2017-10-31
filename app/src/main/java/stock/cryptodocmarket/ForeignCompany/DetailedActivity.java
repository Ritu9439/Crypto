package stock.cryptodocmarket.ForeignCompany;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import stock.cryptodocmarket.MyService;
import stock.cryptodocmarket.R;
import stock.cryptodocmarket.SessionData.SessionForeignListData;

public class DetailedActivity extends AppCompatActivity {
    String marketname = "";
    StringBuilder stringbuilder=new StringBuilder();
    ArrayList<CryptoImage> ar=new ArrayList<>();
    RecyclerView recyclerView;
    ArrayList al2=new ArrayList();
    ForeignMarketAdapter adapter;

    ArrayList<ForeignMarket> arrayList=new ArrayList<>();
    private SessionForeignListData sharedPreference;
    private Gson gson;
ProgressBar progress;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(MyService.BROADCAST_ACTION));

*/
        setContentView(R.layout.activity_detailed);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        al2=getIntent().getStringArrayListExtra("data");

        Log.d("asdada",""+al2);
        recyclerView = (RecyclerView) findViewById(R.id.formarketlist);
        progress = (ProgressBar) findViewById(R.id.progress);
        gson = new Gson();
        sharedPreference = new SessionForeignListData(DetailedActivity.this);

        marketname = getIntent().getStringExtra("marketname");
        Log.d("asdada",""+marketname);

        getSupportActionBar().setTitle(marketname);
        Toast.makeText(this, "" + marketname, Toast.LENGTH_SHORT).show();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailedActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        if (marketname!=null && !marketname.isEmpty()) {

            if (marketname.equalsIgnoreCase("Bittrex")) {
                ArrayList<ForeignMarket> arrayList=new ArrayList<>();
                adapter=new ForeignMarketAdapter(getHighScoreListbitttrexFromSharedPreference(),DetailedActivity.this, al2);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);

            }
            if (marketname.equalsIgnoreCase("Kraken")) {

                ArrayList<ForeignMarket> arrayList=new ArrayList<>();

                adapter=new ForeignMarketAdapter(getHighScoreListkrakenFromSharedPreference(),DetailedActivity.this, al2);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }
            if (marketname.equalsIgnoreCase("Poloniex")) {
                ArrayList<ForeignMarket> arrayList=new ArrayList<>();
                adapter=new ForeignMarketAdapter(getHighScoreListpoloFromSharedPreference(),DetailedActivity.this, al2);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }
            if (marketname.equalsIgnoreCase("Bitstamp")) {
                ArrayList<ForeignMarket> arrayList=new ArrayList<>();
                adapter=new ForeignMarketAdapter(getHighScoreListbitstampFromSharedPreference(),DetailedActivity.this, al2);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }
            if (marketname.equalsIgnoreCase("Bitfinex")) {
                ArrayList<ForeignMarket> arrayList=new ArrayList<>();
                adapter=new ForeignMarketAdapter(getHighScoreListbitfinexFromSharedPreference(),DetailedActivity.this, al2);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }

            if (marketname.equalsIgnoreCase("BTCE")) {
                ArrayList<ForeignMarket> arrayList=new ArrayList<>();

                adapter=new ForeignMarketAdapter(getHighScoreListbtceFromSharedPreference(),DetailedActivity.this, al2);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }
        }
        intent = new Intent(DetailedActivity.this, BroadcastReceiver.class);

        startService(intent);

        registerReceiver(broadcastReceiver, new IntentFilter(MyService.BROADCAST_ACTION));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            // Do stuff
            finish();

        }
        return true;
    }
    private ArrayList<ForeignMarket> getHighScoreListbitttrexFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreListbittrex();
        Type type = new TypeToken<List<ForeignMarket>>(){}.getType();
        arrayList = gson.fromJson(jsonScore, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }
    private ArrayList<ForeignMarket> getHighScoreListbitfinexFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreListbitfinex();
        Type type = new TypeToken<List<ForeignMarket>>(){}.getType();
        arrayList = gson.fromJson(jsonScore, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    private ArrayList<ForeignMarket> getHighScoreListbitstampFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreListbitstamp();
        Type type = new TypeToken<List<ForeignMarket>>(){}.getType();
        arrayList = gson.fromJson(jsonScore, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    private ArrayList<ForeignMarket> getHighScoreListpoloFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreListpoloniex();
        Type type = new TypeToken<List<ForeignMarket>>(){}.getType();
        arrayList = gson.fromJson(jsonScore, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    private ArrayList<ForeignMarket> getHighScoreListkrakenFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreListKraken();
        Type type = new TypeToken<List<ForeignMarket>>(){}.getType();
        arrayList = gson.fromJson(jsonScore, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    private ArrayList<ForeignMarket> getHighScoreListbtceFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreListBTCE();
        Type type = new TypeToken<List<ForeignMarket>>(){}.getType();
        arrayList = gson.fromJson(jsonScore, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }
































    private void saveScoreListToSharedpreferencebittrex(ArrayList<ForeignMarket> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);

        //save to shared preference
        sharedPreference.saveBittrex(jsonScore);
    }




    private void saveScoreListToSharedpreferencebitfinex(ArrayList<ForeignMarket> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);

        //save to shared preference
        sharedPreference.saveBitfinex(jsonScore);
    }

    private void saveScoreListToSharedpreferencebitstamp(ArrayList<ForeignMarket> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);

        //save to shared preference
        sharedPreference.saveBitStamp(jsonScore);
    }

    private void saveScoreListToSharedpreferencepolo(ArrayList<ForeignMarket> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);

        //save to shared preference
        sharedPreference.savePoloniex(jsonScore);
    }

    private void saveScoreListToSharedpreferenceKraken(ArrayList<ForeignMarket> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);

        //save to shared preference
        sharedPreference.saveBitKraken(jsonScore);
    }

    private void saveScoreListToSharedpreferenceBTCE(ArrayList<ForeignMarket> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);

        //save to shared preference
        sharedPreference.saveBTCE(jsonScore);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAAAAAAAAAAAGfoe",""+intent.getParcelableArrayListExtra("foreignList"));
            if (marketname!=null && !marketname.isEmpty()) {
             if (marketname.equalsIgnoreCase("Bittrex")) {

                 ArrayList<ForeignMarket> arrayList=new ArrayList<>();


                 arrayList = intent.getParcelableArrayListExtra("bittrex");
                 saveScoreListToSharedpreferencebittrex(arrayList);
                 adapter = new ForeignMarketAdapter(arrayList, DetailedActivity.this, al2);
                 recyclerView.setAdapter(adapter);
             }
                if (marketname.equalsIgnoreCase("Kraken")) {
                    ArrayList<ForeignMarket> arrayList=new ArrayList<>();

                    arrayList = intent.getParcelableArrayListExtra("kraken");
                    saveScoreListToSharedpreferenceKraken(arrayList);
                    adapter = new ForeignMarketAdapter(arrayList, DetailedActivity.this, al2);
                    recyclerView.setAdapter(adapter);
                }
                if (marketname.equalsIgnoreCase("Poloniex")) {
                    ArrayList<ForeignMarket> arrayList=new ArrayList<>();

                    arrayList = intent.getParcelableArrayListExtra("poloniex");
                    saveScoreListToSharedpreferencepolo(arrayList);
                    adapter = new ForeignMarketAdapter(arrayList, DetailedActivity.this, al2);
                    recyclerView.setAdapter(adapter);
                }
                if (marketname.equalsIgnoreCase("Bitstamp")) {
                    ArrayList<ForeignMarket> arrayList=new ArrayList<>();
                    Toast.makeText(context, "came", Toast.LENGTH_SHORT).show();
                    arrayList = intent.getParcelableArrayListExtra("bitstamp");
                    Log.d("sbitstamp",""+arrayList);
                    saveScoreListToSharedpreferencebitstamp(arrayList);
                    adapter = new ForeignMarketAdapter(arrayList, DetailedActivity.this, al2);
                    recyclerView.setAdapter(adapter);
                }
                if (marketname.equalsIgnoreCase("Bitfinex")) {
                    ArrayList<ForeignMarket> arrayList=new ArrayList<>();

                    arrayList = intent.getParcelableArrayListExtra("bitfinex");
                    saveScoreListToSharedpreferencebitfinex(arrayList);
                    adapter = new ForeignMarketAdapter(arrayList, DetailedActivity.this, al2);

                    recyclerView.setAdapter(adapter);
                }

                if (marketname.equalsIgnoreCase("BTCE")) {
                    ArrayList<ForeignMarket> arrayList=new ArrayList<>();

                    arrayList = intent.getParcelableArrayListExtra("BTCE");
                    saveScoreListToSharedpreferenceBTCE(arrayList);
                    adapter = new ForeignMarketAdapter(arrayList, DetailedActivity.this, al2);

                    recyclerView.setAdapter(adapter);
                }
            }
        }


    };

}
