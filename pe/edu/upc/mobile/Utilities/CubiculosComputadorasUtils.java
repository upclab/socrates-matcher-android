package pe.edu.upc.mobile.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

public class CubiculosComputadorasUtils {
    public static ArrayList<String> horasReserva() {
        return new ArrayList(Arrays.asList(new String[]{"7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"}));
    }

    public static ArrayList<String> diasReserva() {
        int i;
        ArrayList<String> days = new ArrayList();
        LocalDate currentDate = new LocalDate();
        int currentDayOfWeek = currentDate.dayOfWeek().get();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy");
        for (i = currentDayOfWeek; i <= 7; i++) {
            days.add(currentDate.withDayOfWeek(i).toString(formatter));
        }
        LocalDate nextWeek = currentDate.plusDays(7);
        for (i = 1; i <= 7; i++) {
            days.add(nextWeek.withDayOfWeek(i).toString(formatter));
        }
        return days;
    }

    public static String parameterWithDateString(String dateString) {
        return DateTimeFormat.forPattern("dd/MM/yy").parseLocalDate(dateString).toString(DateTimeFormat.forPattern("ddMMyyyy")).concat("%200000");
    }

    public static String parameterWithStartDateString(String dateString, String hourString) {
        return DateTimeFormat.forPattern("dd/MM/yy").parseLocalDate(dateString).toString(DateTimeFormat.forPattern("ddMMyyyy")).concat("%20").concat(hourString.replace(":", StringUtils.EMPTY));
    }

    public static String parameterWithEndDateString(String dateString, String hourString, int hours) {
        return DateTimeFormat.forPattern("dd/MM/yy").parseLocalDate(dateString).toString(DateTimeFormat.forPattern("ddMMyyyy")).concat("%20").concat(DateTimeFormat.forPattern("HH:mm").parseLocalTime(hourString).plusHours(hours).toString(DateTimeFormat.forPattern("HHmm")));
    }

    public static String parameterWithResourceNameString(String resourceNameString) {
        return resourceNameString.replace(" ", "%20");
    }

    public static ArrayList<String> disponibilidadFromResults(ArrayList<JSONObject> resultsList) throws JSONException {
        ArrayList<String> disponibilidad = new ArrayList();
        disponibilidad.add("av://CUB\u00cdCULOS");
        Iterator it = resultsList.iterator();
        while (it.hasNext()) {
            JSONObject object = (JSONObject) it.next();
            disponibilidad.add("sc://" + object.getString("NomRecurso") + "://" + object.getString("CodRecurso"));
        }
        return disponibilidad;
    }
}
