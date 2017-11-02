package stock.cryptodocmarket.ForeignCompany;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stock.cryptodocmarket.ApiClient;
import stock.cryptodocmarket.Comments;
import stock.cryptodocmarket.CommentsAdapter;
import stock.cryptodocmarket.Datum;
import stock.cryptodocmarket.Example;
import stock.cryptodocmarket.MyInterface;
import stock.cryptodocmarket.R;
import stock.cryptodocmarket.SessionData.SessionManagement;
import stock.cryptodocmarket.SessionData.SessionMarket;
import stock.cryptodocmarket.UserActivity;


public class GraphActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    LineChart web;
    Toolbar toolbar;
    String coin,coinprice,market;
RecyclerView postdatalist;
RadioButton timeseries_hour;
    Dialog mBottomSheetDialog;
    TextView txt_logout;
    String name;
    ArrayList<Comments> arraylistcomments=new ArrayList<>();
    CommentsAdapter commentAdapter;
    RadioGroup timeseries;
    ArrayList<Entry> arrayList=new ArrayList<>();
    ArrayList<String> al=new ArrayList<>();
TextView cointv,coinpricetv,open,high,low,close,date;

FloatingActionButton addNewPostFab;
    LinearLayout newCommentContainer;
    String email="",photo="";
    EditText commentEditText;
    Button sendButton;
    private Uri photoUrl;
SessionManagement sessionManagement;
SessionMarket sessionMarket;
    TextView addcomments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        sessionManagement=new SessionManagement(GraphActivity.this);
        sessionMarket=new SessionMarket(GraphActivity.this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sendButton= (Button) findViewById(R.id.sendButton);

        web= (LineChart) findViewById(R.id.web);
        timeseries= (RadioGroup) findViewById(R.id.timeseries);
        newCommentContainer= (LinearLayout) findViewById(R.id.newCommentContainer);
        timeseries_hour= (RadioButton) findViewById(R.id.timeseries_hour);
        cointv= (TextView) findViewById(R.id.cointv);
        postdatalist= (RecyclerView) findViewById(R.id.postdatalist);
        coinpricetv= (TextView) findViewById(R.id.coinpricetv);
        addcomments= (TextView) findViewById(R.id.addcomments);
        sendButton= (Button) findViewById(R.id.sendButton);
        web.setOnChartValueSelectedListener(this);

        commentEditText= (EditText) findViewById(R.id.commentEditText);
        open= (TextView) findViewById(R.id.open);
        low= (TextView) findViewById(R.id.low);
        high= (TextView) findViewById(R.id.high);
        close= (TextView) findViewById(R.id.close);
        date= (TextView) findViewById(R.id.date);
        coin=getIntent().getStringExtra("coin");
        coinprice=getIntent().getStringExtra("coinprice");
        market=getIntent().getStringExtra("market");
        coinpricetv.setText(coinprice);
        cointv.setText(coin);
       // Toast.makeText(this, market+"-"+coin, Toast.LENGTH_SHORT).show();
        sessionMarket.createLoginSession(market,coin);

            HashMap<String,String> hm1=sessionMarket.getCoinDetails();

            market=hm1.get(SessionMarket.KEY_MARKET);
            coin=hm1.get(SessionMarket.KEY_COIN);
        getSupportActionBar().setTitle(market);

        if (sessionManagement.isLoggedIn()){

            HashMap<String,String> hm=sessionManagement.getUserDetails();

            email=hm.get(SessionManagement.KEY_EMAIL);
            photo=hm.get(SessionManagement.KEY_PHOTOURI);
            getComments(market,coin);

        }
        addcomments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManagement.isLoggedIn()){
                    newCommentContainer.setVisibility(View.VISIBLE);

                }
                else {
                    Intent intent=new Intent(GraphActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();

                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String    date = df.format(c.getTime());
              //  Toast.makeText(GraphActivity.this, "sdsa"+email, Toast.LENGTH_SHORT).show();
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://cryptodoc.in/").build();
                MyInterface myinterface = restAdapter.create(MyInterface.class);
                myinterface.addComments(email, commentEditText.getText().toString(), date, market, coin, new retrofit.Callback<retrofit.client.Response>() {
                    @Override
                    public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                //        Toast.makeText(GraphActivity.this, "Posted", Toast.LENGTH_SHORT).show();
                        getComments(market,coin);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });



                //add in database and recyclerview
                // hide
                newCommentContainer.setVisibility(View.GONE);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(GraphActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        postdatalist.setLayoutManager(layoutManager);
       
     
    //    Toast.makeText(this, market+"h"+coin, Toast.LENGTH_SHORT).show();
        web.setOnChartValueSelectedListener(this);
        if(market != null && !market.isEmpty()) {
            if (market.equalsIgnoreCase("Bitfinex")) {
                if (coin.equalsIgnoreCase("BTC")) {

                    getChart("BTC", "USD", "60", "Bitfinex");
                    timeseries_hour.setChecked(true);

                }
                if (coin.equalsIgnoreCase("LTC")) {

                    getChart("LTC", "USD", "60", "Bitfinex");
                    timeseries_hour.setChecked(true);

                }
                if (coin.equalsIgnoreCase("XRP")) {

                    getChart("XRP", "USD", "60", "Bitfinex");
                    timeseries_hour.setChecked(true);

                }
                if (coin.equalsIgnoreCase("BCH")) {

                    getChart("BCH", "USD", "60", "Bitfinex");
                    timeseries_hour.setChecked(true);

                }  if (coin.equalsIgnoreCase("ETH")) {

                    getChart("ETH", "USD", "60", "Bitfinex");
                    timeseries_hour.setChecked(true);

                }

            }
            if (market.equalsIgnoreCase("Bitstamp")) {
                if (coin.equalsIgnoreCase("BTC")) {
                    getChart("BTC", "USD", "60", "Bitstamp");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("LTC")) {
                    getChart("LTC", "USD", "60", "Bitstamp");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("XRP")) {
                    getChart("XRP", "USD", "60", "Bitstamp");
                    timeseries_hour.setChecked(true);
                }


            }
            if (market.equalsIgnoreCase("BitTrex")) {
                if (coin.equalsIgnoreCase("BTC")) {

                    getChart("BTC", "USD", "60", "BitTrex");
                    timeseries_hour.setChecked(true);

                }
                if (coin.equalsIgnoreCase("ETH")) {

                    getChart("ETH", "USD", "60", "BitTrex");
                    timeseries_hour.setChecked(true);

                }
                if (coin.equalsIgnoreCase("XRP")) {

                    getChart("XRP", "USD", "60", "BitTrex");
                    timeseries_hour.setChecked(true);

                }
                if (coin.equalsIgnoreCase("LTC")) {

                    getChart("LTC", "USD", "60", "BitTrex");
                    timeseries_hour.setChecked(true);

                }
                if (coin.equalsIgnoreCase("BCH")) {

                    getChart("BCH", "USD", "60", "BitTrex");
                    timeseries_hour.setChecked(true);

                }
            }
            if (market.equalsIgnoreCase("Kraken")) {
                if (coin.equalsIgnoreCase("BTC")) {

                    getChart("BTC", "USD", "60", "Kraken");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("ETH")) {

                    getChart("ETH", "USD", "60", "Kraken");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("LTC")) {

                    getChart("LTC", "USD", "60", "Kraken");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("XRP")) {

                    getChart("XRP", "USD", "60", "Kraken");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("BCH")) {

                    getChart("BCH", "USD", "60", "Kraken");
                    timeseries_hour.setChecked(true);
                }
            }
            if (market.equalsIgnoreCase("Poloniex")) {
                if (coin.equalsIgnoreCase("BTC")) {

                    getChart("BTC", "USD", "60", "Poloniex");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("ETH")) {

                    getChart("ETH", "USD", "60", "Poloniex");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("LTC")) {

                    getChart("LTC", "USD", "60", "Poloniex");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("XRP")) {

                    getChart("XRP", "USD", "60", "Poloniex");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("BCH")) {

                    getChart("BCH", "USD", "60", "Poloniex");
                    timeseries_hour.setChecked(true);
                }
            }
            if (market.equalsIgnoreCase("BTCE")) {
                if (coin.equalsIgnoreCase("BTC")) {

                    getChart("BTC", "USD", "60", "BTCE");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("ETH")) {

                    getChart("ETH", "USD", "60", "BTCE");
                    timeseries_hour.setChecked(true);
                }
                if (coin.equalsIgnoreCase("LTC")) {

                    getChart("LTC", "USD", "60", "BTCE");
                    timeseries_hour.setChecked(true);
                }
            }
        }


                    timeseries.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch(i){
                    case R.id.timeseries_month:
                        if(market != null && !market.isEmpty()) {
                            if (market.equalsIgnoreCase("Bitfinex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                
                                    getBTCchartMonth("BTC", "USD", "30", "Bitfinex");


                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "30", "Bitfinex");


                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "30", "Bitfinex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                

                                    getBTCchartMonth("BCH", "USD", "30", "Bitfinex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "30", "Bitfinex");
                                }
                            }
                            if (market.equalsIgnoreCase("Bittrex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                
                                    getBTCchartMonth("BTC", "USD", "30", "BitTrex");


                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "30", "BitTrex");


                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "30", "BitTrex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                

                                    getBTCchartMonth("BCH", "USD", "30", "BitTrex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "30", "BitTrex");
                                }
                            }
                            if (market.equalsIgnoreCase("Bitstamp")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "30", "Bitstamp");

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "30", "Bitstamp");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "30", "Bitstamp");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                

                                    getBTCchartMonth("BCH", "USD", "30", "Bitstamp");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "30", "Bitstamp");

                                }
                            }
                            if (market.equalsIgnoreCase("Kraken")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                
                                    getBTCchartMonth("BTC", "USD", "30", "Kraken");

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "30", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "30", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                

                                    getBTCchartMonth("BCH", "USD", "30", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "30", "Kraken");

                                }
                            }
                            if (market.equalsIgnoreCase("Poloniex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "30", "Poloniex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "30", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                

                                    getBTCchartMonth("LTC", "USD", "30", "Poloniex");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                


                                    getBTCchartMonth("BCH", "USD", "30", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                

                                    getBTCchartMonth("XRP", "USD", "30", "Poloniex");

                                }

                            }
                            if (market.equalsIgnoreCase("BTCE")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                
                                    getBTCchartMonth("BTC", "USD", "30", "BTCE");

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                

                                    getBTCchartMonth("ETH", "USD", "30", "BTCE");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "30", "BTCE");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "30", "BTCE");


                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "30", "BTCE");


                                }

                            }
                        }
                            break;
                    case R.id.timeseries_three_month:
                        if(market != null && !market.isEmpty()) {

                            if (market.equalsIgnoreCase("Bittrex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "90", "BitTrex");
                

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getBTCchartMonth("LTC", "USD", "90", "BitTrex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "90", "BitTrex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "90", "BitTrex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                                    getBTCchartMonth("XRP", "USD", "90", "BitTrex");
                
                                }
                            }
                            if (market.equalsIgnoreCase("Bitfinex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                
                                    getBTCchartMonth("BTC", "USD", "90", "Bitfinex");


                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getBTCchartMonth("LTC", "USD", "90", "Bitfinex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                                    getBTCchartMonth("ETH", "USD", "90", "Bitfinex");
                
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "90", "Bitfinex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                                    getBTCchartMonth("XRP", "USD", "90", "Bitfinex");
                
                                }
                            }
                            if (market.equalsIgnoreCase("Bitstamp")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "90", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                                    getBTCchartMonth("ETH", "USD", "90", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getBTCchartMonth("LTC", "USD", "90", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("BCH")) {

                                    getBTCchartMonth("BCH", "USD", "90", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                                    getBTCchartMonth("XRP", "USD", "90", "Bitstamp");
                
                                }
                            }
                            if (market.equalsIgnoreCase("Kraken")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "90", "Kraken");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "90", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "90", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "90", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                                    getBTCchartMonth("XRP", "USD", "90", "Kraken");
                
                                }
                            }
                            if (market.equalsIgnoreCase("Poloniex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "90", "Poloniex");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "90", "Poloniex");
                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "90", "Poloniex");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {

                
                                    getBTCchartMonth("BCH", "USD", "90", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "90", "Poloniex");

                                }

                            }
                            if (market.equalsIgnoreCase("BTCE")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "90", "BTCE");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {

                                    getBTCchartMonth("ETH", "USD", "90", "BTCE");
                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "90", "BTCE");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "90", "BTCE");


                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "90", "BTCE");


                                }

                            }
                        }

                        break;
                    case R.id.timeseries_week:
                        if(market != null && !market.isEmpty()) {
                            if (market.equalsIgnoreCase("Bittrex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "7", "BitTrex");
                

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getBTCchartMonth("LTC", "USD", "7", "BitTrex");

                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "7", "BitTrex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "7", "BitTrex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                                    getBTCchartMonth("XRP", "USD", "7", "BitTrex");
                                }
                            }
                            if (market.equalsIgnoreCase("Bitfinex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "7", "Bitfinex");

                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getBTCchartMonth("LTC", "USD", "7", "Bitfinex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "7", "Bitfinex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "7", "Bitfinex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "7", "Bitfinex");
                                }
                            }
                            if (market.equalsIgnoreCase("Bitstamp")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "7", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                                    getBTCchartMonth("ETH", "USD", "7", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "7", "Bitstamp");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "7", "Bitstamp");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                                    getBTCchartMonth("XRP", "USD", "7", "Bitstamp");
                
                                }
                            }
                            if (market.equalsIgnoreCase("Kraken")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "7", "Kraken");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "7", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "7", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                

                                    getBTCchartMonth("BCH", "USD", "7", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "7", "Kraken");

                                }
                            }
                            if (market.equalsIgnoreCase("Poloniex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                
                                    getBTCchartMonth("BTC", "USD", "7", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "7", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "7", "Poloniex");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                

                                    getBTCchartMonth("BCH", "USD", "7", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "7", "Poloniex");

                                }

                            }
                            if (market.equalsIgnoreCase("BTCE")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "7", "BTCE");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {

                                    getBTCchartMonth("ETH", "USD", "7", "BTCE");
                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {

                
                                    getBTCchartMonth("LTC", "USD", "7", "BTCE");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {

                                    getBTCchartMonth("BCH", "USD", "7", "BTCE");
                

                                }
                                if (coin.equalsIgnoreCase("XRP")) {

                                    getBTCchartMonth("XRP", "USD", "7", "BTCE");
                

                                }

                            }
                        }
                        break;
                    case R.id.timeseries_day:
                        if(market != null && !market.isEmpty()) {
                            if (market.equalsIgnoreCase("Bittrex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "1", "BitTrex");

                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getBTCchartMonth("LTC", "USD", "1", "BitTrex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "1", "BitTrex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "1", "BitTrex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "1", "BitTrex");
                                }
                            }
                            if (market.equalsIgnoreCase("Bitfinex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "1", "Bitfinex");
                

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getBTCchartMonth("LTC", "USD", "1", "Bitfinex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "1", "Bitfinex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "1", "Bitfinex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                  getBTCchartMonth("XRP", "USD", "1", "Bitfinex");
                                }
                            }
                            if (market.equalsIgnoreCase("Bitstamp")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "1", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                                    getBTCchartMonth("ETH", "USD", "1", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                    getBTCchartMonth("LTC", "USD", "1", "Bitstamp");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "1", "Bitstamp");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                    getBTCchartMonth("XRP", "USD", "1", "Bitstamp");

                                }
                            }
                            if (market.equalsIgnoreCase("Kraken")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "1", "Kraken");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                     getBTCchartMonth("ETH", "USD", "1", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                   getBTCchartMonth("LTC", "USD", "1", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {

                    getBTCchartMonth("BCH", "USD", "1", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                                    getBTCchartMonth("XRP", "USD", "1", "Kraken");
                
                                }
                            }
                            if (market.equalsIgnoreCase("Poloniex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "1", "Poloniex");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {

                     getBTCchartMonth("ETH", "USD", "1", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "1", "Poloniex");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                

                                    getBTCchartMonth("BCH", "USD", "1", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "1", "Poloniex");

                                }

                            }
                            if (market.equalsIgnoreCase("BTCE")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getBTCchartMonth("BTC", "USD", "1", "BTCE");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getBTCchartMonth("ETH", "USD", "1", "BTCE");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getBTCchartMonth("LTC", "USD", "1", "BTCE");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getBTCchartMonth("BCH", "USD", "1", "BTCE");


                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getBTCchartMonth("XRP", "USD", "1", "BTCE");


                                }

                            }
                        }
                        break;
                    case R.id.timeseries_hour:
                        if(market != null && !market.isEmpty()) {
                            if (market.equalsIgnoreCase("Bitfinex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getChart("BTC", "USD", "60", "Bitfinex");
                

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getChart("LTC", "USD", "60", "Bitfinex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                   getChart("ETH", "USD", "60", "Bitfinex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getChart("BCH", "USD", "60", "Bitfinex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                    getChart("XRP", "USD", "60", "Bitfinex");
                                }
                            }
                            if (market.equalsIgnoreCase("Bitstamp")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getChart("BTC", "USD", "60", "Bitstamp");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                  getChart("ETH", "USD", "60", "Bitstamp");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                   getChart("LTC", "USD", "60", "Bitstamp");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getChart("BCH", "USD", "60", "Bitstamp");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                   getChart("XRP", "USD", "60", "Bitstamp");

                                }
                            }
                            if (market.equalsIgnoreCase("Bitfinex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getChart("BTC", "USD", "60", "Bitfinex");
                

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getChart("LTC", "USD", "60", "Bitfinex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getChart("ETH", "USD", "60", "Bitfinex");
                                }

                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getChart("BCH", "USD", "60", "Bitfinex");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getChart("XRP", "USD", "60", "Bitfinex");
                                }
                            }
                            if (market.equalsIgnoreCase("BitTrex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getChart("BTC", "USD", "60", "BitTrex");
                
                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                                    getChart("ETH", "USD", "60", "BitTrex");
                
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                                    getChart("LTC", "USD", "60", "BitTrex");
                

                                }
                                if (coin.equalsIgnoreCase("BCH")) {
                
                                    getChart("BCH", "USD", "60", "BitTrex");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                                    getChart("XRP", "USD", "60", "BitTrex");
                

                                }
                            }
                            if (market.equalsIgnoreCase("Kraken")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getChart("BTC", "USD", "60", "Kraken");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                
                                    getChart("ETH", "USD", "60", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                
                                    getChart("LTC", "USD", "60", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {

                
                                    getChart("BCH", "USD", "60", "Kraken");
                                }
                                if (coin.equalsIgnoreCase("XRP")) {
                
                                    getChart("XRP", "USD", "60", "Kraken");

                                }
                            }
                            if (market.equalsIgnoreCase("Poloniex")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getChart("BTC", "USD", "60", "Poloniex");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                

                                    getChart("ETH", "USD", "60", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {
                

                                    getChart("LTC", "USD", "60", "Poloniex");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {


                
                                    getChart("BCH", "USD", "60", "Poloniex");

                                }
                                if (coin.equalsIgnoreCase("XRP")) {

                
                                    getChart("XRP", "USD", "60", "Poloniex");

                                }

                            }
                            if (market.equalsIgnoreCase("BTCE")) {
                                if (coin.equalsIgnoreCase("BTC")) {
                                    getChart("BTC", "USD", "60", "BTCE");
                

                                }
                                if (coin.equalsIgnoreCase("ETH")) {
                

                                    getChart("ETH", "USD", "60", "BTCE");

                                }
                                if (coin.equalsIgnoreCase("LTC")) {

                
                                    getChart("LTC", "USD", "60", "BTCE");
                                }
                                if (coin.equalsIgnoreCase("BCH")) {

                
                                    getChart("BCH", "USD", "60", "BTCE");


                                }
                                if (coin.equalsIgnoreCase("XRP")) {

                
                                    getChart("XRP", "USD", "60", "BTCE");


                                }

                            }
                        }
                        break;

                }


            }
        });
        //market null check


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManagement.isLoggedIn()){

            HashMap<String,String> hm=sessionManagement.getUserDetails();

            email=hm.get(SessionManagement.KEY_EMAIL);
            photo=hm.get(SessionManagement.KEY_PHOTOURI);

        }
    }

    private void getComments(String market, String coin) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://cryptodoc.in/").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getPost(market,coin,new retrofit.Callback<retrofit.client.Response>() {
            @Override
            public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                try {
                    BufferedReader buffered=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;
                    StringBuilder stringbuilder=new StringBuilder();

                    while ((output=buffered.readLine())!=null){
                        stringbuilder.append(output);

                    }
                    Log.d("asd",""+stringbuilder);
                //    Toast.makeText(GraphActivity.this, "output"+output, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray=new JSONArray(""+stringbuilder);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObj=jsonArray.getJSONObject(i);
                        Comments comments=new Comments();
                        comments.setEmail(jsonObj.getString("email"));
                        comments.setMarketname(jsonObj.getString("marketname"));
                        comments.setMessage(jsonObj.getString("c_message"));
                        comments.setProfilepic(jsonObj.getString("photouri"));
                        comments.setDate(jsonObj.getString("c_dateandtime"));
                        arraylistcomments.add(comments);
                        commentAdapter = new CommentsAdapter(arraylistcomments, GraphActivity.this);
                        postdatalist.setAdapter(commentAdapter);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void getBTCchartMonth(String btc, String usd, String s, String bitfinex) {
        MyInterface apiInterface= ApiClient.getClient().create(MyInterface.class);

        Call<Example> responseBodyCall=apiInterface.getChartLTCMonth(btc,usd,s,bitfinex);
        responseBodyCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                ArrayList<String> al=new ArrayList<>();
                ArrayList<Entry> arrayList=new ArrayList<>();
                List<Datum> data=response.body().getData();
                for (int i=0;i<data.size();i++) {
                    Datum d = data.get(i);

                    al.add(""+getDate(d.getTime()));

                    Log.d("sdfs", "" + d.getClose());
                }

                for (int i=0;i<data.size();i++) {
                    Datum d = data.get(i);
                    Entry entry=new Entry(Float.valueOf(String.valueOf(d.getClose())),i);
                    arrayList.add(entry);
                    Log.d("sdfs", "" + d.getClose());
                }
                LineDataSet dataset = new LineDataSet(arrayList,"");
                dataset.setColor(Color.WHITE);

                dataset.setCircleSize(0f);
                LineData datas=new LineData(al,dataset);
                dataset.setValueTextColor(Color.TRANSPARENT);
                XAxis xAxis = web.getXAxis();
                xAxis.setTextColor(Color.WHITE);

                YAxis leftAxis = web.getAxisLeft();
                YAxis rightAxis = web.getAxisRight();
                rightAxis.setTextColor(Color.TRANSPARENT);


                leftAxis.setTextColor(Color.WHITE);

                web.setData(datas);
                web.invalidate();

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                //Toast.makeText(GraphActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void getChart(String btc, String usd, String s, String bitfinex) {
        MyInterface apiInterface= ApiClient.getClient().create(MyInterface.class);

        Call<Example> responseBodyCall=apiInterface.getChart(btc,usd,s,bitfinex);
        responseBodyCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                ArrayList<String> al=new ArrayList<>();
                ArrayList<Entry> arrayList=new ArrayList<>();
                List<Datum> data=response.body().getData();
                for (int i=0;i<data.size();i++) {
                    Datum d = data.get(i);

                    al.add(""+getDateandTime(d.getTime()));

                    Log.d("sdfsssdasd", "" + d.getClose());
                }

                for (int i=0;i<data.size();i++) {
                    Datum d = data.get(i);
                    Entry entry=new Entry(Float.valueOf(String.valueOf(d.getClose())),i);
                    arrayList.add(entry);
                    Log.d("sdfs", "" + d.getClose());
                }
                LineDataSet dataset = new LineDataSet(arrayList,"");
               dataset.setColor(Color.WHITE);
                dataset.setCircleSize(0f);
                LineData datas = new LineData(al, dataset);
                XAxis xAxis = web.getXAxis();
                xAxis.setTextColor(Color.WHITE);

                YAxis leftAxis = web.getAxisLeft();
                YAxis rightAxis = web.getAxisRight();
                rightAxis.setTextColor(Color.TRANSPARENT);


                leftAxis.setTextColor(Color.WHITE);

                dataset.setValueTextColor(Color.TRANSPARENT);
                web.setData(datas);
                web.invalidate();

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
               // Toast.makeText(GraphActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionMarket.clearCoin();
        finish();
        //Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }


    private String getDate(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM");
            Date netDate = (new Date(timeStamp*1000L));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    private String getDateandTime(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date netDate = (new Date(timeStamp*1000L));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
     /*   Entry datum=arrayList.get(e.getXIndex());*/
/*
       *//* open.setText(String.valueOf(datum.getOpen()));
        low.setText(String.valueOf(datum.getLow()));
        high.setText(String.valueOf(datum.getHigh()));
        close.setText(String.valueOf(datum.getClose()));*//*
        date.setText(String.valueOf(al.get(e.getXIndex())));*/
        //Toast.makeText(this, ""+e.getVal()+" "+datum.getHigh(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }




    ////////////////////Bitstamp

}
