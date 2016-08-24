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
import pe.edu.upc.mobile.Entities.Noticia;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;
import pe.edu.upc.mobile.XMLParser.XMLParser;

public class NoticiasActivity extends MenuActivity {
    private NoticiasAdapter noticiasAdapter;
    private ArrayList<Noticia> noticiasList;
    private BroadcastReceiver receiver;
    private TextView txtMensaje;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.NoticiasActivity.1 */
    class C04141 extends BroadcastReceiver {
        C04141() {
        }

        public void onReceive(Context context, Intent intent) {
            NoticiasActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.NoticiasActivity.2 */
    class C04152 implements OnItemClickListener {
        C04152() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            try {
                NoticiasActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((Noticia) NoticiasActivity.this.noticiasList.get(position)).getLink())));
            } catch (Exception e) {
                NoticiasActivity.this.prepareHandleException(e);
            }
        }
    }

    private class NoticiasAdapter extends ArrayAdapter<Noticia> {
        public NoticiasAdapter(Context context, int textViewResourceId, ArrayList<Noticia> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                try {
                    v = ((LayoutInflater) NoticiasActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.noticiasrow, null);
                } catch (Exception e) {
                    NoticiasActivity.this.prepareHandleException(e);
                }
            }
            LinearLayout loPagosRow = (LinearLayout) v.findViewById(C0386R.id.noticiasRow);
            if (position % 2 == 0) {
                loPagosRow.setBackgroundColor(Color.parseColor("#EDEDED"));
            } else {
                loPagosRow.setBackgroundColor(Color.parseColor("#DEDEDE"));
            }
            Noticia noticia = (Noticia) NoticiasActivity.this.noticiasList.get(position);
            Typeface typeface = Typeface.createFromAsset(NoticiasActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf");
            TextView txtViewTitle = (TextView) v.findViewById(C0386R.id.txtViewTitle);
            txtViewTitle.setTypeface(typeface);
            TextView txtViewPubDate = (TextView) v.findViewById(C0386R.id.txtViewPubDate);
            txtViewPubDate.setTypeface(typeface);
            if (noticia != null) {
                txtViewTitle.setText(noticia.getTitle());
                txtViewPubDate.setText(noticia.getPubDate());
            }
            return v;
        }
    }

    private class RESTTask extends AsyncTask<String, Void, List<Noticia>> {
        AlertDialog alertDialog;

        private RESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(NoticiasActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                NoticiasActivity.this.prepareHandleException(e);
            }
        }

        protected List<Noticia> doInBackground(String... urls) {
            Noticia noticia;
            ArrayList<Noticia> response = null;
            for (String url : urls) {
                try {
                    response = (ArrayList) new XMLParser().parseNoticias(RESTClient.connectAndReturnInputStream(url));
                } catch (SocketTimeoutException e) {
                    response = new ArrayList();
                    noticia = new Noticia();
                    noticia.setTitle("SocketTimeoutException");
                    response.add(noticia);
                } catch (ConnectTimeoutException e2) {
                    response = new ArrayList();
                    noticia = new Noticia();
                    noticia.setTitle("ConnectTimeoutException");
                    response.add(noticia);
                } catch (Exception e3) {
                    response = new ArrayList();
                    noticia = new Noticia();
                    noticia.setTitle("Exception");
                    response.add(noticia);
                }
            }
            return response;
        }

        protected void onPostExecute(List<Noticia> result) {
            try {
                this.alertDialog.dismiss();
                if (result == null) {
                    MessageUtils.showErrorAlert(NoticiasActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                } else if (result.size() > 0) {
                    Noticia error = (Noticia) result.get(0);
                    if (error.getTitle().equalsIgnoreCase("Exception")) {
                        NoticiasActivity.this.prepareHandleException(null);
                    } else if (error.getTitle().equalsIgnoreCase("SocketTimeoutException") || error.getTitle().equalsIgnoreCase("ConnectTimeoutException")) {
                        MessageUtils.showErrorAlert(NoticiasActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
                    } else {
                        NoticiasActivity.this.noticiasList.addAll(result);
                        NoticiasActivity.this.noticiasAdapter.notifyDataSetChanged();
                        NoticiasActivity.this.txtMensaje.setVisibility(8);
                    }
                } else {
                    NoticiasActivity.this.txtMensaje.setVisibility(0);
                }
            } catch (Exception e) {
                NoticiasActivity.this.prepareHandleException(e);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            this.receiver = new C04141();
            registerReceiver(this.receiver, intentFilter);
            this.noticiasList = new ArrayList();
            this.noticiasAdapter = new NoticiasAdapter(this, C0386R.layout.noticiasrow, this.noticiasList);
            ListView listview = (ListView) findViewById(C0386R.id.lsNoticias);
            listview.setAdapter(this.noticiasAdapter);
            listview.setOnItemClickListener(new C04152());
            this.txtMensaje = (TextView) findViewById(C0386R.id.txtMensaje);
            this.txtMensaje.setText("No se encontraron noticias.");
            this.txtMensaje.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf"));
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            EasyTracker.getInstance(this).activityStart(this);
            this.noticiasList.clear();
            if (NetworkUtils.isConnected(this)) {
                new RESTTask().execute(new String[]{"http://www.upc.edu.pe/noticias/rss"});
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
