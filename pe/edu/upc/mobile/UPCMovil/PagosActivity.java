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

public class PagosActivity extends MenuActivity {
    private PagosAdapter pagosAdapter;
    private ArrayList<JSONObject> pagosList;
    private BroadcastReceiver receiver;
    private TextView txtMensajePagos;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.PagosActivity.1 */
    class C04161 extends BroadcastReceiver {
        C04161() {
        }

        public void onReceive(Context context, Intent intent) {
            PagosActivity.this.finish();
        }
    }

    private class PagosAdapter extends ArrayAdapter<JSONObject> {
        public PagosAdapter(Context context, int textViewResourceId, ArrayList<JSONObject> pagos) {
            super(context, textViewResourceId, pagos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            try {
                JSONObject jsonPago = (JSONObject) PagosActivity.this.pagosList.get(position);
                if (row == null) {
                    row = ((LayoutInflater) PagosActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.pagosrow, null);
                }
                LinearLayout loPagosRow = (LinearLayout) row.findViewById(C0386R.id.loPagosRow);
                if (position % 2 == 0) {
                    loPagosRow.setBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    loPagosRow.setBackgroundColor(Color.parseColor("#DEDEDE"));
                }
                TextView txtNroCuota = (TextView) row.findViewById(C0386R.id.txtNroCuota);
                TextView txtDocumento = (TextView) row.findViewById(C0386R.id.txtDocumento);
                TextView txtFechaEmision = (TextView) row.findViewById(C0386R.id.txtFechaEmision);
                TextView txtFechaVencimiento = (TextView) row.findViewById(C0386R.id.txtFechaVencimiento);
                TextView txtMoneda = (TextView) row.findViewById(C0386R.id.txtMoneda);
                TextView txtImporte = (TextView) row.findViewById(C0386R.id.txtImporte);
                TextView txtDescuento = (TextView) row.findViewById(C0386R.id.txtDescuento);
                TextView txtImpuesto = (TextView) row.findViewById(C0386R.id.txtImpuesto);
                TextView txtImporteCancelado = (TextView) row.findViewById(C0386R.id.txtImporteCancelado);
                TextView txtSaldo = (TextView) row.findViewById(C0386R.id.txtSaldo);
                TextView txtMora = (TextView) row.findViewById(C0386R.id.txtMora);
                TextView txtTotal = (TextView) row.findViewById(C0386R.id.txtTotal);
                TextView txtDocumentoTitle = (TextView) row.findViewById(C0386R.id.txtDocumentoTitle);
                TextView txtFechaEmisionTitle = (TextView) row.findViewById(C0386R.id.txtFechaEmisionTitle);
                TextView txtFechaVencimientoTitle = (TextView) row.findViewById(C0386R.id.txtFechaVencimientoTitle);
                TextView txtMonedaTitle = (TextView) row.findViewById(C0386R.id.txtMonedaTitle);
                TextView txtImporteTitle = (TextView) row.findViewById(C0386R.id.txtImporteTitle);
                TextView txtDescuentoTitle = (TextView) row.findViewById(C0386R.id.txtDescuentoTitle);
                TextView txtImpuestoTitle = (TextView) row.findViewById(C0386R.id.txtImpuestoTitle);
                TextView txtImporteCanceladoTitle = (TextView) row.findViewById(C0386R.id.txtImporteCanceladoTitle);
                TextView txtSaldoTitle = (TextView) row.findViewById(C0386R.id.txtSaldoTitle);
                TextView txtMoraTitle = (TextView) row.findViewById(C0386R.id.txtMoraTitle);
                TextView txtTotalTitle = (TextView) row.findViewById(C0386R.id.txtTotalTitle);
                Typeface typefaceRegular = Typeface.createFromAsset(PagosActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
                txtNroCuota.setTypeface(Typeface.createFromAsset(PagosActivity.this.getAssets(), "fonts/Zizou Slab-Medium.otf"));
                txtDocumento.setTypeface(typefaceRegular);
                txtFechaEmision.setTypeface(typefaceRegular);
                txtFechaVencimiento.setTypeface(typefaceRegular);
                txtMoneda.setTypeface(typefaceRegular);
                txtImporte.setTypeface(typefaceRegular);
                txtDescuento.setTypeface(typefaceRegular);
                txtImpuesto.setTypeface(typefaceRegular);
                txtImporteCancelado.setTypeface(typefaceRegular);
                txtSaldo.setTypeface(typefaceRegular);
                txtMora.setTypeface(typefaceRegular);
                txtTotal.setTypeface(typefaceRegular);
                txtDocumentoTitle.setTypeface(typefaceRegular);
                txtFechaEmisionTitle.setTypeface(typefaceRegular);
                txtFechaVencimientoTitle.setTypeface(typefaceRegular);
                txtMonedaTitle.setTypeface(typefaceRegular);
                txtImporteTitle.setTypeface(typefaceRegular);
                txtDescuentoTitle.setTypeface(typefaceRegular);
                txtImpuestoTitle.setTypeface(typefaceRegular);
                txtImporteCanceladoTitle.setTypeface(typefaceRegular);
                txtSaldoTitle.setTypeface(typefaceRegular);
                txtMoraTitle.setTypeface(typefaceRegular);
                txtTotalTitle.setTypeface(typefaceRegular);
                txtNroCuota.setText("Cuota " + jsonPago.getString("NroCuota"));
                txtDocumento.setText(jsonPago.getString("NroDocumento").trim());
                String anio = jsonPago.getString("FecEmision").substring(0, 4);
                String mes = jsonPago.getString("FecEmision").substring(4, 6);
                txtFechaEmision.setText(new StringBuilder(String.valueOf(jsonPago.getString("FecEmision").substring(6))).append("/").append(mes).append("/").append(anio).toString());
                anio = jsonPago.getString("FecVencimiento").substring(0, 4);
                mes = jsonPago.getString("FecVencimiento").substring(4, 6);
                txtFechaVencimiento.setText(new StringBuilder(String.valueOf(jsonPago.getString("FecVencimiento").substring(6))).append("/").append(mes).append("/").append(anio).toString());
                String moneda = jsonPago.getString("Moneda");
                if (moneda.equalsIgnoreCase("S")) {
                    txtMoneda.setText("Nuevos Soles");
                } else {
                    txtMoneda.setText(moneda);
                }
                txtImporte.setText(jsonPago.getString("Importe"));
                txtDescuento.setText(jsonPago.getString("Descuento"));
                txtImpuesto.setText(jsonPago.getString("Impuesto"));
                txtImporteCancelado.setText(jsonPago.getString("ImporteCancelado"));
                txtSaldo.setText(jsonPago.getString("Saldo"));
                txtMora.setText(jsonPago.getString("Mora"));
                txtTotal.setText(jsonPago.getString("Total"));
            } catch (Exception e) {
                PagosActivity.this.prepareHandleException(e);
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
                this.alertDialog = MessageUtils.buildLoadingAlert(PagosActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                PagosActivity.this.prepareHandleException(e);
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
                    PagosActivity.this.prepareHandleException(null);
                } else if (result.equalsIgnoreCase("SocketTimeoutException") || result.equalsIgnoreCase("ConnectTimeoutException") || result.equalsIgnoreCase(StringUtils.EMPTY)) {
                    MessageUtils.showErrorAlert(PagosActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    String codError = jsonObject.getString("CodError");
                    if (codError.equalsIgnoreCase("00000")) {
                        PagosActivity.this.txtMensajePagos.setVisibility(8);
                        JSONArray jsonArray = jsonObject.getJSONArray("PagosPendientes");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            PagosActivity.this.pagosList.add(jsonArray.getJSONObject(i));
                        }
                        PagosActivity.this.pagosAdapter.notifyDataSetChanged();
                    } else if (codError.equalsIgnoreCase("00003")) {
                        PagosActivity.this.loggout(true);
                    } else {
                        String msgError = jsonObject.getString("MsgError");
                        if (codError.equalsIgnoreCase("00041")) {
                            PagosActivity.this.txtMensajePagos.setText(msgError);
                            PagosActivity.this.txtMensajePagos.setVisibility(0);
                            return;
                        }
                        MessageUtils.showErrorAlert(PagosActivity.this, msgError);
                    }
                }
            } catch (Exception e) {
                PagosActivity.this.prepareHandleException(e);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04161();
            registerReceiver(this.receiver, intentFilter);
            this.pagosList = new ArrayList();
            ListView listView = (ListView) findViewById(C0386R.id.lsPagos);
            this.pagosAdapter = new PagosAdapter(this, C0386R.layout.pagosrow, this.pagosList);
            listView.setAdapter(this.pagosAdapter);
            this.txtMensajePagos = (TextView) findViewById(C0386R.id.txtMensajePagos);
            this.txtMensajePagos.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf"));
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.pagosList.clear();
            if (NetworkUtils.isConnected(this)) {
                SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String codAlumno;
                String url;
                if (session.getString("TipoUsuario", StringUtils.EMPTY).equalsIgnoreCase("PADRE")) {
                    String codigo = session.getString("Codigo", StringUtils.EMPTY);
                    codAlumno = session.getString("CodAlumno", StringUtils.EMPTY);
                    url = Environment.BASE_URL + "/PagoPendientePadre/?Codigo=" + codigo + "&CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
                    new RESTTask().execute(new String[]{url});
                    return;
                }
                codAlumno = session.getString("Codigo", StringUtils.EMPTY);
                url = Environment.BASE_URL + "/PagoPendiente/?CodAlumno=" + codAlumno + "&Token=" + session.getString("Token", StringUtils.EMPTY);
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
