package pe.edu.upc.mobile.Utilities;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScheduleUtils {
    public static ArrayList<JSONObject> getScheduleForDay(ArrayList<JSONObject> schedule, int day, String userType) throws Exception {
        for (int i = 0; i < schedule.size(); i++) {
            JSONObject scheduleDay = (JSONObject) schedule.get(i);
            JSONArray clases;
            ArrayList<JSONObject> scheduleDayList;
            int j;
            if (userType.equalsIgnoreCase("PROFESOR")) {
                if (Integer.parseInt(scheduleDay.getString("CodDia")) == day) {
                    clases = scheduleDay.getJSONArray("Clases");
                    scheduleDayList = new ArrayList();
                    for (j = 0; j < clases.length(); j++) {
                        scheduleDayList.add(clases.getJSONObject(j));
                    }
                    return scheduleDayList;
                }
            } else if (((Integer) scheduleDay.get("CodDia")).intValue() == day) {
                clases = scheduleDay.getJSONArray("Clases");
                scheduleDayList = new ArrayList();
                for (j = 0; j < clases.length(); j++) {
                    scheduleDayList.add(clases.getJSONObject(j));
                }
                return scheduleDayList;
            }
        }
        return null;
    }

    public static boolean checkScheduleClass(JSONObject scheduleClass, int hourIter) throws Exception {
        String HoraInicio = scheduleClass.getString("HoraInicio");
        String HoraFin = scheduleClass.getString("HoraFin");
        int horaInicioInt = Integer.parseInt(HoraInicio.substring(0, 2));
        int horaFinInt = Integer.parseInt(HoraFin.substring(0, 2));
        if (hourIter < horaInicioInt || hourIter >= horaFinInt) {
            return false;
        }
        return true;
    }
}
