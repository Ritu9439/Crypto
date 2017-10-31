package stock.cryptodocmarket.ForeignCompany;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

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
import stock.cryptodocmarket.MyInterface;

/**
 * Created by Administrator on 24-10-2017.
 */

public class ForeignService  extends Service {
    public static final String BROADCAST_ACTION = "stock.cryptodoc.foreign";
    Intent intent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "for", Toast.LENGTH_SHORT).show();

        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        getBittrex();
        Toast.makeText(this, "for", Toast.LENGTH_SHORT).show();
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
                    foreignmarket.setIMAGE("http://cryptodoc.in/cryptodocimg/btc.png");


                    al.add(foreignmarket);

                    JSONObject jobj1 = jobject.getJSONObject("ETH");
                    String btcusd1 = jobj1.getString("USD");
                    ForeignMarket foreignmarket1 = new ForeignMarket("ETH", "$ " + btcusd1, "", "");
                    foreignmarket1.setMARKETNAME("BitTrex");

                    foreignmarket1.setIMAGE("http://cryptodoc.in/cryptodocimg/eth.png");

                    al.add(foreignmarket1);
                    JSONObject jobj2 = jobject.getJSONObject("LTC");
                    String btcusd2 = jobj2.getString("USD");
                    ForeignMarket foreignmarket2 = new ForeignMarket("LTC", "$ " + btcusd2, "", "");
                    foreignmarket2.setMARKETNAME("BitTrex");
                    foreignmarket2.setIMAGE("http://cryptodoc.in/cryptodocimg/ltc.png");

                    al.add(foreignmarket2);
                    JSONObject jobj3 = jobject.getJSONObject("XRP");
                    String btcusd3 = jobj3.getString("USD");
                    ForeignMarket foreignmarket3 = new ForeignMarket("XRP", "$ " + btcusd3, "", "");
                    foreignmarket3.setMARKETNAME("BitTrex");
                    foreignmarket3.setIMAGE("http://cryptodoc.in/cryptodocimg/xrp.png");

                    al.add(foreignmarket3);
                    JSONObject jobj4 = jobject.getJSONObject("BCH");
                    String btcusd4 = jobj4.getString("USD");
                    ForeignMarket foreignmarket4 = new ForeignMarket("BCH", "$ " + btcusd4, "", "");
                    foreignmarket4.setMARKETNAME("BitTrex");
                    foreignmarket4.setIMAGE("http://cryptodoc.in/cryptodocimg/btc.png");

                    al.add(foreignmarket4);

                    intent.putParcelableArrayListExtra("foreignList", al);

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
}
