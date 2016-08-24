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
import pe.edu.upc.mobile.Utilities.CubiculosComputadorasUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class ReservarCubiculosComputadorasActivity extends MenuActivity {
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.ReservarCubiculosComputadorasActivity.1 */
    class C04191 extends BroadcastReceiver {
        C04191() {
        }

        public void onReceive(Context context, Intent intent) {
            ReservarCubiculosComputadorasActivity.this.finish();
        }
    }

    private class RESTTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(ReservarCubiculosComputadorasActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                ReservarCubiculosComputadorasActivity.this.prepareHandleException(e);
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
                    ReservarCubiculosComputadorasActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(ReservarCubiculosComputadorasActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("00000")) {
                        MessageUtils.showErrorAlert(ReservarCubiculosComputadorasActivity.this, aJSONObject.getString("MsgError"));
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        ReservarCubiculosComputadorasActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(ReservarCubiculosComputadorasActivity.this, aJSONObject.getString("MsgError"));
                    }
                }
            } catch (Exception e) {
                ReservarCubiculosComputadorasActivity.this.prepareHandleException(e);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04191();
            registerReceiver(this.receiver, intentFilter);
            ((TextView) this.app.findViewById(C0386R.id.txtViewTitle)).setText("RESERVAS DE RECURSOS");
            Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf");
            Typeface typefaceBold = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Bold.otf");
            ((TextView) findViewById(C0386R.id.txtViewReservarTitle)).setTypeface(typefaceBold);
            ((TextView) findViewById(C0386R.id.txtViewReservarFecha)).setTypeface(typefaceBold);
            ((TextView) findViewById(C0386R.id.txtViewReservarRecurso)).setTypeface(typefaceBold);
            ((TextView) findViewById(C0386R.id.txtViewReservarHora)).setTypeface(typefaceBold);
            TextView txtViewReservarActividadSelected = (TextView) findViewById(C0386R.id.txtViewReservarRecursoSelected);
            txtViewReservarActividadSelected.setTypeface(typefaceRegular);
            txtViewReservarActividadSelected.setText(DisponibilidadCubiculosComputadorasActivity.nombreRecurso);
            TextView txtViewReservarFechaSelected = (TextView) findViewById(C0386R.id.txtViewReservarFechaSelected);
            txtViewReservarFechaSelected.setText(CubiculosComputadorasActivity.fechaReserva);
            txtViewReservarFechaSelected.setTypeface(typefaceRegular);
            TextView txtViewReservarHoraSelected = (TextView) findViewById(C0386R.id.txtViewReservarHoraSelected);
            txtViewReservarHoraSelected.setText(CubiculosComputadorasActivity.horaReserva);
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
                String url = Environment.BASE_URL + "/Reservar/?CodRecurso=" + DisponibilidadCubiculosComputadorasActivity.codRecurso + "&NomRecurso=" + CubiculosComputadorasUtils.parameterWithResourceNameString(DisponibilidadCubiculosComputadorasActivity.nombreRecurso) + "&CodAlumno=" + codAlumno + "&CanHoras=" + CubiculosComputadorasActivity.numeroHoras + "&fecIni=" + CubiculosComputadorasUtils.parameterWithStartDateString(CubiculosComputadorasActivity.fechaReserva, CubiculosComputadorasActivity.horaReserva) + "&fecFin=" + CubiculosComputadorasUtils.parameterWithEndDateString(CubiculosComputadorasActivity.fechaReserva, CubiculosComputadorasActivity.horaReserva, Integer.parseInt(CubiculosComputadorasActivity.numeroHoras)) + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                new RESTTask().execute(new String[]{url});
                return;
            }
            MessageUtils.showErrorAlert(this, "Verifique que su dispositivo tiene conexi\u00f3n a internet.");
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }
}
