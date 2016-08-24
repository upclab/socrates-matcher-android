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
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.RESTClient.Environment;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class DetalleNotaActivity extends MenuActivity {
    private NotasAdapter notasAdapter;
    private ArrayList<JSONObject> notasList;
    private BroadcastReceiver receiver;
    private TextView txtViewNota;
    private TextView txtViewPeso;
    private TextView txtViewTipo;
    private TextView txt_formula;
    private TextView txt_nota_footer;
    private TextView txt_porcentaje_footer;
    private TextView txt_tipo_footer;
    private TextView txt_titulo_formula;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.DetalleNotaActivity.1 */
    class C03961 extends BroadcastReceiver {
        C03961() {
        }

        public void onReceive(Context context, Intent intent) {
            DetalleNotaActivity.this.finish();
        }
    }

    private class NotasAdapter extends ArrayAdapter<JSONObject> {
        public NotasAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                try {
                    v = ((LayoutInflater) DetalleNotaActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.detallenotasrow, null);
                } catch (Exception e) {
                    DetalleNotaActivity.this.prepareHandleException(e);
                }
            }
            JSONObject curso = (JSONObject) DetalleNotaActivity.this.notasList.get(position);
            if (curso != null) {
                LinearLayout linear = (LinearLayout) v.findViewById(C0386R.id.layout_detalle_nota);
                TextView tipo_nota = (TextView) v.findViewById(C0386R.id.txt_tipo);
                TextView peso_nota = (TextView) v.findViewById(C0386R.id.txt_porcentaje);
                TextView puntaje_nota = (TextView) v.findViewById(C0386R.id.txt_nota);
                Typeface typeface = Typeface.createFromAsset(DetalleNotaActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
                tipo_nota.setTypeface(typeface);
                peso_nota.setTypeface(typeface);
                puntaje_nota.setTypeface(typeface);
                if (position % 2 == 0) {
                    linear.setBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    linear.setBackgroundColor(Color.parseColor("#DEDEDE"));
                }
                tipo_nota.setText(new StringBuilder(String.valueOf(WordUtils.capitalize(curso.getString("NombreEvaluacion").toLowerCase(Locale.getDefault())))).append(" ").append(curso.getString("NroEvaluacion")).toString());
                peso_nota.setText(curso.getString("Peso"));
                if (curso.getString("Valor").equals("null")) {
                    puntaje_nota.setText(StringUtils.EMPTY);
                } else {
                    puntaje_nota.setText(curso.getString("Valor"));
                    if (curso.getString("Valor").equalsIgnoreCase("NR")) {
                        puntaje_nota.setTextColor(-65536);
                    } else if (curso.getString("Valor").equalsIgnoreCase("RET")) {
                        puntaje_nota.setTextColor(-65536);
                    } else if (!(curso.getString("Valor").equalsIgnoreCase(StringUtils.EMPTY) || curso.getString("Valor").equalsIgnoreCase("NR") || curso.getString("Valor").equalsIgnoreCase("RET") || Double.valueOf(curso.getString("Valor")).doubleValue() >= 13.0d)) {
                        puntaje_nota.setTextColor(-65536);
                    }
                }
            }
            return v;
        }
    }

    private class RESTTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(DetalleNotaActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                DetalleNotaActivity.this.prepareHandleException(e);
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
                    DetalleNotaActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(DetalleNotaActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("00000")) {
                        JSONArray jsonArray = aJSONObject.getJSONArray("Notas");
                        DetalleNotaActivity.this.txt_formula.setText(aJSONObject.getString("Formula"));
                        if (aJSONObject.getString("NotaFinal").equals("null")) {
                            DetalleNotaActivity.this.txt_nota_footer.setText(StringUtils.EMPTY);
                        } else {
                            DetalleNotaActivity.this.txt_nota_footer.setText(aJSONObject.getString("NotaFinal"));
                        }
                        DetalleNotaActivity.this.txt_porcentaje_footer.setText(aJSONObject.getString("PorcentajeAvance"));
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            DetalleNotaActivity.this.notasList.add(jsonArray.getJSONObject(i));
                        }
                        DetalleNotaActivity.this.notasAdapter.notifyDataSetChanged();
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        DetalleNotaActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(DetalleNotaActivity.this, aJSONObject.getString("MsgError"));
                    }
                }
            } catch (Exception e) {
                DetalleNotaActivity.this.prepareHandleException(e);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            this.txt_formula = (TextView) findViewById(C0386R.id.txt_formula);
            this.txt_porcentaje_footer = (TextView) findViewById(C0386R.id.txt_porcentaje_footer);
            this.txt_tipo_footer = (TextView) findViewById(C0386R.id.txt_tipo_footer);
            this.txt_nota_footer = (TextView) findViewById(C0386R.id.txt_nota_footer);
            this.txt_titulo_formula = (TextView) findViewById(C0386R.id.txt_titulo_formula);
            this.txtViewTipo = (TextView) findViewById(C0386R.id.txtViewTipo);
            this.txtViewPeso = (TextView) findViewById(C0386R.id.txtViewPeso);
            this.txtViewNota = (TextView) findViewById(C0386R.id.txtViewNota);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf");
            this.txt_formula.setTypeface(typeface);
            this.txt_porcentaje_footer.setTypeface(typeface);
            this.txt_tipo_footer.setTypeface(typeface);
            this.txt_nota_footer.setTypeface(typeface);
            this.txt_titulo_formula.setTypeface(typeface);
            this.txtViewTipo.setTypeface(typeface);
            this.txtViewPeso.setTypeface(typeface);
            this.txtViewNota.setTypeface(typeface);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C03961();
            registerReceiver(this.receiver, intentFilter);
            this.notasList = new ArrayList();
            this.notasAdapter = new NotasAdapter(this, C0386R.layout.detallenotasrow, this.notasList);
            ((ListView) findViewById(C0386R.id.lista_detalle_notas)).setAdapter(this.notasAdapter);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.notasList.clear();
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String userType = session.getString("TipoUsuario", StringUtils.EMPTY);
                String codigo;
                String codAlumno;
                String url;
                if (userType.equalsIgnoreCase("PADRE")) {
                    ((TextView) this.app.findViewById(C0386R.id.txtViewTitle)).setText(NotasActivity.selectedCursoNombre);
                    codigo = session.getString("Codigo", StringUtils.EMPTY);
                    codAlumno = session.getString("CodAlumno", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/NotaPadre/?Codigo=" + codigo + "&CodAlumno=" + codAlumno + "&CodCurso=" + NotasActivity.selectedCodCurso + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                } else if (userType.equalsIgnoreCase("PROFESOR")) {
                    ((TextView) this.app.findViewById(C0386R.id.txtViewTitle)).setText(CursosActivity.cursoNombre);
                    codigo = session.getString("Codigo", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/NotaProfesor/?Codigo=" + codigo + "&CodAlumno=" + CompanerosActivity.codAlumno + "&CodCurso=" + CursosActivity.cursoId + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                } else {
                    ((TextView) this.app.findViewById(C0386R.id.txtViewTitle)).setText(NotasActivity.selectedCursoNombre);
                    codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/Nota/?CodAlumno=" + codAlumno + "&CodCurso=" + NotasActivity.selectedCodCurso + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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
