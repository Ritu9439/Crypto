package stock.cryptodocmarket;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static stock.cryptodocmarket.R.id.relativelayout;

public class SplashActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    Animation animFadeIn;
    LinearLayout nointernet;
    ImageView splashimage;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        nointernet= (LinearLayout) findViewById(R.id.nointernet);
        splashimage= (ImageView) findViewById(R.id.splashimage);
        progress= (ProgressBar) findViewById(R.id.progress);

        PowerManager mgr = (PowerManager)getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
        wakeLock.acquire();

        startService(new Intent(this, MyService.class));
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, MyService.class);
        PendingIntent pintent = PendingIntent
                .getService(this, 0, intent, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Start service every 20 seconds
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                9 * 1000, pintent);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
        }
        checkConnection();

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.VISIBLE);
                    splashimage.setVisibility(View.VISIBLE);

                    nointernet.setVisibility(View.GONE);
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);

                    startActivity(intent);
                }
            },5000);
        } else {
            progress.setVisibility(View.GONE);
            splashimage.setVisibility(View.GONE);

            nointernet.setVisibility(View.VISIBLE);
        }


    }

}
