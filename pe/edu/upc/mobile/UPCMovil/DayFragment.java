package pe.edu.upc.mobile.UPCMovil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.Utilities.ScheduleUtils;

public final class DayFragment extends Fragment {
    private static final String KEY_CONTENT = "DayFragment:Weekday";
    private Calendar day;
    private DayAdapter dayAdapter;
    private ArrayList<String> dayList;
    private ListView lsDay;
    private ArrayList<JSONObject> scheduleDayList;

    private class DayAdapter extends ArrayAdapter<String> {
        public DayAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
            super(context, textViewResourceId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                try {
                    v = ((LayoutInflater) DayFragment.this.getActivity().getSystemService("layout_inflater")).inflate(C0386R.layout.dayrow, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LinearLayout hour = (LinearLayout) v.findViewById(C0386R.id.hour);
            if (position % 2 == 0) {
                hour.setBackgroundColor(Color.parseColor("#EDEDED"));
            } else {
                hour.setBackgroundColor(Color.parseColor("#DEDEDE"));
            }
            int hourOfDay = -1;
            if (Calendar.getInstance().get(5) == DayFragment.this.day.get(5)) {
                hourOfDay = Calendar.getInstance().get(11);
            }
            TextView txtViewHour = (TextView) v.findViewById(C0386R.id.txtViewHour);
            TextView txtViewAMPM = (TextView) v.findViewById(C0386R.id.txtViewAMPM);
            TextView txtViewCourse = (TextView) v.findViewById(C0386R.id.txtViewCourse);
            TextView txtViewSection = (TextView) v.findViewById(C0386R.id.txtViewSection);
            TextView txtViewClassroom = (TextView) v.findViewById(C0386R.id.txtViewClassroom);
            Typeface typeface = Typeface.createFromAsset(DayFragment.this.getActivity().getAssets(), "fonts/Zizou Slab-Regular.otf");
            txtViewHour.setTypeface(typeface);
            txtViewAMPM.setTypeface(typeface);
            txtViewCourse.setTypeface(typeface);
            txtViewSection.setTypeface(typeface);
            txtViewClassroom.setTypeface(typeface);
            int hourIter = 7 + position;
            int hourIterParam = hourIter;
            if (hourOfDay == hourIterParam) {
                hour.setBackgroundColor(Color.parseColor("#FED9D9"));
            }
            if (hourIterParam > 12) {
                hourIter -= 12;
                txtViewAMPM.setText("PM");
            } else {
                txtViewAMPM.setText("AM");
            }
            txtViewHour.setText(hourIter);
            txtViewCourse.setText(" ");
            txtViewSection.setText(" ");
            txtViewClassroom.setText(" ");
            Iterator it = DayFragment.this.scheduleDayList.iterator();
            while (it.hasNext()) {
                JSONObject scheduleClass = (JSONObject) it.next();
                if (ScheduleUtils.checkScheduleClass(scheduleClass, hourIterParam)) {
                    String course = scheduleClass.getString("CursoNombre");
                    String courseCode = scheduleClass.getString("CodCurso");
                    String section = scheduleClass.getString("Seccion");
                    String local = scheduleClass.getString("Sede");
                    String classroom = scheduleClass.getString("Salon");
                    txtViewCourse.setText(new StringBuilder(String.valueOf(courseCode)).append(" - ").append(course).toString());
                    txtViewSection.setText(section);
                    txtViewClassroom.setText(new StringBuilder(String.valueOf(local)).append(" ").append(classroom).toString());
                }
            }
            return v;
        }
    }

    public DayFragment() {
        this.day = null;
        this.scheduleDayList = null;
    }

    public static DayFragment newInstance(Calendar day, ArrayList<JSONObject> scheduleDayList) {
        DayFragment fragment = new DayFragment();
        fragment.day = day;
        fragment.scheduleDayList = scheduleDayList;
        return fragment;
    }

    public void update(ArrayList<JSONObject> scheduleDayList) {
        this.scheduleDayList = scheduleDayList;
        this.dayAdapter.notifyDataSetChanged();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CONTENT)) {
            this.day = (Calendar) savedInstanceState.getSerializable(KEY_CONTENT);
        }
    }

    @SuppressLint({"ResourceAsColor"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Exception e;
        LinearLayout layout = null;
        try {
            LinearLayout layout2 = new LinearLayout(getActivity());
            try {
                this.dayList = new ArrayList();
                for (int i = 0; i < 16; i++) {
                    this.dayList.add(StringUtils.EMPTY);
                }
                this.dayAdapter = new DayAdapter(getActivity(), C0386R.layout.dayrow, this.dayList);
                this.lsDay = new ListView(getActivity());
                this.lsDay.setBackgroundColor(17170445);
                this.lsDay.setSelector(17170445);
                this.lsDay.setCacheColorHint(17170445);
                this.lsDay.setVerticalFadingEdgeEnabled(false);
                this.lsDay.setVerticalScrollBarEnabled(false);
                this.lsDay.setHorizontalFadingEdgeEnabled(false);
                this.lsDay.setHorizontalScrollBarEnabled(false);
                this.lsDay.setLayoutParams(new LayoutParams(-1, -1));
                this.lsDay.setAdapter(this.dayAdapter);
                this.lsDay.setDivider(new ColorDrawable(-1));
                TextView txtViewHeader = new TextView(getActivity());
                txtViewHeader.setLayoutParams(new LayoutParams(-1, -2));
                txtViewHeader.setPadding(0, 20, 0, 20);
                txtViewHeader.setText(new StringBuilder(String.valueOf(this.day.get(5))).append("/").append(this.day.get(2) + 1).append("/").append(this.day.get(1)).toString());
                txtViewHeader.setTextSize(15.0f);
                txtViewHeader.setGravity(17);
                txtViewHeader.setTextColor(Color.parseColor("#FF0000"));
                txtViewHeader.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Zizou Slab-Regular.otf"));
                layout2.setLayoutParams(new LayoutParams(-1, -1));
                layout2.setOrientation(1);
                layout2.addView(txtViewHeader);
                layout2.addView(this.lsDay);
                return layout2;
            } catch (Exception e2) {
                e = e2;
                layout = layout2;
                ((HorarioActivity) getActivity()).prepareHandleException(e);
                return layout;
            }
        } catch (Exception e3) {
            e = e3;
            ((HorarioActivity) getActivity()).prepareHandleException(e);
            return layout;
        }
    }

    public void onResume() {
        super.onResume();
        this.lsDay.setSelection(Calendar.getInstance().get(11) - 8);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONTENT, this.day);
    }
}
