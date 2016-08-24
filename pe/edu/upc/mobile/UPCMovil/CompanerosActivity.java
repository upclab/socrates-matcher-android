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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;
import com.squareup.picasso.Picasso;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.RESTClient.Environment;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.UPCMovil.ProfessorOptionsDialogFragment.ProfessorOptionsDialogFragmentListener;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class CompanerosActivity extends MenuActivity implements ProfessorOptionsDialogFragmentListener {
    public static String codAlumno;
    private CompanerosAdapter companerosAdapter;
    private ArrayList<JSONObject> companerosList;
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.CompanerosActivity.1 */
    class C03891 extends BroadcastReceiver {
        C03891() {
        }

        public void onReceive(Context context, Intent intent) {
            CompanerosActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.CompanerosActivity.2 */
    class C03902 implements OnItemClickListener {
        C03902() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            if (CompanerosActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0).getString("TipoUsuario", StringUtils.EMPTY).equalsIgnoreCase("PROFESOR")) {
                ProfessorOptionsDialogFragment.newInstance(position).show(CompanerosActivity.this.getSupportFragmentManager(), "options");
            }
        }
    }

    private class CompanerosAdapter extends ArrayAdapter<JSONObject> {
        public CompanerosAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                try {
                    convertView = ((LayoutInflater) CompanerosActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.companerosrow, null);
                } catch (Exception e) {
                    CompanerosActivity.this.prepareHandleException(e);
                }
            }
            JSONObject companero = (JSONObject) CompanerosActivity.this.companerosList.get(position);
            if (companero != null) {
                LinearLayout linearLayout = (LinearLayout) convertView.findViewById(C0386R.id.companeroRow);
                if (position % 2 == 0) {
                    linearLayout.setBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    linearLayout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                }
                Typeface typefaceRegular = Typeface.createFromAsset(CompanerosActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
                Typeface typefaceLight = Typeface.createFromAsset(CompanerosActivity.this.getAssets(), "fonts/Zizou Slab-Light.otf");
                TextView txtViewCompaneroName = (TextView) convertView.findViewById(C0386R.id.txtViewCompaneroName);
                txtViewCompaneroName.setText(companero.getString("nombre_completo"));
                txtViewCompaneroName.setTypeface(typefaceRegular);
                TextView txtViewCompaneroEmail = (TextView) convertView.findViewById(C0386R.id.txtViewCompaneroEmail);
                txtViewCompaneroEmail.setText(new StringBuilder(String.valueOf(companero.getString("codigo").toLowerCase(Locale.getDefault()))).append("@upc.edu.pe").toString());
                txtViewCompaneroEmail.setTypeface(typefaceLight);
                TextView txtViewCompaneroCareer = (TextView) convertView.findViewById(C0386R.id.txtViewCompaneroCareer);
                txtViewCompaneroCareer.setText(companero.getString("carrera_actual"));
                txtViewCompaneroCareer.setTypeface(typefaceLight);
                Picasso.with(CompanerosActivity.this).load(companero.getString("url_foto")).resize(150, 200).into((ImageView) convertView.findViewById(C0386R.id.imgViewCompaneroFoto));
            }
            return convertView;
        }
    }

    private class RESTTask extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(CompanerosActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                CompanerosActivity.this.prepareHandleException(e);
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
                    CompanerosActivity.this.prepareHandleException(null);
                    return;
                }
                if (!result.equalsIgnoreCase("SocketTimeoutException")) {
                    if (!result.equalsIgnoreCase("ConnectTimeoutException")) {
                        if (!result.equalsIgnoreCase(StringUtils.EMPTY)) {
                            JSONObject aJSONObject = new JSONObject(result);
                            String aCodError = aJSONObject.getString("CodError");
                            if (aCodError.equalsIgnoreCase("00000")) {
                                JSONArray jsonArray;
                                if (CompanerosActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0).getString("TipoUsuario", StringUtils.EMPTY).equalsIgnoreCase("PROFESOR")) {
                                    jsonArray = aJSONObject.getJSONArray("Cursos").getJSONObject(0).getJSONArray("alumnos");
                                } else {
                                    jsonArray = aJSONObject.getJSONArray("alumnos");
                                }
                                int length = jsonArray.length();
                                for (int i = 0; i < length; i++) {
                                    CompanerosActivity.this.companerosList.add(jsonArray.getJSONObject(i));
                                }
                                CompanerosActivity.this.companerosAdapter.notifyDataSetChanged();
                                return;
                            } else if (aCodError.equalsIgnoreCase("00003")) {
                                CompanerosActivity.this.loggout(true);
                                return;
                            } else {
                                MessageUtils.showErrorAlert(CompanerosActivity.this, aJSONObject.getString("MsgError"));
                                return;
                            }
                        }
                    }
                }
                MessageUtils.showErrorAlert(CompanerosActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
            } catch (Exception e) {
                CompanerosActivity.this.prepareHandleException(e);
            }
        }
    }

    static {
        codAlumno = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C03891();
            registerReceiver(this.receiver, intentFilter);
            ((TextView) this.app.findViewById(C0386R.id.txtViewTitle)).setText("ALUMNOS");
            this.companerosList = new ArrayList();
            this.companerosAdapter = new CompanerosAdapter(this, C0386R.layout.companerosrow, this.companerosList);
            ListView listview = (ListView) findViewById(C0386R.id.lista_companeros);
            listview.setAdapter(this.companerosAdapter);
            listview.setOnItemClickListener(new C03902());
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.companerosList.clear();
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String url;
                if (session.getString("TipoUsuario", StringUtils.EMPTY).equalsIgnoreCase("PROFESOR")) {
                    String codigo = session.getString("Codigo", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/ListadoAlumnosProfesor/?Codigo=" + codigo + "&Token=" + session.getString("Token", StringUtils.EMPTY) + "&Modalidad=" + CursosActivity.modalidad + "&Periodo=" + CursosActivity.periodo + "&Curso=" + CursosActivity.cursoId + "&Seccion=" + CursosActivity.seccion + "&Grupo=" + CursosActivity.grupo;
                    new RESTTask().execute(new String[]{url});
                    return;
                }
                String codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                url = Environment.BASE_URL + "/Companeros/?CodAlumno=" + codAlumno + "&CodCurso=" + NotasActivity.selectedCodCurso + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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

    public void onSelectInasistencias(int position) {
        try {
            codAlumno = ((JSONObject) this.companerosList.get(position)).getString("codigo");
            Intent aIntent = new Intent(this, InasistenciaActivity.class);
            aIntent.putExtra("OptionNumber", 99);
            aIntent.putExtra("OptionString", "Inasistencias");
            aIntent.putExtra("LoggedIn", true);
            startActivity(aIntent);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    public void onSelectNotas(int position) {
        try {
            codAlumno = ((JSONObject) this.companerosList.get(position)).getString("codigo");
            Intent aIntent = new Intent(this, DetalleNotaActivity.class);
            aIntent.putExtra("OptionNumber", 97);
            aIntent.putExtra("LoggedIn", true);
            startActivity(aIntent);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }
}
