package pe.edu.upc.mobile.UPCMovil;

import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;
import org.apache.commons.lang3.StringUtils;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.Entities.Session;
import pe.edu.upc.mobile.Utilities.ExceptionUtils;
import pe.edu.upc.mobile.Utilities.ImageUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;

public class DetalleCampusActivity extends FragmentActivity {
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.DetalleCampusActivity.1 */
    class C03951 implements OnClickListener {
        C03951() {
        }

        public void onClick(View v) {
            DetalleCampusActivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(C0386R.layout.detallecampuslayout);
            TextView txtViewTitle = (TextView) findViewById(C0386R.id.txtViewTitle);
            txtViewTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SolanoGothicMVBProBd.otf"));
            txtViewTitle.setText("CAMPUS");
            ((ImageView) findViewById(C0386R.id.BtnSlide)).setOnClickListener(new C03951());
            Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf");
            Typeface typefaceMedium = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Medium.otf");
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String placeInfoName = extras.getString("placeInfoName");
                TextView txtDetailPlaceName = (TextView) findViewById(C0386R.id.txtDetailPlaceName);
                txtDetailPlaceName.setTypeface(typefaceMedium);
                txtDetailPlaceName.setText(placeInfoName);
                String placeName = extras.getString("placeName");
                TextView txtPlaceName = (TextView) findViewById(C0386R.id.txtPlaceName);
                txtPlaceName.setTypeface(typefaceRegular);
                txtPlaceName.setText(placeName);
                String places = extras.getString("places");
                TextView txtPlaceDescription = (TextView) findViewById(C0386R.id.txtPlaceDescription);
                txtPlaceDescription.setTypeface(typefaceRegular);
                txtPlaceDescription.setText(places);
                ((ImageView) findViewById(C0386R.id.imgViewPin)).setImageBitmap(ImageUtils.getPinImageForPlaceInfoName(placeInfoName, this));
            }
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
