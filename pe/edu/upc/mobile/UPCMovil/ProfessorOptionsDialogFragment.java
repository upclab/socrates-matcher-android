package pe.edu.upc.mobile.UPCMovil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import pe.edu.upc.mobile.C0386R;

public class ProfessorOptionsDialogFragment extends DialogFragment {
    private ProfessorOptionsDialogFragmentListener listener;
    private int position;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.ProfessorOptionsDialogFragment.1 */
    class C04171 implements OnClickListener {
        C04171() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            ProfessorOptionsDialogFragment.this.listener.onSelectInasistencias(ProfessorOptionsDialogFragment.this.position);
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.ProfessorOptionsDialogFragment.2 */
    class C04182 implements OnClickListener {
        C04182() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            ProfessorOptionsDialogFragment.this.listener.onSelectNotas(ProfessorOptionsDialogFragment.this.position);
        }
    }

    public interface ProfessorOptionsDialogFragmentListener {
        void onSelectInasistencias(int i);

        void onSelectNotas(int i);
    }

    public static ProfessorOptionsDialogFragment newInstance(int position) {
        ProfessorOptionsDialogFragment frag = new ProfessorOptionsDialogFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        frag.setArguments(args);
        return frag;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ProfessorOptionsDialogFragmentListener) {
            this.listener = (ProfessorOptionsDialogFragmentListener) activity;
            return;
        }
        throw new ClassCastException(activity.toString() + " must implement OptionsDialogFragment.OptionsDialogFragmentListener");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.position = getArguments().getInt("position");
        return new Builder(getActivity()).setIcon(C0386R.drawable.ic_launcher).setTitle("Informaci\u00f3n del alumno").setPositiveButton("Inasistencias", new C04171()).setNegativeButton("Notas", new C04182()).create();
    }
}
