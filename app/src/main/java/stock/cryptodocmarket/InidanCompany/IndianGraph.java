package stock.cryptodocmarket.InidanCompany;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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

import static stock.cryptodocmarket.R.id.web;

public class IndianGraph extends AppCompatActivity {
    String market = "";
    RadioButton timeseries_hour;
    RadioGroup timeseries;
SessionManagement sessionManagement;
    LinearLayout newCommentContainer;
    TextView addcomments;
    LineChart lineChart;
    private EditText commentEditText;
    Button sendButton;
    String email,photo;
    RecyclerView postdatalist;
    private String coin="BTC";
    ArrayList<Comments> arraylistcomments=new ArrayList<>();
    CommentsAdapter commentAdapter;
    SessionMarket sessionMarket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagement=new SessionManagement(IndianGraph.this);
        sessionMarket=new SessionMarket(IndianGraph.this);
        setContentView(R.layout.activity_indian_graph);
        newCommentContainer= (LinearLayout) findViewById(R.id.newCommentContainer);
        commentEditText= (EditText) findViewById(R.id.commentEditText);
        sendButton= (Button) findViewById(R.id.sendButton);
        postdatalist= (RecyclerView) findViewById(R.id.postdatalist);
RecyclerView.LayoutManager layout=new LinearLayoutManager(IndianGraph.this);
        postdatalist.setLayoutManager(layout);
        timeseries_hour = (RadioButton) findViewById(R.id.timeseries_hour);
        timeseries= (RadioGroup) findViewById(R.id.timeseries);
        addcomments= (TextView) findViewById(R.id.addcomments);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        market = getIntent().getStringExtra("market");
        sessionMarket.createLoginSession(market,coin);
        timeseries.setVisibility(View.VISIBLE);
        Toast.makeText(this, ""+market, Toast.LENGTH_SHORT).show();
        if (sessionManagement.isLoggedIn()){

            HashMap<String,String> hm=sessionManagement.getUserDetails();

            email=hm.get(SessionManagement.KEY_EMAIL);
            photo=hm.get(SessionManagement.KEY_PHOTOURI);
            getComments(market,coin);

        }

            HashMap<String,String> hm1=sessionMarket.getCoinDetails();

            market=hm1.get(SessionMarket.KEY_MARKET);
            coin=hm1.get(SessionMarket.KEY_COIN);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();

                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String    date = df.format(c.getTime());
                Toast.makeText(IndianGraph.this, "sdsa"+email, Toast.LENGTH_SHORT).show();
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://cryptodoc.in/").build();
                MyInterface myinterface = restAdapter.create(MyInterface.class);
                myinterface.addComments(email, commentEditText.getText().toString(), date, market, coin, new retrofit.Callback<retrofit.client.Response>() {
                    @Override
                    public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                        Toast.makeText(IndianGraph.this, "Posted", Toast.LENGTH_SHORT).show();
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
        addcomments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManagement.isLoggedIn()){
                    newCommentContainer.setVisibility(View.VISIBLE);

                }
                else {
                    Intent intent=new Intent(IndianGraph.this, UserActivity.class);
                    startActivity(intent);
                }
            }
        });
        if (market != null && !market.isEmpty()) {
            timeseries.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    switch(i){
                        case R.id.timeseries_month:
                            if(market != null && !market.isEmpty()) {
                                if (market.equalsIgnoreCase("LocalBitcoins")) {

                                    getBTCchartMonth("BTC", "INR", "30", "LocalBitcoins");



                                }

                                if (market.equalsIgnoreCase("Unocoin")) {

                                    getBTCchartMonth("BTC", "INR", "30", "Unocoin");



                                }
                                if (market.equalsIgnoreCase("EthexIndia")) {

                                    getBTCchartMonth("ETH", "INR", "30", "EthexIndia");



                                }
                            }
                            break;
                        case R.id.timeseries_three_month:
                            if(market != null && !market.isEmpty()) {
                                if (market.equalsIgnoreCase("LocalBitcoins")) {

                                    getBTCchartMonth("BTC", "INR", "90", "LocalBitcoins");



                                }
                                if (market.equalsIgnoreCase("Unocoin")) {

                                    getBTCchartMonth("BTC", "INR", "90", "Unocoin");



                                }
                                if (market.equalsIgnoreCase("EthexIndia")) {

                                    getBTCchartMonth("ETH", "INR", "90", "EthexIndia");



                                }
                            }
                            break;
                        case R.id.timeseries_week:
                            if(market != null && !market.isEmpty()) {
                                if (market.equalsIgnoreCase("LocalBitcoins")) {

                                    getBTCchartMonth("BTC", "INR", "7", "LocalBitcoins");



                                }
                                if (market.equalsIgnoreCase("Unocoin")) {

                                    getBTCchartMonth("BTC", "INR", "7", "Unocoin");



                                }
                                if (market.equalsIgnoreCase("EthexIndia")) {

                                    getBTCchartMonth("ETH", "INR", "7", "EthexIndia");



                                }
                            }
                            break;
                        case R.id.timeseries_hour:
                            if(market != null && !market.isEmpty()) {
                                if (market.equalsIgnoreCase("LocalBitcoins")) {
                                    getChart("BTC", "USD", "60", "LocalBitcoins");


                                }
                                if (market.equalsIgnoreCase("Unocoin")) {

                                    getChart("BTC", "INR", "60", "Unocoin");



                                }
                                if (market.equalsIgnoreCase("EthexIndia")) {

                                    getChartETH();



                                }
                            }
                            break;
                        case R.id.timeseries_day:
                            if(market != null && !market.isEmpty()) {
                                if (market.equalsIgnoreCase("LocalBitcoins")) {

                                    getBTCchartMonth("BTC", "USD", "1", "LocalBitcoins");


                                }
                                if (market.equalsIgnoreCase("Unocoin")) {

                                    getBTCchartMonth("BTC", "INR", "1", "Unocoin");



                                }
                                if (market.equalsIgnoreCase("EthexIndia")) {

                                    getBTCchartMonth("ETH", "INR", "1", "EthexIndia");



                                }
                            }
                            break;
                    }
                }
            });
            getSupportActionBar().setTitle(market);
            if (market.equalsIgnoreCase("LocalBitcoins")) {

                getChart("BTC", "INR", "60", "LocalBitcoins");
                timeseries_hour.setChecked(true);

            }
            if (market.equalsIgnoreCase("Unocoin")) {
                getChart("BTC", "INR", "60", "Unocoin");
                timeseries_hour.setChecked(true);


            }

            if (market.equalsIgnoreCase("EthexIndia")) {
                getChartETH();
                timeseries_hour.setChecked(true);


            }
            if (market.equalsIgnoreCase("Coin Secure")){
                getGraph();
                timeseries.setVisibility(View.GONE);
            }
            if (market.equalsIgnoreCase("Zebapi")){
                getGraph();
                timeseries.setVisibility(View.GONE);
            }
            if (market.equalsIgnoreCase("BitXOXO")){
                getGraph();
                timeseries.setVisibility(View.GONE);
            }
        }
        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (market.equalsIgnoreCase("LocalBitcoins")) {

                    getChart("BTC", "INR", "60", "LocalBitcoins");
                    timeseries_hour.setChecked(true);

                }
                if (market.equalsIgnoreCase("Unocoin")) {
                    getChart("BTC", "INR", "60", "Unocoin");
                    timeseries_hour.setChecked(true);


                }

                if (market.equalsIgnoreCase("EthexIndia")) {
                    getChartETH();
                    timeseries_hour.setChecked(true);


                }
                if (market.equalsIgnoreCase("Coin Secure")){
                    getGraph();
                    timeseries.setVisibility(View.GONE);

                }
                if (market.equalsIgnoreCase("Zebapi")){
                    getGraph();
                    timeseries.setVisibility(View.GONE);
                }
                if (market.equalsIgnoreCase("BitXOXO")){
                    getGraph();
                    timeseries.setVisibility(View.GONE);
                }

            }
        }, 8000);

            }

    private void getGraph() {
        lineChart= (LineChart) findViewById(web);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://cryptodoc.in").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getGraph(market, new retrofit.Callback<retrofit.client.Response>() {
            @Override
            public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                ArrayList<String> al=new ArrayList<>();
                ArrayList<Entry> arrayList=new ArrayList<>();

                try {
                    BufferedReader buffered=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;
                    StringBuilder stringbuilder=new StringBuilder();
                    while ((output=buffered.readLine())!=null){
                        stringbuilder.append(output);

                    }
                    JSONArray jsonArray=new JSONArray(""+stringbuilder);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);



                            Entry entry=new Entry(Float.parseFloat(String.valueOf(jsonObj.getString("ask"))),i);
                            arrayList.add(entry);

                    al.add(jsonObj.getString("time"));


                    }
                    LineDataSet dataset = new LineDataSet(arrayList,"");
                    dataset.setColor(Color.WHITE);
                    dataset.setCircleSize(0f);
                    LineData datas = new LineData(al, dataset);
                    dataset.setValueTextColor(Color.TRANSPARENT);

                    lineChart.setData(datas);
                    lineChart.invalidate();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionMarket.clearCoin();
        finish();
        Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
    }

    private void getComments(String market, String coin) {
        lineChart= (LineChart) findViewById(web);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://cryptodoc.in").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getPost(market,"BTC",new retrofit.Callback<retrofit.client.Response>() {
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
                    Toast.makeText(IndianGraph.this, "output"+output, Toast.LENGTH_SHORT).show();
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
                        commentAdapter = new CommentsAdapter(arraylistcomments, IndianGraph.this);
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
    private void getBTCchartMonth(String btc, String inr, String s, String localBitcoins) {
        MyInterface apiInterface= ApiClient.getClient().create(MyInterface.class);

        lineChart= (LineChart) findViewById(web);

        Call<Example> responseBodyCall=apiInterface.getChartLTCMonth(btc,inr,s,localBitcoins);
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
                    Entry entry=new Entry(Float.parseFloat(String.valueOf(d.getClose())),i);
                    arrayList.add(entry);
                    Log.d("sdfs", "" + d.getClose());
                }
                LineDataSet dataset = new LineDataSet(arrayList,"");
                dataset.setColor(Color.WHITE);
                dataset.setCircleSize(0f);
                LineData datas = new LineData(al, dataset);
                dataset.setValueTextColor(Color.TRANSPARENT);
                lineChart.setData(datas);
                lineChart.invalidate();

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            }
        });
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

    private void getChartETH() {
            MyInterface apiInterface= ApiClient.getClient().create(MyInterface.class);
        lineChart= (LineChart) findViewById(web);

        Call<Example> responseBodyCall=apiInterface.getChartsETH();
            responseBodyCall.enqueue(new Callback<Example>() {

                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    ArrayList<String> al=new ArrayList<>();
                    ArrayList<Entry> arrayList=new ArrayList<>();
                    List<Datum> data=response.body().getData();
                    for (int i=0;i<data.size();i++) {
                        Datum d = data.get(i);

                        al.add(""+getDateandTime(d.getTime()));

                        Log.d("sdfs", "" + d.getClose());
                    }

                    for (int i=0;i<data.size();i++) {
                        Datum d = data.get(i);
                        Entry entry=new Entry(Float.parseFloat(String.valueOf(d.getClose())),i);
                        arrayList.add(entry);
                        Log.d("sdfs", "" + d.getClose());
                    }
                    LineDataSet dataset = new LineDataSet(arrayList,"");
                    dataset.setColor(Color.WHITE);

                    dataset.setCircleSize(0f);

                    LineData datas = new LineData(al, dataset);
                    datas.setValueTextColor(Color.TRANSPARENT);

                    lineChart.setData(datas);
                    lineChart.invalidate();

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                }
            });


        }



    private void getChart(String btc, String usd, String s, String bitfinex) {
        lineChart= (LineChart) findViewById(web);

        Toast.makeText(IndianGraph.this, "called", Toast.LENGTH_SHORT).show();
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

                    Log.d("sdfs", "" + d.getClose());
                }

                for (int i=0;i<data.size();i++) {
                    Datum d = data.get(i);

                    Entry entry=new Entry(Float.parseFloat(String.valueOf(d.getClose())),i);
                    arrayList.add(entry);
                    Log.d("sdfs", "" + d.getClose());
                }
                LineDataSet dataset = new LineDataSet(arrayList,"");
                dataset.setColor(Color.WHITE);
                dataset.setCircleSize(0f);
                LineData datas = new LineData(al, dataset);
                dataset.setValueTextColor(Color.TRANSPARENT);
                lineChart.setData(datas);
                lineChart.invalidate();

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            }
        });


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
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            // Do stuff
            finish();

        }
        return true;
    }
}
