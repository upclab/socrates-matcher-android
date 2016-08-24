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
import pe.edu.upc.mobile.Utilities.CursoProfesorUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class CursosActivity extends MenuActivity {
    public static String cursoId;
    public static String cursoNombre;
    public static String grupo;
    public static String modalidad;
    public static String periodo;
    public static String seccion;
    private CursosProfesorAdapter cursoProfesorAdapter;
    private ArrayList<String> cursosList;
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.CursosActivity.1 */
    class C03931 extends BroadcastReceiver {
        C03931() {
        }

        public void onReceive(Context context, Intent intent) {
            CursosActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.CursosActivity.2 */
    class C03942 implements OnItemClickListener {
        C03942() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            String[] split = ((String) CursosActivity.this.cursosList.get(position)).split("://");
            if (split[0].equalsIgnoreCase("cur")) {
                CursosActivity.cursoNombre = split[1];
                CursosActivity.cursoId = split[2];
                CursosActivity.modalidad = split[5];
                CursosActivity.periodo = split[6];
                CursosActivity.seccion = split[3];
                CursosActivity.grupo = split[4];
                Intent aIntent = new Intent(CursosActivity.this, CompanerosActivity.class);
                aIntent.putExtra("OptionNumber", 98);
                aIntent.putExtra("LoggedIn", true);
                CursosActivity.this.startActivity(aIntent);
            }
        }
    }

    private class CursosProfesorAdapter extends ArrayAdapter<String> {
        public CursosProfesorAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                try {
                    convertView = ((LayoutInflater) CursosActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.cursosprofesorrow, null);
                } catch (Exception e) {
                    CursosActivity.this.prepareHandleException(e);
                }
            }
            String curso = (String) CursosActivity.this.cursosList.get(position);
            if (curso != null) {
                String[] split = curso.split("://");
                TextView txtViewModalidadTitle = (TextView) convertView.findViewById(C0386R.id.txtViewModalidadTitle);
                LinearLayout cursoLayout = (LinearLayout) convertView.findViewById(C0386R.id.cursoLayout);
                Typeface typefaceRegular = Typeface.createFromAsset(CursosActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
                Typeface typefaceBold = Typeface.createFromAsset(CursosActivity.this.getAssets(), "fonts/Zizou Slab-Bold.otf");
                if (split[0].equalsIgnoreCase("mod")) {
                    cursoLayout.setVisibility(8);
                    txtViewModalidadTitle.setVisibility(0);
                    txtViewModalidadTitle.setText(split[2] + " " + split[3]);
                    txtViewModalidadTitle.setTextColor(Color.parseColor("#FF0000"));
                    txtViewModalidadTitle.setTypeface(typefaceRegular);
                } else {
                    txtViewModalidadTitle.setVisibility(8);
                    cursoLayout.setVisibility(0);
                    if (position % 2 == 0) {
                        cursoLayout.setBackgroundColor(Color.parseColor("#EDEDED"));
                    } else {
                        cursoLayout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                    }
                    TextView txtViewDisponibilidad = (TextView) cursoLayout.findViewById(C0386R.id.txtViewCursoProfesorNombre);
                    txtViewDisponibilidad.setText(split[1] + "\n(" + split[2] + ")");
                    txtViewDisponibilidad.setTypeface(typefaceBold);
                    TextView txtViewDisponibilidadAvailable = (TextView) cursoLayout.findViewById(C0386R.id.txtViewCursoProfesorSeccionGrupo);
                    txtViewDisponibilidadAvailable.setTypeface(typefaceRegular);
                    txtViewDisponibilidadAvailable.setText("Seccion: " + split[3] + "\nGrupo: " + split[4]);
                }
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
                this.alertDialog = MessageUtils.buildLoadingAlert(CursosActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                CursosActivity.this.prepareHandleException(e);
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
                    CursosActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(CursosActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("00000")) {
                        JSONArray jsonArray = aJSONObject.getJSONArray("modalidades");
                        ArrayList<JSONObject> resultsList = new ArrayList();
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            resultsList.add(jsonArray.getJSONObject(i));
                        }
                        CursosActivity.this.cursosList.addAll(CursoProfesorUtils.cursosFromResults(resultsList));
                        CursosActivity.this.cursoProfesorAdapter.notifyDataSetChanged();
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        CursosActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(CursosActivity.this, aJSONObject.getString("MsgError"));
                    }
                }
            } catch (Exception e) {
                CursosActivity.this.prepareHandleException(e);
            }
        }
    }

    static {
        cursoNombre = StringUtils.EMPTY;
        cursoId = StringUtils.EMPTY;
        modalidad = StringUtils.EMPTY;
        periodo = StringUtils.EMPTY;
        seccion = StringUtils.EMPTY;
        grupo = StringUtils.EMPTY;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C03931();
            registerReceiver(this.receiver, intentFilter);
            this.cursosList = new ArrayList();
            this.cursoProfesorAdapter = new CursosProfesorAdapter(this, C0386R.layout.cursosprofesorrow, this.cursosList);
            ListView listview = (ListView) findViewById(C0386R.id.lista_cursos);
            listview.setAdapter(this.cursoProfesorAdapter);
            listview.setOnItemClickListener(new C03942());
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
                String codigo = session.getString("Codigo", StringUtils.EMPTY);
                String url = Environment.BASE_URL + "/ListadoCursosProfesor/?Codigo=" + codigo + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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
