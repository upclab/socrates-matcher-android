package pe.edu.upc.mobile.UPCMovil;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.RESTClient.Environment;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class TramitesActivity extends MenuActivity {
    BroadcastReceiver receiver;
    private ArrayList<JSONObject> tramites;
    private TramitesAdapter tramitesAdapter;
    private TextView txtMensaje;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.TramitesActivity.1 */
    class C04261 extends BroadcastReceiver {
        C04261() {
        }

        public void onReceive(Context context, Intent intent) {
            TramitesActivity.this.finish();
        }
    }

    private class RESTTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(TramitesActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                TramitesActivity.this.prepareHandleException(e);
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
                    TramitesActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(TramitesActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    String codError = jsonObject.getString("CodError");
                    if (codError.equalsIgnoreCase("00000")) {
                        TramitesActivity.this.txtMensaje.setVisibility(8);
                        JSONArray jsonArray = new JSONArray();
                        jsonArray = jsonObject.getJSONArray("TramitesRealizados");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            TramitesActivity.this.tramites.add(jsonArray.getJSONObject(i));
                        }
                        TramitesActivity.this.tramitesAdapter.notifyDataSetChanged();
                    } else if (codError.equalsIgnoreCase("00003")) {
                        TramitesActivity.this.loggout(true);
                    } else {
                        String msgError = jsonObject.getString("MsgError");
                        if (codError.equalsIgnoreCase("00051")) {
                            TramitesActivity.this.txtMensaje.setText(msgError);
                            TramitesActivity.this.txtMensaje.setVisibility(0);
                            return;
                        }
                        MessageUtils.showErrorAlert(TramitesActivity.this, msgError);
                    }
                }
            } catch (Exception e) {
                TramitesActivity.this.prepareHandleException(e);
            }
        }
    }

    private class TramitesAdapter extends ArrayAdapter<JSONObject> {
        public TramitesAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> tramites) {
            super(context, textViewResourceId, tramites);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            try {
                JSONObject tramite = (JSONObject) TramitesActivity.this.tramites.get(position);
                if (row == null) {
                    row = ((LayoutInflater) TramitesActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.tramitesrow, null);
                }
                LinearLayout loTramitesRow = (LinearLayout) row.findViewById(C0386R.id.loTramitesRow);
                if (position % 2 == 0) {
                    loTramitesRow.setBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    loTramitesRow.setBackgroundColor(Color.parseColor("#DEDEDE"));
                }
                TextView txtNroSolicitud = (TextView) row.findViewById(C0386R.id.txtNroSolicitud);
                TextView txtEstado = (TextView) row.findViewById(C0386R.id.txtEstado);
                TextView txtTipoTramite = (TextView) row.findViewById(C0386R.id.txtTipoTramite);
                TextView txtFecha = (TextView) row.findViewById(C0386R.id.txtFecha);
                TextView txtNroSolicitudTitle = (TextView) row.findViewById(C0386R.id.txtNroSolicitudTitle);
                TextView txtEstadoTitle = (TextView) row.findViewById(C0386R.id.txtEstadoTitle);
                TextView txtTipoTramiteTitle = (TextView) row.findViewById(C0386R.id.txtTipoTramiteTitle);
                TextView txtFechaTitle = (TextView) row.findViewById(C0386R.id.txtFechaTitle);
                Typeface typefaceRegular = Typeface.createFromAsset(TramitesActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
                Typeface typefaceMedium = Typeface.createFromAsset(TramitesActivity.this.getAssets(), "fonts/Zizou Slab-Medium.otf");
                txtNroSolicitud.setTypeface(typefaceMedium);
                txtEstado.setTypeface(typefaceMedium);
                txtTipoTramite.setTypeface(typefaceRegular);
                txtFecha.setTypeface(typefaceRegular);
                txtNroSolicitudTitle.setTypeface(typefaceRegular);
                txtEstadoTitle.setTypeface(typefaceRegular);
                txtTipoTramiteTitle.setTypeface(typefaceRegular);
                txtFechaTitle.setTypeface(typefaceRegular);
                txtNroSolicitud.setText(tramite.getString("NroSolicitud"));
                txtEstado.setText(tramite.getString("Estado"));
                txtTipoTramite.setText(tramite.getString("Nombre"));
                String anio = tramite.getString("Fecha").substring(0, 4);
                String mes = tramite.getString("Fecha").substring(4, 6);
                txtFecha.setText(new StringBuilder(String.valueOf(tramite.getString("Fecha").substring(6))).append("/").append(mes).append("/").append(anio).toString());
            } catch (Exception e) {
                TramitesActivity.this.prepareHandleException(e);
            }
            return row;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04261();
            registerReceiver(this.receiver, intentFilter);
            this.tramites = new ArrayList();
            ListView listView = (ListView) findViewById(C0386R.id.lsTramites);
            this.tramitesAdapter = new TramitesAdapter(this, C0386R.layout.pagosrow, this.tramites);
            listView.setAdapter(this.tramitesAdapter);
            this.txtMensaje = (TextView) findViewById(C0386R.id.txtMensaje);
            this.txtMensaje.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf"));
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.tramites.clear();
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String codAlumno;
                String url;
                if (session.getString("TipoUsuario", StringUtils.EMPTY).equalsIgnoreCase("PADRE")) {
                    String codigo = session.getString("Codigo", StringUtils.EMPTY);
                    codAlumno = session.getString("CodAlumno", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/TramiteRealizadoPadre/?Codigo=" + codigo + "&CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                }
                codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                url = Environment.BASE_URL + "/TramiteRealizado/?CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                new RESTTask().execute(new String[]{url});
                return;
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
}
