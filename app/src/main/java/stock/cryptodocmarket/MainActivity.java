package stock.cryptodocmarket;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;

import stock.cryptodocmarket.ForeignCompany.ForeignFragment;
import stock.cryptodocmarket.Fragment.NewsFragment;
import stock.cryptodocmarket.InidanCompany.IndianFragment;

public class MainActivity extends AppCompatActivity {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments

    ForeignFragment foreignFragment;
    IndianFragment indianFragment;
    NewsFragment newsFragment;
    static boolean mIsLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        AdView adView = (AdView) findViewById(R.id.adView);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-2755525358188689/7890379459");
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        indianFragment=new IndianFragment();
        foreignFragment=new ForeignFragment();
        newsFragment =new NewsFragment();
        adapter.addFragment(indianFragment,"INDIAN COMPANY");
        adapter.addFragment(foreignFragment,"FOREIGN COMPANY");
        adapter.addFragment(newsFragment,"NEWS");
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
