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
import pe.edu.upc.mobile.UPCMovil.StudentOptionsDialogFragment.StudentOptionsDialogFragmentListener;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class NotasActivity extends MenuActivity implements StudentOptionsDialogFragmentListener {
    public static String selectedCodCurso;
    public static String selectedCursoNombre;
    private CursosAdapter cursosAdapter;
    private ArrayList<JSONObject> cursosList;
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.NotasActivity.1 */
    class C04121 extends BroadcastReceiver {
        C04121() {
        }

        public void onReceive(Context context, Intent intent) {
            NotasActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.NotasActivity.2 */
    class C04132 implements OnItemClickListener {
        C04132() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            try {
                Intent aIntent;
                if (NotasActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0).getString("TipoUsuario", StringUtils.EMPTY).equalsIgnoreCase("PADRE")) {
                    aIntent = new Intent(NotasActivity.this, DetalleNotaActivity.class);
                    aIntent.putExtra("OptionNumber", 99);
                    aIntent.putExtra("LoggedIn", true);
                    NotasActivity.selectedCursoNombre = ((JSONObject) NotasActivity.this.cursosList.get(position)).getString("CursoNombre");
                    NotasActivity.selectedCodCurso = ((JSONObject) NotasActivity.this.cursosList.get(position)).getString("CodCurso");
                    NotasActivity.this.startActivity(aIntent);
                    return;
                }
                aIntent = new Intent(NotasActivity.this, DetalleNotaActivity.class);
                aIntent.putExtra("OptionNumber", 99);
                aIntent.putExtra("LoggedIn", true);
                NotasActivity.selectedCursoNombre = ((JSONObject) NotasActivity.this.cursosList.get(position)).getString("CursoNombre");
                NotasActivity.selectedCodCurso = ((JSONObject) NotasActivity.this.cursosList.get(position)).getString("CodCurso");
                NotasActivity.this.startActivity(aIntent);
            } catch (Exception e) {
                NotasActivity.this.prepareHandleException(e);
            }
        }
    }

    private class CursosAdapter extends ArrayAdapter<JSONObject> {
        public CursosAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                try {
                    v = ((LayoutInflater) NotasActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.cursosrow, null);
                } catch (Exception e) {
                    NotasActivity.this.prepareHandleException(e);
                }
            }
            JSONObject curso = (JSONObject) NotasActivity.this.cursosList.get(position);
            if (curso != null) {
                LinearLayout linearLayout = (LinearLayout) v.findViewById(C0386R.id.cursoRow);
                if (position % 2 == 0) {
                    linearLayout.setBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    linearLayout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                }
                TextView nombre_curso = (TextView) v.findViewById(C0386R.id.txt_row_curso);
                nombre_curso.setText(curso.getString("CursoNombre"));
                nombre_curso.setTypeface(Typeface.createFromAsset(NotasActivity.this.getAssets(), "fonts/Zizou Slab-Medium.otf"));
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
                this.alertDialog = MessageUtils.buildLoadingAlert(NotasActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                NotasActivity.this.prepareHandleException(e);
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
                    NotasActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(NotasActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("00000")) {
                        JSONArray jsonArray = aJSONObject.getJSONArray("Cursos");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            NotasActivity.this.cursosList.add(jsonArray.getJSONObject(i));
                        }
                        NotasActivity.this.cursosAdapter.notifyDataSetChanged();
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        NotasActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(NotasActivity.this, aJSONObject.getString("MsgError"));
                    }
                }
            } catch (Exception e) {
                NotasActivity.this.prepareHandleException(e);
            }
        }
    }

    static {
        selectedCursoNombre = StringUtils.EMPTY;
        selectedCodCurso = StringUtils.EMPTY;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04121();
            registerReceiver(this.receiver, intentFilter);
            this.cursosList = new ArrayList();
            this.cursosAdapter = new CursosAdapter(this, C0386R.layout.cursosrow, this.cursosList);
            ListView listview = (ListView) findViewById(C0386R.id.lista_cursos);
            listview.setAdapter(this.cursosAdapter);
            listview.setOnItemClickListener(new C04132());
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.cursosList.clear();
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String codAlumno;
                String url;
                if (session.getString("TipoUsuario", StringUtils.EMPTY).equalsIgnoreCase("PADRE")) {
                    String codigo = session.getString("Codigo", StringUtils.EMPTY);
                    codAlumno = session.getString("CodAlumno", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/CursoAlumnoPadre/?Codigo=" + codigo + "&CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                }
                codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                url = Environment.BASE_URL + "/CursoAlumno/?CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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

    public void onSelectCompaneros(int position) {
        try {
            Intent aIntent = new Intent(this, CompanerosActivity.class);
            aIntent.putExtra("OptionNumber", 98);
            aIntent.putExtra("LoggedIn", true);
            selectedCursoNombre = ((JSONObject) this.cursosList.get(position)).getString("CursoNombre");
            selectedCodCurso = ((JSONObject) this.cursosList.get(position)).getString("CodCurso");
            startActivity(aIntent);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    public void onSelectNotas(int position) {
        try {
            Intent aIntent = new Intent(this, DetalleNotaActivity.class);
            aIntent.putExtra("OptionNumber", 99);
            aIntent.putExtra("LoggedIn", true);
            selectedCursoNombre = ((JSONObject) this.cursosList.get(position)).getString("CursoNombre");
            selectedCodCurso = ((JSONObject) this.cursosList.get(position)).getString("CodCurso");
            startActivity(aIntent);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }
}
