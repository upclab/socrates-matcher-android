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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.analytics.tracking.android.EasyTracker;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.joda.time.MutableDateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.RESTClient.Environment;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.UPCMovil.SelectorDialogFragment.SelectOptionDialogListener;
import pe.edu.upc.mobile.Utilities.EspaciosDeportivosUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class EspaciosDeportivosActivity extends MenuActivity implements SelectOptionDialogListener {
    public static String actividadCode;
    public static String actividadName;
    public static String espacioCode;
    public static String espacioName;
    public static String numeroHoras;
    public static String sedeKey;
    public static String sedeName;
    public static String semanaConsulta;
    String[] opciones;
    private OpcionesAdapter opcionesAdapter;
    private ArrayList<String> opcionesList;
    private BroadcastReceiver receiver;
    private ArrayList<JSONObject> resultsList;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.EspaciosDeportivosActivity.1 */
    class C04011 extends BroadcastReceiver {
        C04011() {
        }

        public void onReceive(Context context, Intent intent) {
            EspaciosDeportivosActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.EspaciosDeportivosActivity.2 */
    class C04022 implements OnItemClickListener {
        C04022() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            switch (position) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    try {
                        SelectorDialogFragment.newInstance(position, EspaciosDeportivosUtils.sedesNamesFromResults(EspaciosDeportivosActivity.this.resultsList)).show(EspaciosDeportivosActivity.this.getSupportFragmentManager(), "fragment_selector");
                    } catch (JSONException e) {
                        EspaciosDeportivosActivity.this.prepareHandleException(e);
                    }
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    try {
                        if (EspaciosDeportivosActivity.sedeName.equalsIgnoreCase(StringUtils.EMPTY) || EspaciosDeportivosActivity.sedeKey.equalsIgnoreCase(StringUtils.EMPTY)) {
                            MessageUtils.showErrorAlert(EspaciosDeportivosActivity.this, "Por favor, complete los campos anteriores.");
                            return;
                        }
                        SelectorDialogFragment.newInstance(position, EspaciosDeportivosUtils.espaciosNamesFromResults(EspaciosDeportivosActivity.this.resultsList, EspaciosDeportivosActivity.sedeKey)).show(EspaciosDeportivosActivity.this.getSupportFragmentManager(), "fragment_selector");
                    } catch (JSONException e2) {
                        EspaciosDeportivosActivity.this.prepareHandleException(e2);
                    }
                case Value.STRING_FIELD_NUMBER /*2*/:
                    try {
                        if (EspaciosDeportivosActivity.sedeName.equalsIgnoreCase(StringUtils.EMPTY) || EspaciosDeportivosActivity.sedeKey.equalsIgnoreCase(StringUtils.EMPTY) || EspaciosDeportivosActivity.espacioCode.equalsIgnoreCase(StringUtils.EMPTY) || EspaciosDeportivosActivity.espacioName.equalsIgnoreCase(StringUtils.EMPTY)) {
                            MessageUtils.showErrorAlert(EspaciosDeportivosActivity.this, "Por favor, complete los campos anteriores.");
                            return;
                        }
                        SelectorDialogFragment.newInstance(position, EspaciosDeportivosUtils.actividadesNamesFromResults(EspaciosDeportivosActivity.this.resultsList, EspaciosDeportivosActivity.sedeKey, EspaciosDeportivosActivity.espacioCode)).show(EspaciosDeportivosActivity.this.getSupportFragmentManager(), "fragment_selector");
                    } catch (JSONException e22) {
                        EspaciosDeportivosActivity.this.prepareHandleException(e22);
                    }
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    SelectorDialogFragment.newInstance(position, new ArrayList(Arrays.asList(new String[]{"1", "2"}))).show(EspaciosDeportivosActivity.this.getSupportFragmentManager(), "fragment_selector");
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    SelectorDialogFragment.newInstance(position, EspaciosDeportivosUtils.weeksNamesFromNow()).show(EspaciosDeportivosActivity.this.getSupportFragmentManager(), "fragment_selector");
                default:
            }
        }
    }

    private class OpcionesAdapter extends ArrayAdapter<String> {
        public OpcionesAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                try {
                    convertView = ((LayoutInflater) EspaciosDeportivosActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.espaciosdeportivosrow, null);
                } catch (Exception e) {
                    EspaciosDeportivosActivity.this.prepareHandleException(e);
                }
            }
            String opcion = (String) EspaciosDeportivosActivity.this.opcionesList.get(position);
            if (opcion != null) {
                if (position < EspaciosDeportivosActivity.this.opcionesList.size() - 1) {
                    LinearLayout linearLayout = (LinearLayout) convertView.findViewById(C0386R.id.espaciosDeportivosRow);
                    linearLayout.setVisibility(0);
                    ((RelativeLayout) convertView.findViewById(C0386R.id.viewButton)).setVisibility(8);
                    if (position % 2 == 0) {
                        linearLayout.setBackgroundColor(Color.parseColor("#EDEDED"));
                    } else {
                        linearLayout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                    }
                    TextView txtViewEspacioDeportivosOpcionTitle = (TextView) convertView.findViewById(C0386R.id.txtViewEspacioDeportivosOpcionTitle);
                    txtViewEspacioDeportivosOpcionTitle.setText(opcion);
                    txtViewEspacioDeportivosOpcionTitle.setTypeface(Typeface.createFromAsset(EspaciosDeportivosActivity.this.getAssets(), "fonts/Zizou Slab-Medium.otf"));
                    TextView txtViewEspacioDeportivosOpcionSelection = (TextView) convertView.findViewById(C0386R.id.txtViewEspacioDeportivosOpcionSelection);
                    switch (position) {
                        case MutableDateTime.ROUND_NONE /*0*/:
                            if (!EspaciosDeportivosActivity.sedeName.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewEspacioDeportivosOpcionSelection.setText(EspaciosDeportivosActivity.sedeName);
                                break;
                            }
                            txtViewEspacioDeportivosOpcionSelection.setText("seleccione");
                            break;
                        case Value.TYPE_FIELD_NUMBER /*1*/:
                            if (!EspaciosDeportivosActivity.espacioName.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewEspacioDeportivosOpcionSelection.setText(EspaciosDeportivosActivity.espacioName);
                                break;
                            }
                            txtViewEspacioDeportivosOpcionSelection.setText("seleccione");
                            break;
                        case Value.STRING_FIELD_NUMBER /*2*/:
                            if (!EspaciosDeportivosActivity.actividadName.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewEspacioDeportivosOpcionSelection.setText(EspaciosDeportivosActivity.actividadName);
                                break;
                            }
                            txtViewEspacioDeportivosOpcionSelection.setText("seleccione");
                            break;
                        case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                            if (!EspaciosDeportivosActivity.numeroHoras.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewEspacioDeportivosOpcionSelection.setText(EspaciosDeportivosActivity.numeroHoras);
                                break;
                            }
                            txtViewEspacioDeportivosOpcionSelection.setText("seleccione");
                            break;
                        case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                            if (!EspaciosDeportivosActivity.semanaConsulta.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewEspacioDeportivosOpcionSelection.setText(EspaciosDeportivosActivity.semanaConsulta);
                                break;
                            }
                            txtViewEspacioDeportivosOpcionSelection.setText("seleccione");
                            break;
                    }
                    txtViewEspacioDeportivosOpcionSelection.setTypeface(Typeface.createFromAsset(EspaciosDeportivosActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf"));
                } else {
                    ((LinearLayout) convertView.findViewById(C0386R.id.espaciosDeportivosRow)).setVisibility(8);
                    ((RelativeLayout) convertView.findViewById(C0386R.id.viewButton)).setVisibility(0);
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
                this.alertDialog = MessageUtils.buildLoadingAlert(EspaciosDeportivosActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                EspaciosDeportivosActivity.this.prepareHandleException(e);
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
                    EspaciosDeportivosActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(EspaciosDeportivosActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject aJSONObject = new JSONObject(result);
                    String aCodError = aJSONObject.getString("CodError");
                    if (aCodError.equalsIgnoreCase("00000")) {
                        JSONArray jsonArray = aJSONObject.getJSONArray("Sedes");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            EspaciosDeportivosActivity.this.resultsList.add(jsonArray.getJSONObject(i));
                        }
                    } else if (aCodError.equalsIgnoreCase("00003")) {
                        EspaciosDeportivosActivity.this.loggout(true);
                    } else {
                        MessageUtils.showErrorAlert(EspaciosDeportivosActivity.this, aJSONObject.getString("MsgError"));
                    }
                }
            } catch (Exception e) {
                EspaciosDeportivosActivity.this.prepareHandleException(e);
            }
        }
    }

    public EspaciosDeportivosActivity() {
        this.opciones = new String[]{"Sede de Reserva", "Espacio Deportivo", "Actividad Deportiva", "N\u00famero de Horas", "Semana de Consulta", "Consultar"};
    }

    static {
        sedeName = StringUtils.EMPTY;
        sedeKey = StringUtils.EMPTY;
        espacioName = StringUtils.EMPTY;
        actividadName = StringUtils.EMPTY;
        espacioCode = StringUtils.EMPTY;
        actividadCode = StringUtils.EMPTY;
        numeroHoras = StringUtils.EMPTY;
        semanaConsulta = StringUtils.EMPTY;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04011();
            registerReceiver(this.receiver, intentFilter);
            this.opcionesList = new ArrayList(Arrays.asList(this.opciones));
            this.resultsList = new ArrayList();
            this.opcionesAdapter = new OpcionesAdapter(this, C0386R.layout.espaciosdeportivosrow, this.opcionesList);
            ListView listview = (ListView) findViewById(C0386R.id.lista_opciones);
            listview.setAdapter(this.opcionesAdapter);
            listview.setOnItemClickListener(new C04022());
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            sedeName = StringUtils.EMPTY;
            sedeKey = StringUtils.EMPTY;
            espacioName = StringUtils.EMPTY;
            actividadName = StringUtils.EMPTY;
            espacioCode = StringUtils.EMPTY;
            actividadCode = StringUtils.EMPTY;
            numeroHoras = StringUtils.EMPTY;
            semanaConsulta = StringUtils.EMPTY;
            this.opcionesAdapter.notifyDataSetChanged();
            EasyTracker.getInstance(this).activityStart(this);
            this.resultsList.clear();
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                String url = Environment.BASE_URL + "/PoblarED/?CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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

    public void onFinishSelectorDialog(String option, int type) {
        switch (type) {
            case MutableDateTime.ROUND_NONE /*0*/:
                try {
                    sedeName = option;
                    sedeKey = EspaciosDeportivosUtils.sedeKeyWithNameFromResults(this.resultsList, option);
                    espacioName = StringUtils.EMPTY;
                    espacioCode = StringUtils.EMPTY;
                    actividadName = StringUtils.EMPTY;
                    actividadCode = StringUtils.EMPTY;
                    this.opcionesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    prepareHandleException(e);
                }
            case Value.TYPE_FIELD_NUMBER /*1*/:
                try {
                    espacioName = option;
                    espacioCode = EspaciosDeportivosUtils.espacioCodeWithNameAndSedeNameFromResults(this.resultsList, option, sedeName);
                    actividadName = StringUtils.EMPTY;
                    actividadCode = StringUtils.EMPTY;
                    this.opcionesAdapter.notifyDataSetChanged();
                } catch (JSONException e2) {
                    prepareHandleException(e2);
                }
            case Value.STRING_FIELD_NUMBER /*2*/:
                try {
                    actividadName = option;
                    actividadCode = EspaciosDeportivosUtils.actividadCodeWithNameAndEspacioNameSedeNameFromResults(this.resultsList, option, espacioName, sedeName);
                    this.opcionesAdapter.notifyDataSetChanged();
                } catch (JSONException e22) {
                    prepareHandleException(e22);
                }
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                numeroHoras = option;
                this.opcionesAdapter.notifyDataSetChanged();
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                semanaConsulta = option;
                this.opcionesAdapter.notifyDataSetChanged();
            default:
        }
    }

    public void onClickConsultarButton(View view) {
        if (sedeName.equalsIgnoreCase(StringUtils.EMPTY) || sedeKey.equalsIgnoreCase(StringUtils.EMPTY) || espacioCode.equalsIgnoreCase(StringUtils.EMPTY) || espacioName.equalsIgnoreCase(StringUtils.EMPTY) || actividadName.equalsIgnoreCase(StringUtils.EMPTY) || actividadCode.equalsIgnoreCase(StringUtils.EMPTY) || numeroHoras.equalsIgnoreCase(StringUtils.EMPTY) || semanaConsulta.equalsIgnoreCase(StringUtils.EMPTY)) {
            MessageUtils.showErrorAlert(this, "Por favor, complete todos los campos.");
            return;
        }
        Intent aIntent = new Intent(this, DisponibilidadEspaciosDeportivosActivity.class);
        aIntent.putExtra("OptionNumber", 97);
        aIntent.putExtra("LoggedIn", true);
        startActivity(aIntent);
    }
}
