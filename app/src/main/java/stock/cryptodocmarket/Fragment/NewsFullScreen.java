package stock.cryptodocmarket.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import stock.cryptodocmarket.R;

import static stock.cryptodocmarket.R.id.news;

public class NewsFullScreen extends AppCompatActivity {
    WebView webView;
    String data;
    Document doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full_screen);
        webView = (WebView) findViewById(news);
        data = getIntent().getStringExtra("website");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        loadWebViewdata();

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
        return super.onOptionsItemSelected(item);
    }

    public void loadWebViewdata() {



        new AsyncTask<Void, Void, Document>() {
            @Override
            protected Document doInBackground(Void... voids) {
                Document document=null;
                try {
                    document= Jsoup.connect(data).get();
                    document.getElementsByClass("icon-list").remove();
                    document.getElementsByClass("menu").remove();
                    document.getElementsByClass("logo").remove();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return document;
            }

            @Override
            protected void onPostExecute(Document s) {
                doc=s;
                webView.loadDataWithBaseURL(data,s.toString(),"text/html","utf-8","");
                webView.setWebViewClient(new MyBrowser());
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
                webView.getSettings().setAppCachePath( getCacheDir().getAbsolutePath() );
                webView.getSettings().setAllowFileAccess( true );
                webView.getSettings().setAppCacheEnabled( true );
                webView.getSettings().setJavaScriptEnabled( true );
                webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );
                webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
                super.onPostExecute(s);
            }
        }.execute();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {

            Log.d("mydata",url);
            new AsyncTask<Void, Void, Document>() {
                @Override
                protected Document doInBackground(Void... voids) {
                    Document document=null;
                    try {
                        document=Jsoup.connect(url).get();
                        document.getElementsByClass("footer").remove();
                        document.getElementsByClass("navbar").remove();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return document;
                }

                @Override
                protected void onPostExecute(Document s) {
                    doc=s;
                    webView.loadDataWithBaseURL(url,s.toString(),"text/html","utf-8","");
                    //webView.setWebViewClient(new CompletedProjects.MyBrowser());
                    webView.getSettings().setLoadsImagesAutomatically(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setUseWideViewPort(true);

                    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    webView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
                    webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
                    webView.getSettings().setAllowFileAccess( true );
                    webView.getSettings().setAppCacheEnabled( true );
                    webView.getSettings().setJavaScriptEnabled( true );
                    webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );
                    webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );

                    super.onPostExecute(s);
                }
            }.execute();
            view.loadUrl(url);

            return true;
        }



        @Override
        public void onPageFinished(WebView view, String url) {
            webView.loadUrl("javascript:document.body.style.padding=\"0%\"; void 0");
            webView.loadUrl("javascript:document.body.style.margin=\"0%\"; void 0");


        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (view.getSettings().getCacheMode()==WebSettings.LOAD_NO_CACHE){
                webView.setVisibility(View.GONE);
            }
            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }
}
