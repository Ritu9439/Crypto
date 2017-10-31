package stock.cryptodocmarket;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit2.Call;

/**
 * Created by Administrator on 23-10-2017.
 */

public interface MyInterface {
    @GET("/v1/exchange/ticker")
    void getCoinsecureApi(Callback<Response> responseCallback);
    @GET("/api/v1/market/ticker/btc/inr")
    void getZebapi(Callback<Response> responseCallback);
    @GET("/trade?all")
    void getUnocoinApi(Callback<Response> responseCallback);
    @GET("/api/data/coinsnapshot/?fsym=BTC&tsym=INR")
    void getLocalBitCoins(Callback<Response> callback);
    @GET("/sell-bitcoins-online/IN/india/.json")
    void getLocalbitcoinssell(Callback<Response> responseCallback);
    @GET("/api/ticker")
    void getEthexIndia(Callback<Response> responseCallback);
    @GET("/api/bitcoins/rates")
    void getBitxoxo(Callback<Response> responseCallback);

    @retrofit2.http.GET("/data/histominute?fsym=ETH&tsym=INR&limit=60&e=ETHEXIndia")
    Call<Example> getChartsETH();


    @retrofit2.http.GET("/data/histoday")
    Call<Example> getChartLTCMonth(@retrofit2.http.Query("fsym") String fsym, @retrofit2.http.Query("tsym") String tsym, @retrofit2.http.Query("limit") String limit, @retrofit2.http.Query("e") String e);


    @retrofit2.http.GET("/data/histominute")
    Call<Example> getChart(@retrofit2.http.Query("fsym") String fsym, @retrofit2.http.Query("tsym") String tsym, @retrofit2.http.Query("limit") String limit, @retrofit2.http.Query("e") String e);


    @GET("/getimages.php")
    void getImage(Callback<Response> responseCallback);
    @GET("/data/pricemulti?fsyms=BTC,ETH,LTC,XRP,BCH&tsyms=USD&e=BitTrex")
    void getBitrexData(Callback<Response> responseCallback);
    @GET("/data/pricemulti?fsyms=BTC,ETH,LTC,XRP,BCH&tsyms=USD&e=Bitfinex")
    void getBitfinex(Callback<Response> responseCallback);
    @GET("/data/pricemulti?fsyms=BTC,LTC,XRP&tsyms=USD&e=Bitstamp")
    void getBitstamp(Callback<Response> responseCallback);

    @GET("/data/pricemulti?fsyms=BTC,ETH,BCH,LTC,XRP&tsyms=USD&e=Poloniex")
    void getPoloniex(Callback<Response> responseCallback);
    @GET("/data/pricemulti?fsyms=BTC,ETH,LTC,XRP,BCH&tsyms=USD&e=Kraken")
    void getKraken(Callback<Response> responseCallback);
    @GET("/data/pricemulti?fsyms=BTC,ETH,LTC&tsyms=USD&e=BTCE")
    void getBTCE(Callback<Response> responseCallback);


    @FormUrlEncoded
    @POST("/adduser.php")
    public void addUser(@Field("email") String email,
                        @Field("photouri") String photouri,Callback<Response> callback);
    @FormUrlEncoded
    @POST("/addcomments.php")
    public void addComments(@Field("email") String email,
                            @Field("c_message") String c_message, @Field("c_dateandtime") String c_dateandtime, @Field("marketname")String marketname, @Field("coinname")String coinname,Callback<Response> callback);


    @GET("/getcommentsbyuser.php")
    void getPost(@Query("marketname") String marketname, @Query("coinname") String coinname, Callback<Response> responseCallback);


    @FormUrlEncoded
    @POST("/addgraph.php")
    public void addGraph(@Field("market") String market,
                            @Field("ask") String ask, @Field("time") String time,Callback<Response> callback);



    @GET("/getgraph.php")
    void getGraph(@Query("market") String market, Callback<Response> responseCallback);


}
