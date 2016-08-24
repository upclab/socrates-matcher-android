package pe.edu.upc.mobile.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import pe.edu.upc.mobile.C0386R;

public class MessageUtils {

    /* renamed from: pe.edu.upc.mobile.Utilities.MessageUtils.1 */
    class C04281 implements OnClickListener {
        C04281() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    public static void showErrorAlert(Context context, String msg) {
        try {
            AlertDialog alertDialog = new Builder(context).create();
            alertDialog.setTitle("UPC M\u00f3vil");
            alertDialog.setIcon(C0386R.drawable.ic_launcher);
            alertDialog.setMessage(msg);
            alertDialog.setButton("OK", new C04281());
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AlertDialog buildLoadingAlert(Context context) throws Exception {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setTitle("Cargando");
        alertDialog.setIcon(C0386R.drawable.ic_launcher);
        alertDialog.setView(((Activity) context).getLayoutInflater().inflate(C0386R.layout.loadingdialog, null));
        return alertDialog;
    }
}
