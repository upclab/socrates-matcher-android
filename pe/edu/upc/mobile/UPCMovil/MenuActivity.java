package pe.edu.upc.mobile.UPCMovil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.Entities.Session;
import pe.edu.upc.mobile.RESTClient.Environment;
import pe.edu.upc.mobile.RESTClient.RESTClient;
import pe.edu.upc.mobile.Utilities.ExceptionUtils;
import pe.edu.upc.mobile.Utilities.MenuUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;
import pe.edu.upc.mobile.Utilities.MyHorizontalScrollView;
import pe.edu.upc.mobile.Utilities.MyHorizontalScrollView.SizeCallback;
import pe.edu.upc.mobile.Utilities.NetworkUtils;

public class MenuActivity extends FragmentActivity {
    View app;
    ImageView btnSlide;
    int btnWidth;
    Handler handler;
    ClickListenerForScrolling listener;
    boolean loggedIn;
    String[] loggedInOptionsAlumnos;
    String[] loggedInOptionsPadres;
    String[] loggedInOptionsProfesores;
    String[] loggedOutOptions;
    View menu;
    MenuAdapter menuAdapter;
    ArrayList<String> menuList;
    int optionNumber;
    MyHorizontalScrollView scrollView;
    boolean tokenExpiration;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.MenuActivity.1 */
    class C04111 implements OnItemClickListener {
        C04111() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (position == MenuActivity.this.optionNumber) {
                boolean z;
                int menuWidth = MenuActivity.this.menu.getMeasuredWidth();
                MenuActivity.this.menu.setVisibility(0);
                MenuActivity.this.scrollView.smoothScrollTo(menuWidth, 0);
                ClickListenerForScrolling clickListenerForScrolling = MenuActivity.this.listener;
                if (MenuActivity.this.listener.menuOut) {
                    z = false;
                } else {
                    z = true;
                }
                clickListenerForScrolling.menuOut = z;
                return;
            }
            String userType = MenuActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0).getString("TipoUsuario", StringUtils.EMPTY);
            String[] split = MenuUtils.actionForMenu(MenuActivity.this.loggedIn, position, userType).split(" ");
            if (!split[1].equalsIgnoreCase("nothing")) {
                if (split[1].equalsIgnoreCase("browser")) {
                    MenuActivity.this.openBrowser(split[2]);
                } else if (split[1].equalsIgnoreCase("maps")) {
                    MenuActivity.this.openMap();
                } else if (split[1].equalsIgnoreCase("ar")) {
                    MenuActivity.this.openAR();
                } else if (split[1].equalsIgnoreCase("loggout")) {
                    MenuActivity.this.loggout(false);
                } else {
                    Intent aIntent = MenuUtils.intentWith(MenuActivity.this, MenuActivity.this.loggedIn, position, userType);
                    aIntent.putExtra("OptionNumber", position);
                    aIntent.putExtra("OptionString", (String) MenuActivity.this.menuList.get(position));
                    aIntent.putExtra("LoggedIn", MenuActivity.this.loggedIn);
                    aIntent.putExtra("TokenExpiration", false);
                    MenuActivity.this.startActivity(aIntent);
                }
            }
        }
    }

    public class ClickListenerForScrolling implements OnClickListener {
        View menu;
        boolean menuOut;
        HorizontalScrollView scrollView;

        public ClickListenerForScrolling(HorizontalScrollView scrollView, View menu) {
            this.menuOut = false;
            this.scrollView = scrollView;
            this.menu = menu;
        }

        public void onClick(View v) {
            boolean z = false;
            int menuWidth = this.menu.getMeasuredWidth();
            this.menu.setVisibility(0);
            if (this.menuOut) {
                this.scrollView.smoothScrollTo(menuWidth, 0);
            } else {
                this.scrollView.smoothScrollTo(0, 0);
            }
            if (!this.menuOut) {
                z = true;
            }
            this.menuOut = z;
        }
    }

    private class MenuAdapter extends ArrayAdapter<String> {
        public MenuAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = ((LayoutInflater) MenuActivity.this.getSystemService("layout_inflater")).inflate(C0386R.layout.menurow, null);
            }
            RelativeLayout header = (RelativeLayout) v.findViewById(C0386R.id.header);
            LinearLayout username = (LinearLayout) v.findViewById(C0386R.id.username);
            LinearLayout row = (LinearLayout) v.findViewById(C0386R.id.row);
            TextView txtViewTitle = (TextView) v.findViewById(C0386R.id.txtViewTitle);
            ImageView imgViewIcon = (ImageView) v.findViewById(C0386R.id.imgViewIcon);
            TextView txtViewUserName = (TextView) v.findViewById(C0386R.id.txtViewUserName);
            if (position == 0) {
                header.setVisibility(0);
                row.setVisibility(8);
                username.setVisibility(8);
            } else {
                SharedPreferences session = MenuActivity.this.getSharedPreferences("pe.edu.upc.UPCMovil", 0);
                String userType = session.getString("TipoUsuario", StringUtils.EMPTY);
                if (!MenuActivity.this.loggedIn) {
                    header.setVisibility(8);
                    row.setVisibility(0);
                    username.setVisibility(8);
                    txtViewTitle.setText(MenuActivity.this.loggedOutOptions[position]);
                    txtViewTitle.setTypeface(Typeface.createFromAsset(MenuActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf"));
                    imgViewIcon.setImageResource(MenuUtils.resourceIdForMenu(MenuActivity.this.loggedIn, position, userType));
                } else if (position == 1) {
                    header.setVisibility(8);
                    row.setVisibility(8);
                    username.setVisibility(0);
                    String userName = session.getString("Nombres", StringUtils.EMPTY);
                    txtViewUserName.setText(new StringBuilder(String.valueOf(userName)).append("\n").append(session.getString("Apellidos", StringUtils.EMPTY)).toString());
                    txtViewUserName.setTypeface(Typeface.createFromAsset(MenuActivity.this.getAssets(), "fonts/Zizou Slab-Bold.otf"));
                } else {
                    header.setVisibility(8);
                    row.setVisibility(0);
                    username.setVisibility(8);
                    txtViewTitle.setText((CharSequence) MenuActivity.this.menuList.get(position));
                    txtViewTitle.setTypeface(Typeface.createFromAsset(MenuActivity.this.getAssets(), "fonts/Zizou Slab-Regular.otf"));
                    imgViewIcon.setImageResource(MenuUtils.resourceIdForMenu(MenuActivity.this.loggedIn, position, userType));
                }
            }
            return v;
        }
    }

    private static class RESTTask extends AsyncTask<String, Void, String> {
        private RESTTask() {
        }

        protected String doInBackground(String... urls) {
            String response = StringUtils.EMPTY;
            for (String url : urls) {
                try {
                    response = RESTClient.connectAndReturnResponse(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    static class SizeCallbackForMenu implements SizeCallback {
        View btnSlide;
        int btnWidth;

        public SizeCallbackForMenu(View btnSlide) {
            this.btnSlide = btnSlide;
        }

        public void onGlobalLayout() {
            this.btnWidth = this.btnSlide.getMeasuredWidth();
            System.out.println("btnWidth=" + this.btnWidth);
        }

        public void getViewSize(int idx, int w, int h, int[] dims) {
            dims[0] = w;
            dims[1] = h;
            if (idx == 0) {
                dims[0] = w - this.btnWidth;
            }
        }
    }

    public MenuActivity() {
        this.handler = new Handler();
        this.loggedOutOptions = new String[]{StringUtils.EMPTY, "Intranet", "Eventos", "Noticias", "Directorio Telef\u00f3nico", "Convenios"};
        this.loggedInOptionsAlumnos = new String[]{StringUtils.EMPTY, StringUtils.EMPTY, "Horario de Clases", "Inasistencias", "Pagos Pendientes", "Cursos", "Tr\u00e1mites", "Eventos", "Noticias", "Directorio Telef\u00f3nico", "Convenios", "Cub\u00edculos y Computadoras", "Salir del Sistema"};
        this.loggedInOptionsPadres = new String[]{StringUtils.EMPTY, StringUtils.EMPTY, "Horario de Clases", "Inasistencias", "Pagos Pendientes", "Notas Actuales", "Tr\u00e1mites", "Eventos", "Noticias", "Directorio Telef\u00f3nico", "Convenios", "Salir del Sistema"};
        this.loggedInOptionsProfesores = new String[]{StringUtils.EMPTY, StringUtils.EMPTY, "Horario de Clases", "Cursos y Alumnos", "Eventos", "Noticias", "Directorio Telef\u00f3nico", "Convenios", "Salir del Sistema"};
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle aBundle = getIntent().getExtras();
        LayoutInflater inflater = LayoutInflater.from(this);
        this.scrollView = (MyHorizontalScrollView) inflater.inflate(C0386R.layout.horz_scroll_with_list_menu, null);
        setContentView(this.scrollView);
        this.menu = inflater.inflate(C0386R.layout.horz_scroll_menu, null);
        String userType = getSharedPreferences("pe.edu.upc.UPCMovil", 0).getString("TipoUsuario", StringUtils.EMPTY);
        TextView txtViewTitle;
        if (aBundle != null) {
            this.optionNumber = aBundle.getInt("OptionNumber");
            this.loggedIn = aBundle.getBoolean("LoggedIn");
            this.tokenExpiration = aBundle.getBoolean("TokenExpiration");
            this.app = MenuUtils.viewWithInflater(inflater, this.loggedIn, this.optionNumber, userType);
            txtViewTitle = (TextView) this.app.findViewById(C0386R.id.txtViewTitle);
            txtViewTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SolanoGothicMVBProBd.otf"));
            String optionString = aBundle.getString("OptionString");
            if (optionString != null) {
                txtViewTitle.setText(optionString.toUpperCase(Locale.getDefault()));
            }
        } else {
            this.app = inflater.inflate(C0386R.layout.intranetlayout, null);
            txtViewTitle = (TextView) this.app.findViewById(C0386R.id.txtViewTitle);
            txtViewTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SolanoGothicMVBProBd.otf"));
            txtViewTitle.setText("INTRANET");
            this.optionNumber = 1;
            this.loggedIn = false;
        }
        ViewGroup tabBar = (ViewGroup) this.app.findViewById(C0386R.id.tabBar);
        ListView listView = (ListView) this.menu.findViewById(C0386R.id.list);
        if (!this.loggedIn) {
            this.menuList = new ArrayList(Arrays.asList(this.loggedOutOptions));
        } else if (userType.equalsIgnoreCase("PADRE")) {
            this.menuList = new ArrayList(Arrays.asList(this.loggedInOptionsPadres));
        } else if (userType.equalsIgnoreCase("PROFESOR")) {
            this.menuList = new ArrayList(Arrays.asList(this.loggedInOptionsProfesores));
        } else {
            this.menuList = new ArrayList(Arrays.asList(this.loggedInOptionsAlumnos));
        }
        this.menuAdapter = new MenuAdapter(this, C0386R.layout.menurow, this.menuList);
        listView.setAdapter(this.menuAdapter);
        listView.setOnItemClickListener(new C04111());
        this.btnSlide = (ImageView) tabBar.findViewById(C0386R.id.BtnSlide);
        this.listener = new ClickListenerForScrolling(this.scrollView, this.menu);
        this.btnSlide.setOnClickListener(this.listener);
        this.listener.menuOut = false;
        this.scrollView.initViews(new View[]{this.menu, this.app}, 1, new SizeCallbackForMenu(this.btnSlide));
        if (this.tokenExpiration) {
            MessageUtils.showErrorAlert(this, "El Token es incorrecto o ha expirado.");
        }
    }

    public void onBackPressed() {
        boolean z = false;
        if (this.listener.menuOut) {
            int menuWidth = this.menu.getMeasuredWidth();
            this.menu.setVisibility(0);
            this.scrollView.smoothScrollTo(menuWidth, 0);
            ClickListenerForScrolling clickListenerForScrolling = this.listener;
            if (!this.listener.menuOut) {
                z = true;
            }
            clickListenerForScrolling.menuOut = z;
            return;
        }
        super.onBackPressed();
    }

    protected void onStart() {
        boolean z = false;
        super.onStart();
        if (this.listener.menuOut) {
            int menuWidth = this.menu.getMeasuredWidth();
            this.menu.setVisibility(0);
            this.scrollView.smoothScrollTo(menuWidth, 0);
            ClickListenerForScrolling clickListenerForScrolling = this.listener;
            if (!this.listener.menuOut) {
                z = true;
            }
            clickListenerForScrolling.menuOut = z;
        }
    }

    private void openBrowser(String url) {
        try {
            if (NetworkUtils.isConnected(this)) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            } else {
                MessageUtils.showErrorAlert(this, "Verifique que su dispositivo tiene conexi\u00f3n a internet.");
            }
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    private void openMap() {
        try {
            if (NetworkUtils.isConnected(this)) {
                startActivity(new Intent(this, CampusActivity.class));
            } else {
                MessageUtils.showErrorAlert(this, "Verifique que su dispositivo tiene conexi\u00f3n a internet.");
            }
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    private void openAR() {
        try {
            if (NetworkUtils.isConnected(this)) {
                startActivity(new Intent(this, ARActivity.class));
            } else {
                MessageUtils.showErrorAlert(this, "Verifique que su dispositivo tiene conexi\u00f3n a internet.");
            }
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    protected void prepareHandleException(Exception e) {
        Session session = new Session();
        SharedPreferences preferences = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
        String codAlumno = preferences.getString("CodAlumno", StringUtils.EMPTY);
        String token = preferences.getString("Token", StringUtils.EMPTY);
        session.setCodAlumno(codAlumno);
        session.setToken(token);
        MessageUtils.showErrorAlert(this, "Se encontr\u00f3 un error en la aplicaci\u00f3n.");
        ExceptionUtils.handleException(e, session);
    }

    protected void loggout(boolean tokenExpiration) {
        killToken();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("pe.edu.upc.UPCMovil.ACTION_CHANGE_SESSION_STATE");
        sendBroadcast(broadcastIntent);
        Intent aIntent = new Intent(this, IntranetActivity.class);
        aIntent.putExtra("OptionNumber", 1);
        aIntent.putExtra("OptionString", this.loggedOutOptions[1]);
        aIntent.putExtra("LoggedIn", false);
        if (tokenExpiration) {
            aIntent.putExtra("TokenExpiration", true);
        } else {
            aIntent.putExtra("TokenExpiration", false);
        }
        aIntent.setFlags(67108864);
        startActivity(aIntent);
    }

    protected void killToken() {
        try {
            SharedPreferences preferences = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
            String codAlumno = preferences.getString("CodAlumno", StringUtils.EMPTY);
            String url = Environment.BASE_URL + "/Logoff/?CodAlumno=" + codAlumno + "&Token=" + preferences.getString("Token", StringUtils.EMPTY);
            new RESTTask().execute(new String[]{url});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        int menuWidth = this.menu.getMeasuredWidth();
        this.menu.setVisibility(0);
        if (this.listener.menuOut) {
            this.scrollView.smoothScrollTo(menuWidth, 0);
        } else {
            this.scrollView.smoothScrollTo(0, 0);
        }
        this.listener.menuOut = !this.listener.menuOut;
        return false;
    }
}
