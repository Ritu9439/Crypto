package stock.cryptodocmarket.ForeignCompany;

/**
 * Created by Administrator on 24-10-2017.
 */

public class CryptoImage {
    String coinname,urlimg;

    public CryptoImage(String coinname, String urlimg) {
        this.coinname = coinname;
        this.urlimg = urlimg;
    }

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }

    public String getUrlimg() {
        return urlimg;
    }

    public void setUrlimg(String urlimg) {
        this.urlimg = urlimg;
    }
}
