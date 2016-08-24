package pe.edu.upc.mobile.UPCMovil;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.plus.PlusShare;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Hashtable;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.RESTClient.Environment;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.UPCMovil.SelectorDialogFragment.SelectOptionDialogListener;
import pe.edu.upc.mobile.Utilities.CryptoUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class IntranetActivity extends MenuActivity implements SelectOptionDialogListener {
    private static int MAX_LENGTH;
    private CheckBox ckbRecordarContrasena;
    private EditText edtTextPassword;
    private EditText edtTextUsuario;
    private JSONArray hijosJSONArray;
    private BroadcastReceiver receiver;
    private TextView txtInstructions;
    private TextView txtRemember;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.IntranetActivity.1 */
    class C04071 extends BroadcastReceiver {
        C04071() {
        }

        public void onReceive(Context context, Intent intent) {
            IntranetActivity.this.finish();
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.IntranetActivity.2 */
    class C04082 implements OnCheckedChangeListener {
        C04082() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences session = IntranetActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0);
            boolean recordarContrasena = session.getBoolean("RecordarContrasena", false);
            if (!isChecked && recordarContrasena) {
                Editor editor = session.edit();
                editor.putBoolean("RecordarContrasena", false);
                editor.putString("Contrasena", StringUtils.EMPTY);
                editor.putString("Usuario", StringUtils.EMPTY);
                IntranetActivity.this.edtTextUsuario.setEnabled(true);
                IntranetActivity.this.edtTextUsuario.setText(StringUtils.EMPTY);
                IntranetActivity.this.edtTextPassword.setEnabled(true);
                IntranetActivity.this.edtTextPassword.setText(StringUtils.EMPTY);
                editor.commit();
            }
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.IntranetActivity.3 */
    class C04093 implements InputFilter {
        C04093() {
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isLetterOrDigit(source.charAt(i))) {
                    return StringUtils.EMPTY;
                }
            }
            return null;
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.IntranetActivity.4 */
    class C04104 implements OnEditorActionListener {
        C04104() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (event == null) {
                IntranetActivity.this.onClickEntrarButton(null);
                return true;
            } else if (event.isShiftPressed()) {
                return false;
            } else {
                IntranetActivity.this.onClickEntrarButton(null);
                return true;
            }
        }
    }

    private class HijosRESTTask extends AsyncTask<Hashtable<String, Object>, Void, String> {
        AlertDialog alertDialog;

        private HijosRESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(IntranetActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                IntranetActivity.this.prepareHandleException(e);
            }
        }

        protected String doInBackground(Hashtable<String, Object>... hashtables) {
            String response = StringUtils.EMPTY;
            for (Hashtable<String, Object> hashtable : hashtables) {
                try {
                    response = RESTClient.connectAndReturnResponse((String) hashtable.get(PlusShare.KEY_CALL_TO_ACTION_URL));
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
                    IntranetActivity.this.prepareHandleException(null);
                    return;
                }
                if (!result.equalsIgnoreCase("SocketTimeoutException")) {
                    if (!result.equalsIgnoreCase("ConnectTimeoutException")) {
                        if (!result.equalsIgnoreCase(StringUtils.EMPTY)) {
                            JSONObject aJSONObject = new JSONObject(result);
                            String aCodError = aJSONObject.getString("CodError");
                            if (aCodError.equalsIgnoreCase("00000")) {
                                IntranetActivity.this.hijosJSONArray = aJSONObject.getJSONArray("hijos");
                                ArrayList<String> options = new ArrayList();
                                int count = IntranetActivity.this.hijosJSONArray.length();
                                for (int i = 0; i < count; i++) {
                                    JSONObject hijo = IntranetActivity.this.hijosJSONArray.getJSONObject(i);
                                    options.add(hijo.getString("nombres") + " " + hijo.getString("apellidos"));
                                }
                                SelectorDialogFragment.newInstance(0, options).show(IntranetActivity.this.getSupportFragmentManager(), "fragment_selector");
                                return;
                            }
                            if (aCodError.equalsIgnoreCase("00001")) {
                                IntranetActivity.this.edtTextUsuario.setText(StringUtils.EMPTY);
                                IntranetActivity.this.edtTextPassword.setText(StringUtils.EMPTY);
                                if (IntranetActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0).getBoolean("RecordarContrasena", false)) {
                                    IntranetActivity.this.ckbRecordarContrasena.setChecked(false);
                                }
                                IntranetActivity.this.edtTextUsuario.requestFocus();
                            }
                            MessageUtils.showErrorAlert(IntranetActivity.this, aJSONObject.getString("MsgError"));
                            return;
                        }
                    }
                }
                MessageUtils.showErrorAlert(IntranetActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
            } catch (JSONException e) {
                IntranetActivity.this.prepareHandleException(e);
            }
        }
    }

    private class LoginRESTTask extends AsyncTask<Hashtable<String, Object>, Void, String> {
        AlertDialog alertDialog;

        private LoginRESTTask() {
            this.alertDialog = null;
        }

        protected void onPreExecute() {
            try {
                this.alertDialog = MessageUtils.buildLoadingAlert(IntranetActivity.this);
                this.alertDialog.show();
            } catch (Exception e) {
                IntranetActivity.this.prepareHandleException(e);
            }
        }

        protected String doInBackground(Hashtable<String, Object>... hashtables) {
            String response = StringUtils.EMPTY;
            for (Hashtable<String, Object> hashtable : hashtables) {
                try {
                    response = RESTClient.connectAndReturnResponse((String) hashtable.get(PlusShare.KEY_CALL_TO_ACTION_URL), (StringEntity) hashtable.get("se"));
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
                    IntranetActivity.this.prepareHandleException(null);
                    return;
                }
                if (!result.equalsIgnoreCase("SocketTimeoutException")) {
                    if (!result.equalsIgnoreCase("ConnectTimeoutException")) {
                        if (!result.equalsIgnoreCase(StringUtils.EMPTY)) {
                            JSONObject aJSONObject = new JSONObject(result);
                            String aCodError = aJSONObject.getString("CodError");
                            if (aCodError.equalsIgnoreCase("00000")) {
                                SharedPreferences session = IntranetActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                                String userType = aJSONObject.getString("TipoUser");
                                String token = aJSONObject.getString("Token");
                                Editor editor = session.edit();
                                String str = "Nombres";
                                editor.putString(r19, aJSONObject.getString("Nombres"));
                                str = "Apellidos";
                                editor.putString(r19, aJSONObject.getString("Apellidos"));
                                editor.putString("Token", token);
                                editor.putString("TipoUsuario", userType);
                                if (Environment.DEBUGGING.booleanValue()) {
                                    Log.d("Token", token);
                                }
                                str = "Codigo";
                                editor.putString(r19, aJSONObject.getString("Codigo"));
                                String contrasenaGuardada = session.getString("Contrasena", StringUtils.EMPTY);
                                String usuarioGuardado = session.getString("Usuario", StringUtils.EMPTY);
                                if (IntranetActivity.this.ckbRecordarContrasena.isChecked()) {
                                    if (contrasenaGuardada.equalsIgnoreCase(StringUtils.EMPTY) && usuarioGuardado.equalsIgnoreCase(StringUtils.EMPTY)) {
                                        String encryptedPassword = StringUtils.EMPTY;
                                        try {
                                            encryptedPassword = CryptoUtils.encryptPassword(IntranetActivity.this.edtTextPassword.getText().toString());
                                        } catch (Exception e) {
                                            IntranetActivity.this.prepareHandleException(e);
                                        }
                                        editor.putString("Contrasena", encryptedPassword);
                                        editor.putString("Usuario", IntranetActivity.this.edtTextUsuario.getText().toString());
                                        editor.putBoolean("RecordarContrasena", true);
                                    }
                                }
                                editor.commit();
                                if (userType.equalsIgnoreCase("PADRE")) {
                                    String url = Environment.BASE_URL + "/ListadoHijos/?Codigo=" + IntranetActivity.this.edtTextUsuario.getText().toString() + "&Token=" + token;
                                    new Hashtable().put(PlusShare.KEY_CALL_TO_ACTION_URL, url);
                                    new HijosRESTTask(null).execute(new Hashtable[]{parameters});
                                    return;
                                }
                                Intent broadcastIntent = new Intent();
                                broadcastIntent.setAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
                                IntranetActivity.this.sendBroadcast(broadcastIntent);
                                Intent aIntent = new Intent(IntranetActivity.this, HorarioActivity.class);
                                aIntent.putExtra("OptionNumber", 2);
                                aIntent.putExtra("OptionString", "HORARIO");
                                aIntent.putExtra("LoggedIn", true);
                                IntranetActivity.this.startActivity(aIntent);
                                return;
                            }
                            if (aCodError.equalsIgnoreCase("00001")) {
                                IntranetActivity.this.edtTextUsuario.setText(StringUtils.EMPTY);
                                IntranetActivity.this.edtTextPassword.setText(StringUtils.EMPTY);
                                if (IntranetActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0).getBoolean("RecordarContrasena", false)) {
                                    IntranetActivity.this.ckbRecordarContrasena.setChecked(false);
                                }
                                IntranetActivity.this.edtTextUsuario.requestFocus();
                            }
                            String aMsgError = aJSONObject.getString("MsgError");
                            MessageUtils.showErrorAlert(IntranetActivity.this, aMsgError);
                            return;
                        }
                    }
                }
                MessageUtils.showErrorAlert(IntranetActivity.this, "Se ha superado el tiempo m\u00e1ximo de conexi\u00f3n al servidor.");
            } catch (JSONException e2) {
                IntranetActivity.this.prepareHandleException(e2);
            }
        }
    }

    public IntranetActivity() {
        this.hijosJSONArray = null;
    }

    static {
        MAX_LENGTH = 20;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("pe.edu.upc.UPCMovil.ACTION_SUCCESS_LOGIN");
            this.receiver = new C04071();
            registerReceiver(this.receiver, intentFilter);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("pe.edu.upc.UPCMovil.ACTION_FINISH_SPLASH");
            sendBroadcast(broadcastIntent);
            this.ckbRecordarContrasena = (CheckBox) findViewById(C0386R.id.ckbRecordarContrasena);
            this.ckbRecordarContrasena.setOnCheckedChangeListener(new C04082());
            Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Regular.otf");
            this.edtTextUsuario = (EditText) findViewById(C0386R.id.edtTextUsuario);
            this.edtTextUsuario.setTypeface(typefaceRegular);
            this.edtTextUsuario.setTextSize(15.0f);
            InputFilter characterFilter = new C04093();
            InputFilter leghtFilter = new LengthFilter(MAX_LENGTH);
            InputFilter[] FilterArrayPassword = new InputFilter[]{leghtFilter};
            this.edtTextUsuario.setFilters(new InputFilter[]{characterFilter, leghtFilter});
            this.edtTextPassword = (EditText) findViewById(C0386R.id.edtTextPassword);
            this.edtTextPassword.setTypeface(typefaceRegular);
            this.edtTextPassword.setTextSize(15.0f);
            this.edtTextPassword.setOnEditorActionListener(new C04104());
            this.edtTextPassword.setFilters(FilterArrayPassword);
            SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
            if (session.getBoolean("RecordarContrasena", false)) {
                String usuarioGuardado = session.getString("Usuario", StringUtils.EMPTY);
                if (!(session.getString("Contrasena", StringUtils.EMPTY).equalsIgnoreCase(StringUtils.EMPTY) || usuarioGuardado.equalsIgnoreCase(StringUtils.EMPTY))) {
                    this.ckbRecordarContrasena.setChecked(true);
                    this.edtTextUsuario.setText(usuarioGuardado);
                    this.edtTextUsuario.setEnabled(false);
                    this.edtTextPassword.setText("****************");
                    this.edtTextPassword.setEnabled(false);
                }
            }
            this.txtInstructions = (TextView) findViewById(C0386R.id.txtInstructions);
            this.txtRemember = (TextView) findViewById(C0386R.id.txtRemember);
            this.txtInstructions.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Zizou Slab-Medium.otf"));
            this.txtRemember.setTypeface(typefaceRegular);
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void onResume() {
        try {
            super.onResume();
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

    public void onClickEntrarButton(View aButton) {
        try {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.edtTextPassword.getWindowToken(), 0);
            if (NetworkUtils.isConnected(this)) {
                int comparePassword = this.edtTextPassword.getText().toString().trim().compareToIgnoreCase(StringUtils.EMPTY);
                int compareUsuario = this.edtTextUsuario.getText().toString().trim().compareToIgnoreCase(StringUtils.EMPTY);
                if (comparePassword == 0 || compareUsuario == 0) {
                    MessageUtils.showErrorAlert(this, "Ingrese todos los campos necesarios.");
                    return;
                } else if (this.edtTextPassword.getText().toString().length() <= MAX_LENGTH && this.edtTextUsuario.getText().toString().length() <= MAX_LENGTH) {
                    String encryptedPassword = StringUtils.EMPTY;
                    SharedPreferences session = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                    if (session.getBoolean("RecordarContrasena", false) && this.ckbRecordarContrasena.isChecked()) {
                        String contrasenaGuardada = session.getString("Contrasena", StringUtils.EMPTY);
                        if (contrasenaGuardada.equalsIgnoreCase(StringUtils.EMPTY)) {
                            encryptedPassword = CryptoUtils.encryptPassword(this.edtTextPassword.getText().toString());
                        } else {
                            encryptedPassword = contrasenaGuardada;
                        }
                    } else {
                        encryptedPassword = CryptoUtils.encryptPassword(this.edtTextPassword.getText().toString());
                    }
                    encryptedPassword = encryptedPassword.replace("\n", StringUtils.EMPTY);
                    String url = Environment.BASE_URL + "/Autenticarp2";
                    StringEntity se = new StringEntity(new JSONStringer().object().key("Usuario").value(this.edtTextUsuario.getText().toString()).key("Contrasena").value(encryptedPassword).key("Plataforma").value("A").endObject().toString());
                    Hashtable<String, Object> parameters = new Hashtable();
                    parameters.put(PlusShare.KEY_CALL_TO_ACTION_URL, url);
                    parameters.put("se", se);
                    new LoginRESTTask().execute(new Hashtable[]{parameters});
                    return;
                } else {
                    return;
                }
            }
            MessageUtils.showErrorAlert(this, "Verifique que su dispositivo tiene conexi\u00f3n a internet.");
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    public void onFinishSelectorDialog(String option, int type) {
        try {
            String codAlumno = StringUtils.EMPTY;
            int count = this.hijosJSONArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject hijo = this.hijosJSONArray.getJSONObject(i);
                if ((hijo.getString("nombres") + " " + hijo.getString("apellidos")).equalsIgnoreCase(option)) {
                    codAlumno = hijo.getString("codigo");
                }
            }
            Editor editor = getSharedPreferences("pe.edu.upc.UPCMovil", 0).edit();
            editor.putString("CodAlumno", codAlumno);
            editor.commit();
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
            sendBroadcast(broadcastIntent);
            Intent aIntent = new Intent(this, HorarioActivity.class);
            aIntent.putExtra("OptionNumber", 2);
            aIntent.putExtra("OptionString", "HORARIO");
            aIntent.putExtra("LoggedIn", true);
            startActivity(aIntent);
        } catch (JSONException e) {
            prepareHandleException(e);
        }
    }
}
