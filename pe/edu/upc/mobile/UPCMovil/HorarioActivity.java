package pe.edu.upc.mobile.UPCMovil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.analytics.tracking.android.EasyTracker;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.RESTClient.Environment;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;
import pe.edu.upc.mobile.Utilities.ScheduleUtils;

public class HorarioActivity extends MenuActivity {
    private static final String[] CONTENT;
    private int currentDaySelection;
    private DayFragmentAdapter mAdapter;
    private PageIndicator mIndicator;
    private ViewPager mPager;
    private BroadcastReceiver receiver;
    private ArrayList<JSONObject> scheduleList;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.HorarioActivity.1 */
    class C04051 extends BroadcastReceiver {
        C04051() {
        }

        public void onReceive(Context context, Intent intent) {
            HorarioActivity.this.finish();
        }
    }

    private class RESTTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(HorarioActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                HorarioActivity.this.prepareHandleException(e);
            }
        }

        protected String doInBackground(String... urls) {
            String response = StringUtils.EMPTY;
            for (String url : urls) {
                try {
                    response = RESTClient.connectAndReturnResponse(url);
                } catch (SocketTimeoutException e) {
                    response = "SocketTimeoutException";
                } catch (ConnectTimeoutException e2) {
                    response = "ConnectTimeoutException";
                } catch (Exception e3) {
                    response = "Exception";
                }
            }
            return response;
        }

        protected void onPostExecute(String result) {
            try {
                this.alertDialog.dismiss();
                if (result.equalsIgnoreCase("Exception")) {
                    HorarioActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(HorarioActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("00000")) {
                        JSONArray jsonArray = aJSONObject.getJSONArray("HorarioDia");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            HorarioActivity.this.scheduleList.add(jsonArray.getJSONObject(i));
                        }
                        HorarioActivity.this.updateFragments();
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        HorarioActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(HorarioActivity.this, aJSONObject.getString("MsgError"));
                    }
                }
            } catch (Exception e) {
                HorarioActivity.this.prepareHandleException(e);
            }
        }
    }

    private class DayFragmentAdapter extends FragmentPagerAdapter {
        public DayFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            Calendar calendar = null;
            ArrayList<JSONObject> scheduleDayList = null;
            try {
                int delta = position - HorarioActivity.this.currentDaySelection;
                calendar = Calendar.getInstance();
                calendar.add(5, delta);
                scheduleDayList = ScheduleUtils.getScheduleForDay(HorarioActivity.this.scheduleList, position + 1, HorarioActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0).getString("TipoUsuario", StringUtils.EMPTY));
            } catch (Exception e) {
                HorarioActivity.this.prepareHandleException(e);
            }
            return DayFragment.newInstance(calendar, scheduleDayList);
        }

        public int getCount() {
            return HorarioActivity.CONTENT.length;
        }

        @SuppressLint({"DefaultLocale"})
        public CharSequence getPageTitle(int position) {
            return HorarioActivity.CONTENT[position % HorarioActivity.CONTENT.length].toUpperCase(Locale.getDefault());
        }
    }

    static {
        CONTENT = new String[]{"Lunes", "Martes", "Mi\u00e9rcoles", "Jueves", "Viernes", "S\u00e1bado", "Domingo"};
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04051();
            registerReceiver(this.receiver, intentFilter);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("pe.edu.upc.UPCMovil.ACTION_SUCCESS_LOGIN");
            sendBroadcast(broadcastIntent);
            this.scheduleList = new ArrayList();
            this.mAdapter = new DayFragmentAdapter(getSupportFragmentManager());
            this.mPager = (ViewPager) findViewById(C0386R.id.pager);
            this.mPager.setAdapter(this.mAdapter);
            this.mIndicator = (TabPageIndicator) findViewById(C0386R.id.indicator);
            this.mIndicator.setViewPager(this.mPager);
            switch (Calendar.getInstance().get(7)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    this.mIndicator.setCurrentItem(6);
                    this.currentDaySelection = 6;
                    return;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    this.mIndicator.setCurrentItem(0);
                    this.currentDaySelection = 0;
                    return;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    this.mIndicator.setCurrentItem(1);
                    this.currentDaySelection = 1;
                    return;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    this.mIndicator.setCurrentItem(2);
                    this.currentDaySelection = 2;
                    return;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    this.mIndicator.setCurrentItem(3);
                    this.currentDaySelection = 3;
                    return;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    this.mIndicator.setCurrentItem(4);
                    this.currentDaySelection = 4;
                    return;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    this.mIndicator.setCurrentItem(5);
                    this.currentDaySelection = 5;
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            prepareHandleException(e);
        }
        prepareHandleException(e);
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.scheduleList.clear();
            updateFragments();
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String userType = session.getString("TipoUsuario", StringUtils.EMPTY);
                String codigo;
                String codAlumno;
                String url;
                if (userType.equalsIgnoreCase("PADRE")) {
                    codigo = session.getString("Codigo", StringUtils.EMPTY);
                    codAlumno = session.getString("CodAlumno", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/HorarioPadre/?Codigo=" + codigo + "&CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                } else if (userType.equalsIgnoreCase("PROFESOR")) {
                    codigo = session.getString("Codigo", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/HorarioProfesor/?Codigo=" + codigo + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                } else {
                    codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/Horario/?CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                }
            }
            MessageUtils.showErrorAlert(this, "Verifique que su dispositivo tiene conexi\u00f3n a internet.");
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

    private void updateFragments() {
        try {
            String userType = getSharedPreferences("pe.edu.upc.UPCMovil", 0).getString("TipoUsuario", StringUtils.EMPTY);
            for (int i = 0; i < 7; i++) {
                DayFragment fragment = (DayFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:2131099660:" + i);
                if (!(fragment == null || fragment.getView() == null)) {
                    fragment.update(ScheduleUtils.getScheduleForDay(this.scheduleList, i + 1, userType));
                }
            }
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }
}
