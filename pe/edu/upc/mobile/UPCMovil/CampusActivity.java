package pe.edu.upc.mobile.UPCMovil;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.Entities.Place;
import pe.edu.upc.mobile.Entities.Session;
import pe.edu.upc.mobile.Utilities.ExceptionUtils;
import pe.edu.upc.mobile.Utilities.LocationUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;

public class CampusActivity extends FragmentActivity {
    private PlacesFragmentAdapter mAdapter;
    private PageIndicator mIndicator;
    private ViewPager mPager;
    private ArrayList<Place> places;
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.CampusActivity.1 */
    class C03871 implements OnClickListener {
        C03871() {
        }

        public void onClick(View v) {
            CampusActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.CampusActivity.2 */
    class C03882 extends BroadcastReceiver {
        C03882() {
        }

        public void onReceive(Context context, Intent intent) {
            CampusActivity.this.finish();
        }
    }

    private class PlacesFragmentAdapter extends FragmentPagerAdapter {
        public PlacesFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return PlaceFragment.newInstance((Place) CampusActivity.this.places.get(position));
        }

        public int getCount() {
            return CampusActivity.this.places.size();
        }

        @SuppressLint({"DefaultLocale"})
        public CharSequence getPageTitle(int position) {
            return ((Place) CampusActivity.this.places.get(position)).getName().toUpperCase(Locale.getDefault());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(C0386R.layout.campuslayout);
            TextView txtViewTitle = (TextView) findViewById(C0386R.id.txtViewTitle);
            txtViewTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SolanoGothicMVBProBd.otf"));
            txtViewTitle.setText("CAMPUS");
            ((ImageView) findViewById(C0386R.id.BtnSlide)).setOnClickListener(new C03871());
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C03882();
            registerReceiver(this.receiver, intentFilter);
            this.places = new ArrayList();
            this.places.addAll(LocationUtils.getPlaces(this));
            this.mAdapter = new PlacesFragmentAdapter(getSupportFragmentManager());
            this.mPager = (ViewPager) findViewById(C0386R.id.pager);
            this.mPager.setAdapter(this.mAdapter);
            this.mIndicator = (TabPageIndicator) findViewById(C0386R.id.indicator);
            this.mIndicator.setViewPager(this.mPager);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    private void prepareHandleException(Exception e) {
        Session session = new Session();
        SharedPreferences preferences = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
        String codAlumno = preferences.getString("CodAlumno", StringUtils.EMPTY);
        String token = preferences.getString("Token", StringUtils.EMPTY);
        session.setCodAlumno(codAlumno);
        session.setToken(token);
        MessageUtils.showErrorAlert(this, "Se encontr\u00f3 un error en la aplicaci\u00f3n.");
        ExceptionUtils.handleException(e, session);
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onStop() {
        try {
            super.onStop();
            EasyTracker.getInstance(this).activityStop(this);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onDestroy() {
        try {
            super.onDestroy();
            unregisterReceiver(this.receiver);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }
}
