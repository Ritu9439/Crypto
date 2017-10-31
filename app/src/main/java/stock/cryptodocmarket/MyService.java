package stock.cryptodocmarket;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import stock.cryptodocmarket.ForeignCompany.ForeignMarket;
import stock.cryptodocmarket.InidanCompany.IndianMarket;

/**
 * Created by Administrator on 23-10-2017.
 */

public class MyService extends Service {
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "stock.cryptodoc";
    private final Handler handler = new Handler();
    Intent intent;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();
        Log.d("servicess",""+System.currentTimeMillis());
        intent = new Intent(BROADCAST_ACTION);
    }
    private void getBittrex() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://min-api.cryptocompare.com").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getBitrexData(new Callback<Response>() {

            @Override
            public void success(Response response, Response response2) {
                StringBuilder stringbuilder=new StringBuilder();
                ArrayList<ForeignMarket> al=new ArrayList<ForeignMarket>();
                try {
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }

                    JSONObject jobject = new JSONObject("" + stringbuilder);
                    JSONObject jobj = jobject.getJSONObject("BTC");
                    String btcusd = jobj.getString("USD");



                    ForeignMarket foreignmarket = new ForeignMarket("BTC", "$ " + btcusd, "", "");
                    foreignmarket.setMARKETNAME("BitTrex");
                    foreignmarket.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");


                    al.add(foreignmarket);

                    JSONObject jobj1 = jobject.getJSONObject("ETH");
                    String btcusd1 = jobj1.getString("USD");
                    ForeignMarket foreignmarket1 = new ForeignMarket("ETH", "$ " + btcusd1, "", "");
                    foreignmarket1.setMARKETNAME("BitTrex");

                    foreignmarket1.setIMAGE("http:/cryptodoc.in/cryptodocimg/eth.png");

                    al.add(foreignmarket1);
                    JSONObject jobj2 = jobject.getJSONObject("LTC");
                    String btcusd2 = jobj2.getString("USD");
                    ForeignMarket foreignmarket2 = new ForeignMarket("LTC", "$ " + btcusd2, "", "");
                    foreignmarket2.setMARKETNAME("BitTrex");
                    foreignmarket2.setIMAGE("http:/cryptodoc.in/cryptodocimg/ltc.png");

                    al.add(foreignmarket2);
                    JSONObject jobj3 = jobject.getJSONObject("XRP");
                    String btcusd3 = jobj3.getString("USD");
                    ForeignMarket foreignmarket3 = new ForeignMarket("XRP", "$ " + btcusd3, "", "");
                    foreignmarket3.setMARKETNAME("BitTrex");
                    foreignmarket3.setIMAGE("http:/cryptodoc.in/cryptodocimg/xrp.png");

                    al.add(foreignmarket3);
                    JSONObject jobj4 = jobject.getJSONObject("BCH");
                    String btcusd4 = jobj4.getString("USD");
                    ForeignMarket foreignmarket4 = new ForeignMarket("BCH", "$ " + btcusd4, "", "");
                    foreignmarket4.setMARKETNAME("BitTrex");
                    foreignmarket4.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");

                    al.add(foreignmarket4);


                    intent.putParcelableArrayListExtra("bittrex", al);


                    getBitfinex();
                    Toast.makeText(MyService.this, "OKKK", Toast.LENGTH_SHORT).show();

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

    private void getBitfinex() {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://min-api.cryptocompare.com/").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getBitfinex(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ArrayList<ForeignMarket> al=new ArrayList<ForeignMarket>();

                StringBuilder stringbuilder=new StringBuilder();

                try {
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }

                    JSONObject jobject = new JSONObject("" + stringbuilder);
                    JSONObject jobj = jobject.getJSONObject("BTC");
                    String btcusd = jobj.getString("USD");

                    ForeignMarket foreignmarket = new ForeignMarket("BTC", "$ " + btcusd, "", "");
                    foreignmarket.setMARKETNAME("Bitfinex");
                    foreignmarket.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");

                    al.add(foreignmarket);

                    JSONObject jobj1 = jobject.getJSONObject("ETH");
                    String btcusd1 = jobj1.getString("USD");
                    ForeignMarket foreignmarket1 = new ForeignMarket("ETH", "$ " + btcusd1, "", "");
                    foreignmarket1.setMARKETNAME("Bitfinex");
                    foreignmarket1.setIMAGE("http:/cryptodoc.in/cryptodocimg/eth.png");

                    al.add(foreignmarket1);
                    JSONObject jobj2 = jobject.getJSONObject("LTC");
                    String btcusd2 = jobj2.getString("USD");
                    ForeignMarket foreignmarket2 = new ForeignMarket("LTC", "$ " + btcusd2, "", "");
                    foreignmarket2.setIMAGE("http:/cryptodoc.in/cryptodocimg/ltc.png");

                    foreignmarket2.setMARKETNAME("Bitfinex");
                    al.add(foreignmarket2);
                    JSONObject jobj3 = jobject.getJSONObject("XRP");
                    String btcusd3 = jobj3.getString("USD");
                    ForeignMarket foreignmarket3 = new ForeignMarket("XRP", "$ " + btcusd3, "", "");
                    foreignmarket3.setIMAGE("http:/cryptodoc.in/cryptodocimg/xrp.png");

                    foreignmarket3.setMARKETNAME("Bitfinex");
                    al.add(foreignmarket3);
                    JSONObject jobj4 = jobject.getJSONObject("BCH");
                    String btcusd4 = jobj4.getString("USD");
                    ForeignMarket foreignmarket4 = new ForeignMarket("BCH", "$ " + btcusd4, "", "");
                    foreignmarket4.setMARKETNAME("Bitfinex");
                    foreignmarket4.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");

                    al.add(foreignmarket4);
                    intent.putParcelableArrayListExtra("bitfinex", al);

                    getBitStamp();



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

    private void getBitStamp() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://min-api.cryptocompare.com").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getBitstamp(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ArrayList<ForeignMarket> al=new ArrayList<ForeignMarket>();

                StringBuilder stringbuilder=new StringBuilder();

                try {
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }

                    JSONObject jobject = new JSONObject("" + stringbuilder);
                    JSONObject jobj = jobject.getJSONObject("BTC");
                    String btcusd = jobj.getString("USD");

                    ForeignMarket foreignmarket = new ForeignMarket("BTC", "$ " + btcusd, "", "");
                    foreignmarket.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");
                    foreignmarket.setMARKETNAME("Bitstamp");

                    al.add(foreignmarket);

                    JSONObject jobj2 = jobject.getJSONObject("LTC");
                    String btcusd2 = jobj2.getString("USD");
                    ForeignMarket foreignmarket2 = new ForeignMarket("LTC", "$ " + btcusd2, "", "");
                    foreignmarket2.setMARKETNAME("Bitstamp");
                    foreignmarket2.setIMAGE("http:/cryptodoc.in/cryptodocimg/ltc.png");

                    al.add(foreignmarket2);
                    JSONObject jobj3 = jobject.getJSONObject("XRP");
                    String btcusd3 = jobj3.getString("USD");
                    ForeignMarket foreignmarket3 = new ForeignMarket("XRP", "$ " + btcusd3, "", "");
                    foreignmarket3.setMARKETNAME("Bitstamp");
                    foreignmarket3.setIMAGE("http:/cryptodoc.in/cryptodocimg/xrp.png");

                    al.add(foreignmarket3);

                    intent.putParcelableArrayListExtra("bitstamp", al);
                    getPoloniex();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MyService.this, "bitstamp"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPoloniex() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://min-api.cryptocompare.com").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getPoloniex(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ArrayList<ForeignMarket> al=new ArrayList<ForeignMarket>();

                StringBuilder stringbuilder=new StringBuilder();
                try {
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }

                    JSONObject jobject = new JSONObject("" + stringbuilder);
                    JSONObject jobj = jobject.getJSONObject("BTC");
                    String btcusd = jobj.getString("USD");

                    ForeignMarket foreignmarket = new ForeignMarket("BTC", "$ " + btcusd, "", "");
                    foreignmarket.setMARKETNAME("Poloniex");
                    foreignmarket.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");

                    al.add(foreignmarket);

                    JSONObject jobj1 = jobject.getJSONObject("ETH");
                    String btcusd1 = jobj1.getString("USD");
                    ForeignMarket foreignmarket1 = new ForeignMarket("ETH", "$ " + btcusd1, "", "");
                    foreignmarket1.setMARKETNAME("Poloniex");
                    foreignmarket1.setIMAGE("http:/cryptodoc.in/cryptodocimg/eth.png");

                    al.add(foreignmarket1);
                    JSONObject jobj2 = jobject.getJSONObject("LTC");
                    String btcusd2 = jobj2.getString("USD");
                    ForeignMarket foreignmarket2 = new ForeignMarket("LTC", "$ " + btcusd2, "", "");
                    foreignmarket2.setMARKETNAME("Poloniex");
                    foreignmarket2.setIMAGE("http:/cryptodoc.in/cryptodocimg/ltc.png");

                    al.add(foreignmarket2);
                    JSONObject jobj3 = jobject.getJSONObject("XRP");
                    String btcusd3 = jobj3.getString("USD");
                    ForeignMarket foreignmarket3 = new ForeignMarket("XRP", "$ " + btcusd3, "", "");
                    foreignmarket3.setMARKETNAME("Poloniex");
                    foreignmarket3.setIMAGE("http:/cryptodoc.in/cryptodocimg/xrp.png");

                    al.add(foreignmarket3);
                    JSONObject jobj4 = jobject.getJSONObject("BCH");
                    String btcusd4 = jobj4.getString("USD");
                    ForeignMarket foreignmarket4 = new ForeignMarket("BCH", "$ " + btcusd4, "", "");
                    foreignmarket4.setMARKETNAME("Poloniex");
                    foreignmarket4.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");

                    al.add(foreignmarket4);

                    intent.putParcelableArrayListExtra("poloniex", al);
                    getKraken();


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

    private void getKraken() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://min-api.cryptocompare.com").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getKraken(new Callback<Response>() {
            ArrayList<ForeignMarket> al=new ArrayList<ForeignMarket>();

            StringBuilder stringbuilder=new StringBuilder();
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }

                    JSONObject jobject = new JSONObject("" + stringbuilder);
                    JSONObject jobj = jobject.getJSONObject("BTC");
                    String btcusd = jobj.getString("USD");

                    ForeignMarket foreignmarket = new ForeignMarket("BTC", "$ " + btcusd, "", "");
                    foreignmarket.setMARKETNAME("Kraken");
                    foreignmarket.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");
                    al.add(foreignmarket);

                    JSONObject jobj1 = jobject.getJSONObject("ETH");
                    String btcusd1 = jobj1.getString("USD");
                    ForeignMarket foreignmarket1 = new ForeignMarket("ETH", "$ " + btcusd1, "", "");
                    foreignmarket1.setMARKETNAME("Kraken");
                    foreignmarket1.setIMAGE("http:/cryptodoc.in/cryptodocimg/eth.png");

                    al.add(foreignmarket1);
                    JSONObject jobj2 = jobject.getJSONObject("LTC");
                    String btcusd2 = jobj2.getString("USD");
                    ForeignMarket foreignmarket2 = new ForeignMarket("LTC", "$ " + btcusd2, "", "");
                    foreignmarket2.setMARKETNAME("Kraken");
                    foreignmarket2.setIMAGE("http:/cryptodoc.in/cryptodocimg/ltc.png");

                    al.add(foreignmarket2);
                    JSONObject jobj3 = jobject.getJSONObject("XRP");
                    String btcusd3 = jobj3.getString("USD");
                    ForeignMarket foreignmarket3 = new ForeignMarket("XRP", "$ " + btcusd3, "", "");
                    foreignmarket3.setMARKETNAME("Kraken");
                    foreignmarket3.setIMAGE("http:/cryptodoc.in/cryptodocimg/xrp.png");

                    al.add(foreignmarket3);
                    JSONObject jobj4 = jobject.getJSONObject("BCH");
                    String btcusd4 = jobj4.getString("USD");
                    ForeignMarket foreignmarket4 = new ForeignMarket("BCH", "$ " + btcusd4, "", "");
                    foreignmarket4.setMARKETNAME("Kraken");
                    foreignmarket4.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");

                    al.add(foreignmarket4);
                    intent.putParcelableArrayListExtra("kraken", al);
                    getBTCE();


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

    private void getBTCE() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://min-api.cryptocompare.com").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getBTCE(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ArrayList<ForeignMarket> al=new ArrayList<ForeignMarket>();

                StringBuilder stringbuilder=new StringBuilder();
                try {
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }

                    JSONObject jobject = new JSONObject("" + stringbuilder);
                    JSONObject jobj = jobject.getJSONObject("BTC");
                    String btcusd = jobj.getString("USD");

                    ForeignMarket foreignmarket = new ForeignMarket("BTC", "$ " + btcusd, "", "");
                    foreignmarket.setIMAGE("http:/cryptodoc.in/cryptodocimg/btc.png");
                    foreignmarket.setMARKETNAME("BTCE");
                    al.add(foreignmarket);

                    JSONObject jobj1 = jobject.getJSONObject("ETH");
                    String btcusd1 = jobj1.getString("USD");
                    ForeignMarket foreignmarket1 = new ForeignMarket("ETH", "$ " + btcusd1, "", "");
                    foreignmarket1.setMARKETNAME("BTCE");
                    foreignmarket1.setIMAGE("http:/cryptodoc.in/cryptodocimg/eth.png");


                    al.add(foreignmarket1);
                    JSONObject jobj2 = jobject.getJSONObject("LTC");
                    String btcusd2 = jobj2.getString("USD");
                    ForeignMarket foreignmarket2 = new ForeignMarket("LTC", "$ " + btcusd2, "", "");
                    foreignmarket2.setMARKETNAME("BTCE");
                    foreignmarket2.setIMAGE("http:/cryptodoc.in/cryptodocimg/ltc.png");

                    al.add(foreignmarket2);
                    intent.putParcelableArrayListExtra("BTCE", al);

                    sendBroadcast(intent);



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

    private void getCoinSecure() {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.coinsecure.in").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getCoinsecureApi(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                ArrayList<IndianMarket> indianMarkets=new ArrayList<>();

                try {

                    int counter = 0;
                    StringBuilder stringbuilder=new StringBuilder();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }
                    JSONObject jsonobiect = new JSONObject("" + stringbuilder);
                    JSONObject childobj = jsonobiect.getJSONObject("message");
                    Toast.makeText(getApplicationContext(), " Data Refreshed", Toast.LENGTH_LONG).show();


                    String buy = childobj.getString("ask");
                    String sell = childobj.getString("bid");
                    String url="https://api.coinsecure.in";
                    String coin="BTC";
                    String market="Coin Secure";
                    String time=childobj.getString("timestamp");
                    IndianMarket indianMarket = new IndianMarket(market, buy.substring(0, buy.length() - 2), sell.substring(0, sell.length() - 2), url, coin,time);
indianMarkets.add(indianMarket);
                    Log.d("graphadded",""+indianMarkets+" "+buy.substring(0, buy.length() - 2)+" ");
                    addGraph(market,buy.substring(0, buy.length() - 2),time);
                   callZebapi("https://www.zebapi.com",indianMarkets);

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

    private void addGraph(String market, String buy, String time) {
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint("http://cryptodoc.in/").build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.addGraph(market, buy, time, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(MyService.this, "added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MyService.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        getCoinSecure();



    }


    private void callZebapi(final String s, final ArrayList<IndianMarket> indianMarkets){
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getZebapi(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    StringBuilder stringbuilder=new StringBuilder();

                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder.append(output);
                    }
                    JSONObject jsonobiect=new JSONObject(""+stringbuilder);
                    String buy=jsonobiect.getString("buy");
                    String sell=jsonobiect.getString("sell");


                    IndianMarket indianMarket=new IndianMarket("Zebapi",buy,sell,s,"BTC","");
                    indianMarkets.add(indianMarket);
                    addGraph("Zebapi",buy,sell);
                    callUnocoin("https://www.unocoin.com",indianMarkets);


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
    private void callUnocoin(final String s, final ArrayList<IndianMarket> indianMarkets){
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getUnocoinApi(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    StringBuilder stringbuilder=new StringBuilder();

                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder.append(output);
                    }
                    JSONObject jsonobiect=new JSONObject(""+stringbuilder);
                    String buy=jsonobiect.getString("buy");
                    String sell=jsonobiect.getString("sell");
                    IndianMarket indianMarket=new IndianMarket("Unocoin",buy,sell,s,"BTC","");

                    indianMarkets.add(indianMarket);
                    getLocalBitCoins("https://www.cryptocompare.com/",indianMarkets);


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
    public void getLocalBitCoins(final String s , final ArrayList<IndianMarket> indianMarkets){
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getLocalBitCoins(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    StringBuilder stringbuilder=new StringBuilder();
                    IndianMarket indianMarket=new IndianMarket();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;
                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }
                    JSONObject jsonobiect = new JSONObject("" + stringbuilder);
                    String resp=jsonobiect.getString("Response");
                    if (resp!=null && !resp.isEmpty())
                    {
                        JSONObject childjson=jsonobiect.getJSONObject("Data");
                        JSONArray jsonArray=childjson.getJSONArray("Exchanges");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            String localbitcoins=object.getString("MARKET");
                            if (localbitcoins!=null && !localbitcoins.isEmpty()){
                                if (localbitcoins.equalsIgnoreCase("LocalBitcoins")) {
                                    String localprice = object.getString("PRICE");
                                    indianMarket.setUrl(s);
                                    indianMarket.setBuy(localprice);
                                    indianMarket.setCoin("BTC");
                                    indianMarket.setMarket("LocalBitcoins");



                                }
                            }

                        }
                        getLocalbitCoinssell("https://localbitcoins.com/",indianMarkets,indianMarket);




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
    private void getLocalbitCoinssell(String s, final ArrayList<IndianMarket> indianMarkets, final IndianMarket indianMarket) {
        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        final StringBuilder stringbuilder=new StringBuilder();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getLocalbitcoinssell(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder.append(output);
                    }
                    JSONObject jsonobiect=new JSONObject(""+stringbuilder);
                    JSONObject childobj=jsonobiect.getJSONObject("data");
                    JSONArray jsonArray=childobj.getJSONArray("ad_list");
                    JSONObject jobj=jsonArray.getJSONObject(0);
                    JSONObject dataobj=jobj.getJSONObject("data");
                    String temp_price=dataobj.getString("temp_price");

                    indianMarket.setSell(temp_price);
                    indianMarket.setTime("");
                    indianMarkets.add(indianMarket);
                    getEthexIndia("https://ethexindia.com/",indianMarkets);


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
    private void getEthexIndia(final String s, final ArrayList<IndianMarket> indianMarkets) {
        final StringBuilder stringbuilder=new StringBuilder();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getEthexIndia(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    while ((output = buffer.readLine()) != null) {
                        stringbuilder.append(output);
                    }
                    JSONObject jsonobiect = new JSONObject("" + stringbuilder);
                    String buy = jsonobiect.getString("last_traded_price");
                    long last_traded_time = jsonobiect.getLong("last_traded_time_IST");
                    IndianMarket indianMarket = new IndianMarket("ETHEXIndia", buy, "-", s, "BTC","");
                    indianMarkets.add(indianMarket);

                    getBitXoxo("https://api.bitxoxo.com/",indianMarkets);

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

    private void getBitXoxo(final String s, final ArrayList<IndianMarket> indianMarkets) {

        RestAdapter restAdapter=new RestAdapter.Builder().setEndpoint(s).build();
        MyInterface myinterface=restAdapter.create(MyInterface.class);
        myinterface.getBitxoxo(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                 StringBuilder stringbuilder=new StringBuilder();

                try {
                    BufferedReader buffer=new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output=null;

                    while ((output=buffer.readLine())!=null){
                        stringbuilder.append(output);
                    }
                    JSONObject jsonobiect=new JSONObject(""+stringbuilder);
                    String buy=jsonobiect.getString("buy");
                    String sell=jsonobiect.getString("sell");
                    addGraph("BitXOXO",buy,sell);
                    IndianMarket marketData=new IndianMarket("BitXOXO",buy,sell,s,"BTC","");
                   indianMarkets.add(marketData);
                    intent.putParcelableArrayListExtra("indianlist", indianMarkets);

                   getBittrex();


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
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Servics Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}