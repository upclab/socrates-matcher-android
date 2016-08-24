package pe.edu.upc.mobile.UPCMovil;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
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
import java.util.List;
import org.apache.http.conn.ConnectTimeoutException;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.Entities.Evento;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;
import pe.edu.upc.mobile.XMLParser.XMLParser;

public class EventosActivity extends MenuActivity {
    private EventosAdapter EventosAdapter;
    private ArrayList<Evento> EventosList;
    private BroadcastReceiver receiver;
    private TextView txtMensaje;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.EventosActivity.1 */
    class C04031 extends BroadcastReceiver {
        C04031() {
        }

        public void onReceive(Context context, Intent intent) {
            EventosActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.EventosActivity.2 */
    class C04042 implements OnItemClickListener {
        C04042() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            try {
                EventosActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((Evento) EventosActivity.this.EventosList.get(position)).getLink())));
            } catch (Exception e) {
                EventosActivity.this.prepareHandleException(e);
            }
        }
    }

    private class EventosAdapter extends ArrayAdapter<Evento> {
        public EventosAdapter(Context context, int textViewResourceId, ArrayList<Evento> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                try {
                    v = ((LayoutInflater) EventosActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.eventosrow, null);
                } catch (Exception e) {
                    EventosActivity.this.prepareHandleException(e);
                }
            }
            LinearLayout loPagosRow = (LinearLayout) v.findViewById(C0386R.id.eventosRow);
            if (position % 2 == 0) {
                loPagosRow.setBackgroundColor(Color.parseColor("#EDEDED"));
            } else {
                loPagosRow.setBackgroundColor(Color.parseColor("#DEDEDE"));
            }
            Evento Evento = (Evento) EventosActivity.this.EventosList.get(position);
            Typeface typeface = Typeface.createFromAsset(EventosActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
            TextView txtViewTitle = (TextView) v.findViewById(C0386R.id.txtViewTitle);
            txtViewTitle.setTypeface(typeface);
            TextView txtViewPubDate = (TextView) v.findViewById(C0386R.id.txtViewPubDate);
            txtViewPubDate.setTypeface(typeface);
            if (Evento != null) {
                txtViewTitle.setText(Evento.getTitle());
                txtViewPubDate.setText(Evento.getPubDate());
            }
            return v;
        }
    }

    private class RESTTask extends AsyncTask<String, Void, List<Evento>> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(EventosActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                EventosActivity.this.prepareHandleException(e);
            }
        }

        protected List<Evento> doInBackground(String... urls) {
            Evento evento;
            ArrayList<Evento> response = null;
            for (String url : urls) {
                try {
                    response = (ArrayList) new XMLParser().parseEventos(RESTClient.connectAndReturnInputStream(url));
                } catch (SocketTimeoutException e) {
                    response = new ArrayList();
                    evento = new Evento();
                    evento.setTitle("SocketTimeoutException");
                    response.add(evento);
                } catch (ConnectTimeoutException e2) {
                    response = new ArrayList();
                    evento = new Evento();
                    evento.setTitle("ConnectTimeoutException");
                    response.add(evento);
                } catch (Exception e3) {
                    response = new ArrayList();
                    evento = new Evento();
                    evento.setTitle("Exception");
                    response.add(evento);
                }
            }
            return response;
        }

        protected void onPostExecute(List<Evento> result) {
            try {
                this.alertDialog.dismiss();
                if (result == null) {
                    MessageUtils.showErrorAlert(EventosActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else if (result.size() > 0) {
                    Evento error = (Evento) result.get(0);
                    if (error.getTitle().equalsIgnoreCase("Exception")) {
                        EventosActivity.this.prepareHandleException(null);
                    } else if (error.getTitle().equalsIgnoreCase("SocketTimeoutException") || error.getTitle().equalsIgnoreCase("ConnectTimeoutException")) {
                        MessageUtils.showErrorAlert(EventosActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                    } else {
                        EventosActivity.this.EventosList.addAll(result);
                        EventosActivity.this.EventosAdapter.notifyDataSetChanged();
                        EventosActivity.this.txtMensaje.setVisibility(8);
                    }
                } else {
                    EventosActivity.this.txtMensaje.setVisibility(0);
                }
            } catch (Exception e) {
                EventosActivity.this.prepareHandleException(e);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04031();
            registerReceiver(this.receiver, intentFilter);
            this.EventosList = new ArrayList();
            this.EventosAdapter = new EventosAdapter(this, C0386R.layout.eventosrow, this.EventosList);
            ListView listview = (ListView) findViewById(C0386R.id.lsEventos);
            listview.setAdapter(this.EventosAdapter);
            listview.setOnItemClickListener(new C04042());
            this.txtMensaje = (TextView) findViewById(C0386R.id.txtMensaje);
            this.txtMensaje.setText("No se encontraron eventos.");
            this.txtMensaje.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf"));
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.EventosList.clear();
            if (NetworkUtils.isConnected(this)) {
                new RESTTask().execute(new String[]{"http://www.upc.edu.pe/eventos/rss"});
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
