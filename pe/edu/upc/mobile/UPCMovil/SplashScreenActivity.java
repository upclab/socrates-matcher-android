package pe.edu.upc.mobile.UPCMovil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.google.android.gms.games.GamesClient;
import pe.edu.upc.mobile.C0386R;

public class SplashScreenActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH;
    private Handler handler;
    private BroadcastReceiver receiver;
    private Runnable runnable;
    private TextView txt_splash_message;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.SplashScreenActivity.1 */
    class C04221 extends BroadcastReceiver {
        C04221() {
        }

        public void onReceive(Context context, Intent intent) {
            SplashScreenActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.SplashScreenActivity.2 */
    class C04232 implements Runnable {
        C04232() {
        }

        public void run() {
            SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, IntranetActivity.class));
            SplashScreenActivity.this.handler.removeCallbacks(SplashScreenActivity.this.runnable);
        }
    }

    public SplashScreenActivity() {
        this.SPLASH_DISPLAY_LENGTH = GamesClient.STATUS_ACHIEVEMENT_UNLOCK_FAILURE;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0386R.layout.splashscreenlayout);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_FINISH_SPLASH");
        this.txt_splash_message = (TextView) findViewById(C0386R.id.txt_splash_message);
        this.txt_splash_message.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ChronicleTextG1-Italic.otf"));
        this.receiver = new C04221();
        registerReceiver(this.receiver, intentFilter);
        this.handler = new Handler();
        this.runnable = new C04232();
    }

    protected void onResume() {
        try {
            super.onResume();
            this.handler.postDelayed(this.runnable, 3000);
        } catch (Exception e) {
        }
    }

    public void onBackPressed() {
    }

    protected void onDestroy() {
        try {
            super.onDestroy();
            unregisterReceiver(this.receiver);
        } catch (Exception e) {
        }
    }
}
