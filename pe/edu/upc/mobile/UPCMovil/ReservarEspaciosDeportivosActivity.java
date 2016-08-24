package pe.edu.upc.mobile.UPCMovil;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;
import java.net.SocketTimeoutException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.RESTClient.Environment;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.Utilities.EspaciosDeportivosUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class ReservarEspaciosDeportivosActivity extends MenuActivity {
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.ReservarEspaciosDeportivosActivity.1 */
    class C04201 extends BroadcastReceiver {
        C04201() {
        }

        public void onReceive(Context context, Intent intent) {
            ReservarEspaciosDeportivosActivity.this.finish();
        }
    }

    private class RESTTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(ReservarEspaciosDeportivosActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                ReservarEspaciosDeportivosActivity.this.prepareHandleException(e);
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
                    ReservarEspaciosDeportivosActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(ReservarEspaciosDeportivosActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("000") || aCodError.equalsIgnoreCase("111")) {
                        MessageUtils.showErrorAlert(ReservarEspaciosDeportivosActivity.this, aJSONObject.getString("MsgConfirmacion"));
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        ReservarEspaciosDeportivosActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(ReservarEspaciosDeportivosActivity.this, aJSONObject.getString("MsgError"));
                    }
                }
            } catch (Exception e) {
                ReservarEspaciosDeportivosActivity.this.prepareHandleException(e);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04201();
            registerReceiver(this.receiver, intentFilter);
            ((TextView) this.app.findViewById(C0386R.id.txtViewTitle)).setText("RESERVAS DEPORTIVAS");
            Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf");
            Typeface typefaceBold = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Bold.otf");
            ((TextView) findViewById(C0386R.id.txtViewReservarTitle)).setTypeface(typefaceBold);
            ((TextView) findViewById(C0386R.id.txtViewReservarFecha)).setTypeface(typefaceBold);
            ((TextView) findViewById(C0386R.id.txtViewReservarActividad)).setTypeface(typefaceBold);
            ((TextView) findViewById(C0386R.id.txtViewReservarHora)).setTypeface(typefaceBold);
            TextView txtViewReservarActividadSelected = (TextView) findViewById(C0386R.id.txtViewReservarActividadSelected);
            txtViewReservarActividadSelected.setTypeface(typefaceRegular);
            txtViewReservarActividadSelected.setText(EspaciosDeportivosActivity.actividadName);
            TextView txtViewReservarFechaSelected = (TextView) findViewById(C0386R.id.txtViewReservarFechaSelected);
            txtViewReservarFechaSelected.setText(DisponibilidadEspaciosDeportivosActivity.fechaReservaPrint);
            txtViewReservarFechaSelected.setTypeface(typefaceRegular);
            TextView txtViewReservarHoraSelected = (TextView) findViewById(C0386R.id.txtViewReservarHoraSelected);
            txtViewReservarHoraSelected.setText(DisponibilidadEspaciosDeportivosActivity.horaInicioPrint);
            txtViewReservarHoraSelected.setTypeface(typefaceRegular);
        } catch (Exception e) {
            prepareHandleException(e);
        }
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

    public void onClickReservarButton(View view) {
        try {
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                String url = Environment.BASE_URL + "/ReservarED/?CodSede=" + EspaciosDeportivosActivity.sedeKey + "&CodED=" + EspaciosDeportivosActivity.espacioCode + "&CodActiv=" + EspaciosDeportivosActivity.actividadCode + "&NumHoras=" + EspaciosDeportivosActivity.numeroHoras + "&CodAlumno=" + codAlumno + "&Fecha=" + DisponibilidadEspaciosDeportivosActivity.fechaReserva + "&HoraIni=" + EspaciosDeportivosUtils.parameterWithStartHour(DisponibilidadEspaciosDeportivosActivity.horaInicio) + "&HoraFin=" + EspaciosDeportivosUtils.parameterWithStartHour(DisponibilidadEspaciosDeportivosActivity.horaInicio, EspaciosDeportivosActivity.numeroHoras) + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                new RESTTask().execute(new String[]{url});
                return;
            }
            MessageUtils.showErrorAlert(this, "Verifique que su dispositivo tiene conexi\u00f3n a internet.");
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }
}
