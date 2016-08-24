package pe.edu.upc.mobile.UPCMovil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
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
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.MutableDateTime;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.UPCMovil.SelectorDialogFragment.SelectOptionDialogListener;
import pe.edu.upc.mobile.Utilities.CubiculosComputadorasUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;

public class CubiculosComputadorasActivity extends MenuActivity implements SelectOptionDialogListener {
    public static String fechaReserva;
    public static String horaReserva;
    public static String localReserva;
    public static String localReservaCode;
    public static String numeroHoras;
    public static String tipoRecurso;
    public static String tipoRecursoCode;
    String[] opciones;
    private OpcionesAdapter opcionesAdapter;
    private ArrayList<String> opcionesList;
    private BroadcastReceiver receiver;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.CubiculosComputadorasActivity.1 */
    class C03911 extends BroadcastReceiver {
        C03911() {
        }

        public void onReceive(Context context, Intent intent) {
            CubiculosComputadorasActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.CubiculosComputadorasActivity.2 */
    class C03922 implements OnItemClickListener {
        C03922() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            switch (position) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    try {
                        SelectorDialogFragment.newInstance(position, new ArrayList(Arrays.asList(new String[]{"CAMPUS MONTERRICO"}))).show(CubiculosComputadorasActivity.this.getSupportFragmentManager(), "fragment_selector");
                    } catch (Exception e) {
                        CubiculosComputadorasActivity.this.prepareHandleException(e);
                    }
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    try {
                        SelectorDialogFragment.newInstance(position, new ArrayList(Arrays.asList(new String[]{"COMPUTADORAS", "CUB\u00cdCULOS"}))).show(CubiculosComputadorasActivity.this.getSupportFragmentManager(), "fragment_selector");
                    } catch (Exception e2) {
                        CubiculosComputadorasActivity.this.prepareHandleException(e2);
                    }
                case Value.STRING_FIELD_NUMBER /*2*/:
                    try {
                        SelectorDialogFragment.newInstance(position, new ArrayList(Arrays.asList(new String[]{"1", "2"}))).show(CubiculosComputadorasActivity.this.getSupportFragmentManager(), "fragment_selector");
                    } catch (Exception e22) {
                        CubiculosComputadorasActivity.this.prepareHandleException(e22);
                    }
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    try {
                        SelectorDialogFragment.newInstance(position, CubiculosComputadorasUtils.horasReserva()).show(CubiculosComputadorasActivity.this.getSupportFragmentManager(), "fragment_selector");
                    } catch (Exception e222) {
                        CubiculosComputadorasActivity.this.prepareHandleException(e222);
                    }
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    try {
                        SelectorDialogFragment.newInstance(position, CubiculosComputadorasUtils.diasReserva()).show(CubiculosComputadorasActivity.this.getSupportFragmentManager(), "fragment_selector");
                    } catch (Exception e2222) {
                        CubiculosComputadorasActivity.this.prepareHandleException(e2222);
                    }
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
                    convertView = ((LayoutInflater) CubiculosComputadorasActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.cubiculocomputadorasrow, null);
                } catch (Exception e) {
                    CubiculosComputadorasActivity.this.prepareHandleException(e);
                }
            }
            String opcion = (String) CubiculosComputadorasActivity.this.opcionesList.get(position);
            if (opcion != null) {
                if (position < CubiculosComputadorasActivity.this.opcionesList.size() - 1) {
                    LinearLayout linearLayout = (LinearLayout) convertView.findViewById(C0386R.id.cubiculosComputadorasRow);
                    linearLayout.setVisibility(0);
                    ((RelativeLayout) convertView.findViewById(C0386R.id.viewButton)).setVisibility(8);
                    if (position % 2 == 0) {
                        linearLayout.setBackgroundColor(Color.parseColor("#EDEDED"));
                    } else {
                        linearLayout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                    }
                    TextView txtViewCubiculosComputadorasOpcionTitle = (TextView) convertView.findViewById(C0386R.id.txtViewCubiculosComputadorasOpcionTitle);
                    txtViewCubiculosComputadorasOpcionTitle.setText(opcion);
                    txtViewCubiculosComputadorasOpcionTitle.setTypeface(Typeface.createFromAsset(CubiculosComputadorasActivity.this.getAssets(), "fonts/Zizou Slab-Medium.otf"));
                    TextView txtViewCubiculosComputadorasOpcionSelection = (TextView) convertView.findViewById(C0386R.id.txtViewCubiculosComputadorasOpcionSelection);
                    switch (position) {
                        case MutableDateTime.ROUND_NONE /*0*/:
                            if (!CubiculosComputadorasActivity.localReserva.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewCubiculosComputadorasOpcionSelection.setText(CubiculosComputadorasActivity.localReserva);
                                break;
                            }
                            txtViewCubiculosComputadorasOpcionSelection.setText("seleccione");
                            break;
                        case Value.TYPE_FIELD_NUMBER /*1*/:
                            if (!CubiculosComputadorasActivity.tipoRecurso.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewCubiculosComputadorasOpcionSelection.setText(CubiculosComputadorasActivity.tipoRecurso);
                                break;
                            }
                            txtViewCubiculosComputadorasOpcionSelection.setText("seleccione");
                            break;
                        case Value.STRING_FIELD_NUMBER /*2*/:
                            if (!CubiculosComputadorasActivity.numeroHoras.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewCubiculosComputadorasOpcionSelection.setText(CubiculosComputadorasActivity.numeroHoras);
                                break;
                            }
                            txtViewCubiculosComputadorasOpcionSelection.setText("seleccione");
                            break;
                        case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                            if (!CubiculosComputadorasActivity.horaReserva.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewCubiculosComputadorasOpcionSelection.setText(CubiculosComputadorasActivity.horaReserva);
                                break;
                            }
                            txtViewCubiculosComputadorasOpcionSelection.setText("seleccione");
                            break;
                        case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                            if (!CubiculosComputadorasActivity.fechaReserva.equalsIgnoreCase(StringUtils.EMPTY)) {
                                txtViewCubiculosComputadorasOpcionSelection.setText(CubiculosComputadorasActivity.fechaReserva);
                                break;
                            }
                            txtViewCubiculosComputadorasOpcionSelection.setText("seleccione");
                            break;
                    }
                    txtViewCubiculosComputadorasOpcionSelection.setTypeface(Typeface.createFromAsset(CubiculosComputadorasActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf"));
                } else {
                    ((LinearLayout) convertView.findViewById(C0386R.id.cubiculosComputadorasRow)).setVisibility(8);
                    ((RelativeLayout) convertView.findViewById(C0386R.id.viewButton)).setVisibility(0);
                }
            }
            return convertView;
        }
    }

    public CubiculosComputadorasActivity() {
        this.opciones = new String[]{"Local", "Tipo de Recurso", "N\u00famero de Horas", "Hora de Reserva", "Fecha de Reserva", "Consultar"};
    }

    static {
        localReserva = StringUtils.EMPTY;
        tipoRecurso = StringUtils.EMPTY;
        numeroHoras = StringUtils.EMPTY;
        horaReserva = StringUtils.EMPTY;
        fechaReserva = StringUtils.EMPTY;
        localReservaCode = StringUtils.EMPTY;
        tipoRecursoCode = StringUtils.EMPTY;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C03911();
            registerReceiver(this.receiver, intentFilter);
            this.opcionesList = new ArrayList(Arrays.asList(this.opciones));
            this.opcionesAdapter = new OpcionesAdapter(this, C0386R.layout.cubiculoscomputadoraslayout, this.opcionesList);
            ListView listview = (ListView) findViewById(C0386R.id.lista_opciones);
            listview.setAdapter(this.opcionesAdapter);
            listview.setOnItemClickListener(new C03922());
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            localReserva = StringUtils.EMPTY;
            tipoRecurso = StringUtils.EMPTY;
            numeroHoras = StringUtils.EMPTY;
            horaReserva = StringUtils.EMPTY;
            fechaReserva = StringUtils.EMPTY;
            localReservaCode = StringUtils.EMPTY;
            tipoRecursoCode = StringUtils.EMPTY;
            this.opcionesAdapter.notifyDataSetChanged();
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

    public void onFinishSelectorDialog(String option, int type) {
        switch (type) {
            case MutableDateTime.ROUND_NONE /*0*/:
                localReserva = option;
                if (option.equalsIgnoreCase("CAMPUS MONTERRICO")) {
                    localReservaCode = "A";
                }
                this.opcionesAdapter.notifyDataSetChanged();
            case Value.TYPE_FIELD_NUMBER /*1*/:
                tipoRecurso = option;
                if (option.equalsIgnoreCase("COMPUTADORAS")) {
                    tipoRecursoCode = "CO";
                } else if (option.equalsIgnoreCase("CUB\u00cdCULOS")) {
                    tipoRecursoCode = "CU";
                }
                this.opcionesAdapter.notifyDataSetChanged();
            case Value.STRING_FIELD_NUMBER /*2*/:
                numeroHoras = option;
                this.opcionesAdapter.notifyDataSetChanged();
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                horaReserva = option;
                this.opcionesAdapter.notifyDataSetChanged();
                this.opcionesAdapter.notifyDataSetChanged();
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                fechaReserva = option;
                this.opcionesAdapter.notifyDataSetChanged();
            default:
        }
    }

    public void onClickConsultarButton(View view) {
        if (localReservaCode.equalsIgnoreCase(StringUtils.EMPTY) || tipoRecurso.equalsIgnoreCase(StringUtils.EMPTY) || numeroHoras.equalsIgnoreCase(StringUtils.EMPTY) || horaReserva.equalsIgnoreCase(StringUtils.EMPTY) || fechaReserva.equalsIgnoreCase(StringUtils.EMPTY)) {
            MessageUtils.showErrorAlert(this, "Por favor, complete todos los campos.");
            return;
        }
        Intent aIntent = new Intent(this, DisponibilidadCubiculosComputadorasActivity.class);
        aIntent.putExtra("OptionNumber", 95);
        aIntent.putExtra("LoggedIn", true);
        startActivity(aIntent);
    }
}
