package pe.edu.upc.mobile.UPCMovil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import pe.edu.upc.mobile.C0386R;

public class SelectorDialogFragment extends DialogFragment {
    private SelectOptionDialogListener listener;
    private ArrayList<String> optionList;
    private int type;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.SelectorDialogFragment.1 */
    class C04211 implements OnItemClickListener {
        C04211() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            SelectorDialogFragment.this.listener.onFinishSelectorDialog((String) SelectorDialogFragment.this.optionList.get(position), SelectorDialogFragment.this.type);
            SelectorDialogFragment.this.dismiss();
        }
    }

    public interface SelectOptionDialogListener {
        void onFinishSelectorDialog(String str, int i);
    }

    private class SelectorAdapter extends ArrayAdapter<String> {
        public SelectorAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            MenuActivity activity = (MenuActivity) SelectorDialogFragment.this.getActivity();
            if (convertView == null) {
                try {
                    convertView = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(C0386R.layout.selectordialogrow, null);
                } catch (Exception e) {
                    activity.prepareHandleException(e);
                }
            }
            String option = (String) SelectorDialogFragment.this.optionList.get(position);
            if (option != null) {
                LinearLayout linearLayout = (LinearLayout) convertView.findViewById(C0386R.id.selectorDialogRow);
                if (position % 2 == 0) {
                    linearLayout.setBackgroundColor(Color.parseColor("#EDEDED"));
                } else {
                    linearLayout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                }
                TextView txtViewEspacioDeportivosOpcionTitle = (TextView) convertView.findViewById(C0386R.id.txtViewSedeName);
                txtViewEspacioDeportivosOpcionTitle.setText(option);
                txtViewEspacioDeportivosOpcionTitle.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/Zizou Slab-Medium.otf"));
            }
            return convertView;
        }
    }

    static SelectorDialogFragment newInstance(int type, ArrayList<String> options) {
        SelectorDialogFragment fragment = new SelectorDialogFragment();
        fragment.setStyle(1, 0);
        Bundle args = new Bundle();
        args.putInt("tipo", type);
        args.putStringArrayList("opciones", options);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SelectOptionDialogListener) {
            this.listener = (SelectOptionDialogListener) activity;
            return;
        }
        throw new ClassCastException(activity.toString() + " must implemenet SelectorDialogFragment.SelectOptionDialogListener");
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.type = getArguments().getInt("tipo");
        ArrayList<String> opciones = (ArrayList) getArguments().getSerializable("opciones");
        this.optionList = new ArrayList();
        this.optionList.addAll(opciones);
        return super.onCreateDialog(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Sede de Reserva");
        ListView listView = new ListView(getActivity());
        listView.setAdapter(new SelectorAdapter(getActivity(), C0386R.layout.selectordialogrow, this.optionList));
        listView.setOnItemClickListener(new C04211());
        return listView;
    }
}
