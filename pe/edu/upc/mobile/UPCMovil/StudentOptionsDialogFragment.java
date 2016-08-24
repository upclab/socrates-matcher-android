package pe.edu.upc.mobile.UPCMovil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import pe.edu.upc.mobile.C0386R;

public class StudentOptionsDialogFragment extends DialogFragment {
    private StudentOptionsDialogFragmentListener listener;
    private int position;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.StudentOptionsDialogFragment.1 */
    class C04241 implements OnClickListener {
        C04241() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            StudentOptionsDialogFragment.this.listener.onSelectCompaneros(StudentOptionsDialogFragment.this.position);
        }
    }

    /* renamed from: pe.edu.upc.mobile.UPCMovil.StudentOptionsDialogFragment.2 */
    class C04252 implements OnClickListener {
        C04252() {
        }

        public void onClick(DialogInterface dialog, int whichButton) {
            StudentOptionsDialogFragment.this.listener.onSelectNotas(StudentOptionsDialogFragment.this.position);
        }
    }

    public interface StudentOptionsDialogFragmentListener {
        void onSelectCompaneros(int i);

        void onSelectNotas(int i);
    }

    public static StudentOptionsDialogFragment newInstance(int position) {
        StudentOptionsDialogFragment frag = new StudentOptionsDialogFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        frag.setArguments(args);
        return frag;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof StudentOptionsDialogFragmentListener) {
            this.listener = (StudentOptionsDialogFragmentListener) activity;
            return;
        }
        throw new ClassCastException(activity.toString() + " must implement OptionsDialogFragment.OptionsDialogFragmentListener");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.position = getArguments().getInt("position");
        return new Builder(getActivity()).setIcon(C0386R.drawable.ic_launcher).setTitle("Informaci\u00f3n del curso").setPositiveButton("Alumnos", new C04241()).setNegativeButton("Notas", new C04252()).create();
    }
}
