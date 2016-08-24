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
import pe.edu.upc.mobile.Utilities.EspaciosDeportivosUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class DisponibilidadEspaciosDeportivosActivity extends MenuActivity {
    public static String fechaReserva;
    public static String fechaReservaPrint;
    public static String horaInicio;
    public static String horaInicioPrint;
    private DisponibilidadAdapter disponibilidadAdapter;
    private ArrayList<String> disponibilidadList;
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.DisponibilidadEspaciosDeportivosActivity.1 */
    class C03991 extends BroadcastReceiver {
        C03991() {
        }

        public void onReceive(Context context, Intent intent) {
            DisponibilidadEspaciosDeportivosActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.DisponibilidadEspaciosDeportivosActivity.2 */
    class C04002 implements OnItemClickListener {
        C04002() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            String[] split = ((String) DisponibilidadEspaciosDeportivosActivity.this.disponibilidadList.get(position)).split("://");
            if (split[0].equalsIgnoreCase("sc")) {
                DisponibilidadEspaciosDeportivosActivity.horaInicioPrint = split[1].split(" - ")[0];
                DisponibilidadEspaciosDeportivosActivity.horaInicio = DisponibilidadEspaciosDeportivosActivity.horaInicioPrint.replace(":", StringUtils.EMPTY);
                DisponibilidadEspaciosDeportivosActivity.fechaReserva = split[2];
                DisponibilidadEspaciosDeportivosActivity.fechaReservaPrint = split[3];
                Intent aIntent = new Intent(DisponibilidadEspaciosDeportivosActivity.this, ReservarEspaciosDeportivosActivity.class);
                aIntent.putExtra("OptionNumber", 96);
                aIntent.putExtra("LoggedIn", true);
                DisponibilidadEspaciosDeportivosActivity.this.startActivity(aIntent);
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
                    convertView = ((LayoutInflater) DisponibilidadEspaciosDeportivosActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.disponibilidadespaciosdeportivosrow, null);
                } catch (Exception e) {
                    DisponibilidadEspaciosDeportivosActivity.this.prepareHandleException(e);
                }
            }
            String disponibilidad = (String) DisponibilidadEspaciosDeportivosActivity.this.disponibilidadList.get(position);
            if (disponibilidad != null) {
                String[] split = disponibilidad.split("://");
                TextView txtViewDisponibilidadTitle = (TextView) convertView.findViewById(C0386R.id.txtViewDisponibilidadTitle);
                LinearLayout disponibilidadLayout = (LinearLayout) convertView.findViewById(C0386R.id.disponibilidadLayout);
                Typeface typefaceRegular = Typeface.createFromAsset(DisponibilidadEspaciosDeportivosActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
                Typeface typefaceBold = Typeface.createFromAsset(DisponibilidadEspaciosDeportivosActivity.this.getAssets(), "fonts/Zizou Slab-Bold.otf");
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
                this.alertDialog = MessageUtils.buildLoadingAlert(DisponibilidadEspaciosDeportivosActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                DisponibilidadEspaciosDeportivosActivity.this.prepareHandleException(e);
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
                    DisponibilidadEspaciosDeportivosActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(DisponibilidadEspaciosDeportivosActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("00000")) {
                        JSONArray jsonArray = aJSONObject.getJSONArray("HorarioDia");
                        ArrayList<JSONObject> resultsList = new ArrayList();
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            resultsList.add(jsonArray.getJSONObject(i));
                        }
                        DisponibilidadEspaciosDeportivosActivity.this.disponibilidadList.clear();
                        DisponibilidadEspaciosDeportivosActivity.this.disponibilidadList.addAll(EspaciosDeportivosUtils.disponibilidadFromResults(resultsList));
                        DisponibilidadEspaciosDeportivosActivity.this.disponibilidadAdapter.notifyDataSetChanged();
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        DisponibilidadEspaciosDeportivosActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(DisponibilidadEspaciosDeportivosActivity.this, aJSONObject.getString("MsgError"));
                    }
                }
            } catch (Exception e) {
                DisponibilidadEspaciosDeportivosActivity.this.prepareHandleException(e);
            }
        }
    }

    static {
        fechaReserva = StringUtils.EMPTY;
        fechaReservaPrint = StringUtils.EMPTY;
        horaInicioPrint = StringUtils.EMPTY;
        horaInicio = StringUtils.EMPTY;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C03991();
            registerReceiver(this.receiver, intentFilter);
            ((TextView) this.app.findViewById(C0386R.id.txtViewTitle)).setText("RESERVAS DEPORTIVAS");
            this.disponibilidadList = new ArrayList();
            this.disponibilidadAdapter = new DisponibilidadAdapter(this, C0386R.layout.disponibilidadespaciosdeportivosrow, this.disponibilidadList);
            ListView listview = (ListView) findViewById(C0386R.id.lista_disponibilidad);
            listview.setAdapter(this.disponibilidadAdapter);
            listview.setOnItemClickListener(new C04002());
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            fechaReserva = StringUtils.EMPTY;
            fechaReservaPrint = StringUtils.EMPTY;
            horaInicioPrint = StringUtils.EMPTY;
            horaInicio = StringUtils.EMPTY;
            EasyTracker.getInstance(this).activityStart(this);
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                String url = Environment.BASE_URL + "/DisponibilidadED/?CodSede=" + EspaciosDeportivosActivity.sedeKey + "&CodED=" + EspaciosDeportivosActivity.espacioCode + "&NumHoras=" + EspaciosDeportivosActivity.numeroHoras + "&CodAlumno=" + codAlumno + "&FechaIni=" + EspaciosDeportivosUtils.startDateFromWeek(EspaciosDeportivosActivity.semanaConsulta) + "&FechaFin=" + EspaciosDeportivosUtils.endDateFromWeek(EspaciosDeportivosActivity.semanaConsulta) + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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
