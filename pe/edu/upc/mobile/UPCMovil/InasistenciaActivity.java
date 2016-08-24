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

public class InasistenciaActivity extends MenuActivity {
    private InasistenciaAdapter InasistenciaAdapter;
    private ArrayList<JSONObject> InasistenciaList;
    private LinearLayout loTitleInasistencia;
    private BroadcastReceiver receiver;
    private TextView txtCodigoInasistencia;
    private TextView txtMaxInasistencia;
    private TextView txtMensaje;
    private TextView txtTotalInasistencia;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.InasistenciaActivity.1 */
    class C04061 extends BroadcastReceiver {
        C04061() {
        }

        public void onReceive(Context context, Intent intent) {
            InasistenciaActivity.this.finish();
        }
    }

    private class InasistenciaAdapter extends ArrayAdapter<JSONObject> {
        public InasistenciaAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> pagos) {
            super(context, textViewResourceId, pagos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            try {
                JSONObject jsonPago = (JSONObject) InasistenciaActivity.this.InasistenciaList.get(position);
                if (row == null) {
                    row = ((LayoutInflater) InasistenciaActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.inasistenciarow, null);
                }
                LinearLayout loPagosRow = (LinearLayout) row.findViewById(C0386R.id.loInasistenciaRow);
                if (position % 2 == 0) {
                    loPagosRow.setBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    loPagosRow.setBackgroundColor(Color.parseColor("#DEDEDE"));
                }
                TextView txtCodigo = (TextView) row.findViewById(C0386R.id.txtCodigoInasistencia);
                TextView txtNombre = (TextView) row.findViewById(C0386R.id.txtNombreInasistencia);
                TextView txtMaximo = (TextView) row.findViewById(C0386R.id.txtMaxInasistencia);
                TextView txtTotal = (TextView) row.findViewById(C0386R.id.txtTotalInasistencia);
                Typeface typeface = Typeface.createFromAsset(InasistenciaActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
                txtCodigo.setText(jsonPago.getString("CodCurso"));
                txtNombre.setText(jsonPago.getString("CursoNombre"));
                txtMaximo.setText(jsonPago.getString("Maximo"));
                txtTotal.setText(jsonPago.getString("Total"));
                txtCodigo.setTypeface(typeface);
                txtNombre.setTypeface(typeface);
                txtMaximo.setTypeface(typeface);
                txtTotal.setTypeface(typeface);
            } catch (Exception e) {
                InasistenciaActivity.this.prepareHandleException(e);
            }
            return row;
        }
    }

    private class RESTTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(InasistenciaActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                InasistenciaActivity.this.prepareHandleException(e);
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
                    InasistenciaActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(InasistenciaActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    String codError = jsonObject.getString("CodError");
                    if (codError.equalsIgnoreCase("00000")) {
                        InasistenciaActivity.this.txtMensaje.setVisibility(8);
                        InasistenciaActivity.this.loTitleInasistencia.setVisibility(0);
                        JSONArray jsonArray = jsonObject.getJSONArray("Inasistencias");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            InasistenciaActivity.this.InasistenciaList.add(jsonArray.getJSONObject(i));
                        }
                        InasistenciaActivity.this.InasistenciaAdapter.notifyDataSetChanged();
                    } else if (codError.equalsIgnoreCase("00003")) {
                        InasistenciaActivity.this.loggout(true);
                    } else {
                        String msgError = jsonObject.getString("MsgError");
                        if (codError.equalsIgnoreCase("00031")) {
                            InasistenciaActivity.this.txtMensaje.setText(msgError);
                            InasistenciaActivity.this.txtMensaje.setVisibility(0);
                            InasistenciaActivity.this.loTitleInasistencia.setVisibility(8);
                            return;
                        }
                        MessageUtils.showErrorAlert(InasistenciaActivity.this, msgError);
                    }
                }
            } catch (Exception e) {
                InasistenciaActivity.this.prepareHandleException(e);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04061();
            registerReceiver(this.receiver, intentFilter);
            this.InasistenciaList = new ArrayList();
            ListView listView = (ListView) findViewById(C0386R.id.lsInasistencia);
            this.InasistenciaAdapter = new InasistenciaAdapter(this, C0386R.layout.inasistenciarow, this.InasistenciaList);
            listView.setAdapter(this.InasistenciaAdapter);
            this.txtMensaje = (TextView) findViewById(C0386R.id.txtMensajeInasistencia);
            this.txtMensaje.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf"));
            this.loTitleInasistencia = (LinearLayout) findViewById(C0386R.id.loTitleInasistencia);
            this.txtCodigoInasistencia = (TextView) findViewById(C0386R.id.txtCodigoInasistencia);
            this.txtMaxInasistencia = (TextView) findViewById(C0386R.id.txtMaxInasistencia);
            this.txtTotalInasistencia = (TextView) findViewById(C0386R.id.txtTotalInasistencia);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf");
            this.txtCodigoInasistencia.setTypeface(typeface);
            this.txtMaxInasistencia.setTypeface(typeface);
            this.txtTotalInasistencia.setTypeface(typeface);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.InasistenciaList.clear();
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String userType = session.getString("TipoUsuario", StringUtils.EMPTY);
                String codigo;
                String codAlumno;
                String url;
                if (userType.equalsIgnoreCase("PADRE")) {
                    codigo = session.getString("Codigo", StringUtils.EMPTY);
                    codAlumno = session.getString("CodAlumno", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/InasistenciaPadre/?Codigo=" + codigo + "&CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                } else if (userType.equalsIgnoreCase("PROFESOR")) {
                    codigo = session.getString("Codigo", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/InasistenciaProfesor/?Codigo=" + codigo + "&CodAlumno=" + CompanerosActivity.codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                } else {
                    codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/Inasistencia/?CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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
}
