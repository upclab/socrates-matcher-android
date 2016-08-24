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
import pe.edu.upc.mobile.Utilities.CubiculosComputadorasUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class DisponibilidadCubiculosComputadorasActivity extends MenuActivity {
    public static String codRecurso;
    public static String nombreRecurso;
    private DisponibilidadAdapter disponibilidadAdapter;
    private ArrayList<String> disponibilidadList;
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.DisponibilidadCubiculosComputadorasActivity.1 */
    class C03971 extends BroadcastReceiver {
        C03971() {
        }

        public void onReceive(Context context, Intent intent) {
            DisponibilidadCubiculosComputadorasActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.DisponibilidadCubiculosComputadorasActivity.2 */
    class C03982 implements OnItemClickListener {
        C03982() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            String[] split = ((String) DisponibilidadCubiculosComputadorasActivity.this.disponibilidadList.get(position)).split("://");
            if (split[0].equalsIgnoreCase("sc")) {
                DisponibilidadCubiculosComputadorasActivity.nombreRecurso = split[1];
                DisponibilidadCubiculosComputadorasActivity.codRecurso = split[2];
                Intent aIntent = new Intent(DisponibilidadCubiculosComputadorasActivity.this, ReservarCubiculosComputadorasActivity.class);
                aIntent.putExtra("OptionNumber", 94);
                aIntent.putExtra("LoggedIn", true);
                DisponibilidadCubiculosComputadorasActivity.this.startActivity(aIntent);
            }
        }
    }

    private class DisponibilidadAdapter extends ArrayAdapter<String> {
        public DisponibilidadAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                try {
                    convertView = ((LayoutInflater) DisponibilidadCubiculosComputadorasActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.disponibilidadescubiculoscomputadorasrow, null);
                } catch (Exception e) {
                    DisponibilidadCubiculosComputadorasActivity.this.prepareHandleException(e);
                }
            }
            String disponibilidad = (String) DisponibilidadCubiculosComputadorasActivity.this.disponibilidadList.get(position);
            if (disponibilidad != null) {
                String[] split = disponibilidad.split("://");
                TextView txtViewDisponibilidadTitle = (TextView) convertView.findViewById(C0386R.id.txtViewDisponibilidadTitle);
                LinearLayout disponibilidadLayout = (LinearLayout) convertView.findViewById(C0386R.id.disponibilidadLayout);
                Typeface typefaceRegular = Typeface.createFromAsset(DisponibilidadCubiculosComputadorasActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
                Typeface typefaceBold = Typeface.createFromAsset(DisponibilidadCubiculosComputadorasActivity.this.getAssets(), "fonts/Zizou Slab-Bold.otf");
                if (split[0].equalsIgnoreCase("av") || split[0].equalsIgnoreCase("un")) {
                    disponibilidadLayout.setVisibility(8);
                    txtViewDisponibilidadTitle.setVisibility(0);
                    txtViewDisponibilidadTitle.setText(split[1]);
                    if (split[0].equalsIgnoreCase("av")) {
                        txtViewDisponibilidadTitle.setTextColor(Color.parseColor("#FF0000"));
                    } else {
                        txtViewDisponibilidadTitle.setTextColor(Color.parseColor("#535353"));
                    }
                    txtViewDisponibilidadTitle.setTypeface(typefaceRegular);
                } else {
                    txtViewDisponibilidadTitle.setVisibility(8);
                    disponibilidadLayout.setVisibility(0);
                    if (position % 2 == 0) {
                        disponibilidadLayout.setBackgroundColor(Color.parseColor("#EDEDED"));
                    } else {
                        disponibilidadLayout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                    }
                    TextView txtViewDisponibilidad = (TextView) disponibilidadLayout.findViewById(C0386R.id.txtViewDisponibilidad);
                    txtViewDisponibilidad.setText(split[1]);
                    txtViewDisponibilidad.setTypeface(typefaceBold);
                    ((TextView) disponibilidadLayout.findViewById(C0386R.id.txtViewDisponibilidadAvailable)).setTypeface(typefaceRegular);
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
                this.alertDialog = MessageUtils.buildLoadingAlert(DisponibilidadCubiculosComputadorasActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                DisponibilidadCubiculosComputadorasActivity.this.prepareHandleException(e);
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
                    DisponibilidadCubiculosComputadorasActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(DisponibilidadCubiculosComputadorasActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("00000")) {
                        JSONArray jsonArray = aJSONObject.getJSONArray("Recursos");
                        ArrayList<JSONObject> resultsList = new ArrayList();
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            resultsList.add(jsonArray.getJSONObject(i));
                        }
                        DisponibilidadCubiculosComputadorasActivity.this.disponibilidadList.clear();
                        DisponibilidadCubiculosComputadorasActivity.this.disponibilidadList.addAll(CubiculosComputadorasUtils.disponibilidadFromResults(resultsList));
                        DisponibilidadCubiculosComputadorasActivity.this.disponibilidadAdapter.notifyDataSetChanged();
                    } else if (aCodError.equalsIgnoreCase("00002")) {
                        MessageUtils.showErrorAlert(DisponibilidadCubiculosComputadorasActivity.this, aJSONObject.getString("MsgError"));
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        DisponibilidadCubiculosComputadorasActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(DisponibilidadCubiculosComputadorasActivity.this, aJSONObject.getString("Mensaje"));
                    }
                }
            } catch (Exception e) {
                DisponibilidadCubiculosComputadorasActivity.this.prepareHandleException(e);
            }
        }
    }

    static {
        nombreRecurso = StringUtils.EMPTY;
        codRecurso = StringUtils.EMPTY;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C03971();
            registerReceiver(this.receiver, intentFilter);
            ((TextView) this.app.findViewById(C0386R.id.txtViewTitle)).setText("RESERVAS DE RECURSOS");
            this.disponibilidadList = new ArrayList();
            this.disponibilidadAdapter = new DisponibilidadAdapter(this, C0386R.layout.disponibilidadescubiculoscomputadorasrow, this.disponibilidadList);
            ListView listview = (ListView) findViewById(C0386R.id.lista_disponibilidad);
            listview.setAdapter(this.disponibilidadAdapter);
            listview.setOnItemClickListener(new C03982());
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            nombreRecurso = StringUtils.EMPTY;
            codRecurso = StringUtils.EMPTY;
            EasyTracker.getInstance(this).activityStart(this);
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                String url = Environment.BASE_URL + "/RecursosDisponible/?TipoRecurso=" + CubiculosComputadorasActivity.tipoRecursoCode + "&Local=" + CubiculosComputadorasActivity.localReservaCode + "&FecIni=" + CubiculosComputadorasUtils.parameterWithStartDateString(CubiculosComputadorasActivity.fechaReserva, CubiculosComputadorasActivity.horaReserva) + "&CanHoras=" + CubiculosComputadorasActivity.numeroHoras + "&FechaFin=" + CubiculosComputadorasUtils.parameterWithEndDateString(CubiculosComputadorasActivity.fechaReserva, CubiculosComputadorasActivity.horaReserva, Integer.parseInt(CubiculosComputadorasActivity.numeroHoras)) + "&CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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
