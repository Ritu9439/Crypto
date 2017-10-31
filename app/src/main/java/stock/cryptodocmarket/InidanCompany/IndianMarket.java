package stock.cryptodocmarket.InidanCompany;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 23-10-2017.
 */

public class IndianMarket implements Parcelable {
    String market,buy,sell,url,coin,time;

    public IndianMarket(String market, String buy, String sell, String url, String coin, String time) {
        this.market = market;
        this.buy = buy;
        this.sell = sell;
        this.url = url;
        this.coin = coin;
        this.time = time;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(market);
        parcel.writeString(buy);
        parcel.writeString(sell);
        parcel.writeString(url);
        parcel.writeString(coin);
        parcel.writeString(time);
    }
    private IndianMarket(Parcel in) {
        // This order must match the order in writeToParcel()
        market = in.readString();;
        buy = in.readString();;
        sell = in.readString();;
        url = in.readString();;
        coin = in.readString();;
        time=in.readString();

        // Continue doing this for the rest of your member data
    } public IndianMarket() {
    }

    public static final Creator<IndianMarket> CREATOR = new Creator<IndianMarket>() {
        public IndianMarket createFromParcel(Parcel in) {
            return new IndianMarket(in);
        }

        public IndianMarket[] newArray(int size) {
            return new IndianMarket[size];
        }
    };
}
