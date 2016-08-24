package pe.edu.upc.mobile.Utilities;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EspaciosDeportivosUtils {
    public static String parameterWithStartHour(String startHour) {
        return new StringBuilder(String.valueOf(startHour)).append("00").toString();
    }

    public static String parameterWithStartHour(String startHour, String hours) {
        return DateTimeFormat.forPattern("HHmm").parseLocalTime(startHour).plusHours(Integer.parseInt(hours)).toString(DateTimeFormat.forPattern("HHmm")).concat("00");
    }

    public static ArrayList<String> sedesNamesFromResults(ArrayList<JSONObject> resultsList) throws JSONException {
        ArrayList<String> sedesNames = new ArrayList();
        Iterator it = resultsList.iterator();
        while (it.hasNext()) {
            sedesNames.add(((JSONObject) it.next()).getString("sede"));
        }
        return sedesNames;
    }

    public static ArrayList<String> espaciosNamesFromResults(ArrayList<JSONObject> resultsList, String sedeKey) throws JSONException {
        ArrayList<String> espaciosNames = new ArrayList();
        Iterator it = resultsList.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
            if (jsonObject.getString("key").equalsIgnoreCase(sedeKey)) {
                JSONArray espaciosArray = jsonObject.getJSONArray("espacios");
                int length = espaciosArray.length();
                for (int i = 0; i < length; i++) {
                    espaciosNames.add(espaciosArray.getJSONObject(i).getString("nombre"));
                }
            }
        }
        return espaciosNames;
    }

    public static ArrayList<String> actividadesNamesFromResults(ArrayList<JSONObject> resultsList, String sedeKey, String espacioCode) throws JSONException {
        ArrayList<String> actividadesNames = new ArrayList();
        Iterator it = resultsList.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
            if (jsonObject.getString("key").equalsIgnoreCase(sedeKey)) {
                JSONArray espaciosArray = jsonObject.getJSONArray("espacios");
                int length = espaciosArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject espacio = espaciosArray.getJSONObject(i);
                    if (espacio.getString("codigo").equalsIgnoreCase(espacioCode)) {
                        JSONArray actividadesArray = espacio.getJSONArray("actividades");
                        int size = actividadesArray.length();
                        for (int j = 0; j < size; j++) {
                            actividadesNames.add(actividadesArray.getJSONObject(j).getString("nombre"));
                        }
                    }
                }
            }
        }
        return actividadesNames;
    }

    public static String sedeKeyWithNameFromResults(ArrayList<JSONObject> resultsList, String sedeName) throws JSONException {
        Iterator it = resultsList.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
            if (jsonObject.getString("sede").equalsIgnoreCase(sedeName)) {
                return jsonObject.getString("key");
            }
        }
        return StringUtils.EMPTY;
    }

    public static String espacioCodeWithNameAndSedeNameFromResults(ArrayList<JSONObject> resultsList, String espacioName, String sedeName) throws JSONException {
        Iterator it = resultsList.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
            if (jsonObject.getString("sede").equalsIgnoreCase(sedeName)) {
                JSONArray espaciosArray = jsonObject.getJSONArray("espacios");
                int length = espaciosArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject iterator = espaciosArray.getJSONObject(i);
                    if (iterator.getString("nombre").equalsIgnoreCase(espacioName)) {
                        return iterator.getString("codigo");
                    }
                }
                continue;
            }
        }
        return StringUtils.EMPTY;
    }

    public static String actividadCodeWithNameAndEspacioNameSedeNameFromResults(ArrayList<JSONObject> resultsList, String actividadName, String espacioName, String sedeName) throws JSONException {
        Iterator it = resultsList.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
            if (jsonObject.getString("sede").equalsIgnoreCase(sedeName)) {
                JSONArray espaciosArray = jsonObject.getJSONArray("espacios");
                int length = espaciosArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject espacio = espaciosArray.getJSONObject(i);
                    if (espacio.getString("nombre").equalsIgnoreCase(espacioName)) {
                        JSONArray actividadArray = espacio.getJSONArray("actividades");
                        int size = actividadArray.length();
                        for (int j = 0; j < size; j++) {
                            JSONObject iterator = actividadArray.getJSONObject(j);
                            if (iterator.getString("nombre").equalsIgnoreCase(actividadName)) {
                                return iterator.getString("codigo");
                            }
                        }
                        continue;
                    }
                }
                continue;
            }
        }
        return null;
    }

    public static ArrayList<String> weeksNamesFromNow() {
        ArrayList<String> weeksNames = new ArrayList();
        LocalDate currentWeek = new LocalDate();
        LocalDate firstMonday = currentWeek.withDayOfWeek(1);
        LocalDate firstSunday = currentWeek.withDayOfWeek(7);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy");
        weeksNames.add(firstMonday.toString(formatter) + " - " + firstSunday.toString(formatter));
        LocalDate nextWeek = currentWeek.plusDays(7);
        weeksNames.add(new StringBuilder(String.valueOf(nextWeek.withDayOfWeek(1).toString(formatter))).append(" - ").append(nextWeek.withDayOfWeek(7).toString(formatter)).toString());
        return weeksNames;
    }

    public static String startDateFromWeek(String week) {
        String[] parts = week.split(" - ");
        return LocalDate.parse(parts[0], DateTimeFormat.forPattern("dd/MM/yy")).toString(DateTimeFormat.forPattern("yyyyMMdd"));
    }

    public static String endDateFromWeek(String week) {
        String[] parts = week.split(" - ");
        return LocalDate.parse(parts[1], DateTimeFormat.forPattern("dd/MM/yy")).toString(DateTimeFormat.forPattern("yyyyMMdd"));
    }

    public static ArrayList<String> disponibilidadFromResults(ArrayList<JSONObject> resultsList) throws JSONException {
        ArrayList<String> disponibilidad = new ArrayList();
        int lastCodigoDia = 1;
        int[] checked = new int[7];
        Iterator it = resultsList.iterator();
        while (it.hasNext()) {
            int i;
            JSONObject jsonDisponibilidad = (JSONObject) it.next();
            int codigoDia = jsonDisponibilidad.getInt("CodDia");
            String fecha = jsonDisponibilidad.getString("Fecha");
            DateTimeFormatter availabilityReadFormatter = DateTimeFormat.forPattern("yyyyMMdd");
            DateTimeFormatter availabilityPrintFormatter = DateTimeFormat.forPattern("dd/MM/yy");
            DateTimeFormatter scheduleReadFormatter = DateTimeFormat.forPattern("yyyyMMdd HHmmss");
            DateTimeFormatter schedulePrintFormatter = DateTimeFormat.forPattern("HH:mm");
            LocalDate availableLocalDate = LocalDate.parse(fecha, availabilityReadFormatter);
            for (i = lastCodigoDia; i < codigoDia; i++) {
                switch (i) {
                    case Value.TYPE_FIELD_NUMBER /*1*/:
                        if (checked[0] != 0) {
                            break;
                        }
                        disponibilidad.add("un://Lunes " + availableLocalDate.withDayOfWeek(1).toString(availabilityPrintFormatter));
                        break;
                    case Value.STRING_FIELD_NUMBER /*2*/:
                        if (checked[1] != 0) {
                            break;
                        }
                        disponibilidad.add("un://Martes " + availableLocalDate.withDayOfWeek(2).toString(availabilityPrintFormatter));
                        break;
                    case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                        if (checked[2] != 0) {
                            break;
                        }
                        disponibilidad.add("un://Mi\u00e9rcoles " + availableLocalDate.withDayOfWeek(3).toString(availabilityPrintFormatter));
                        break;
                    case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                        if (checked[3] != 0) {
                            break;
                        }
                        disponibilidad.add("un://Jueves " + availableLocalDate.withDayOfWeek(4).toString(availabilityPrintFormatter));
                        break;
                    case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                        if (checked[4] != 0) {
                            break;
                        }
                        disponibilidad.add("un://Viernes " + availableLocalDate.withDayOfWeek(5).toString(availabilityPrintFormatter));
                        break;
                    case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                        if (checked[5] != 0) {
                            break;
                        }
                        disponibilidad.add("un://S\u00e1bado " + availableLocalDate.withDayOfWeek(6).toString(availabilityPrintFormatter));
                        break;
                    case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                        if (checked[6] != 0) {
                            break;
                        }
                        disponibilidad.add("un://Domingo " + availableLocalDate.withDayOfWeek(7).toString(availabilityPrintFormatter));
                        break;
                    default:
                        break;
                }
            }
            switch (codigoDia) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    disponibilidad.add("av://Lunes " + availableLocalDate.toString(availabilityPrintFormatter));
                    checked[0] = 1;
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    disponibilidad.add("av://Martes " + availableLocalDate.toString(availabilityPrintFormatter));
                    checked[1] = 1;
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    disponibilidad.add("av://Mi\u00e9rcoles " + availableLocalDate.toString(availabilityPrintFormatter));
                    checked[2] = 1;
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    disponibilidad.add("av://Jueves " + availableLocalDate.toString(availabilityPrintFormatter));
                    checked[3] = 1;
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    disponibilidad.add("av://Viernes " + availableLocalDate.toString(availabilityPrintFormatter));
                    checked[4] = 1;
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    disponibilidad.add("av://S\u00e1bado " + availableLocalDate.toString(availabilityPrintFormatter));
                    checked[5] = 1;
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    disponibilidad.add("av://Domingo " + availableLocalDate.toString(availabilityPrintFormatter));
                    checked[6] = 1;
                    break;
            }
            JSONArray horarios = jsonDisponibilidad.getJSONArray("Disponibles");
            int lenght = horarios.length();
            for (i = 0; i < lenght; i++) {
                JSONObject iterator = horarios.getJSONObject(i);
                String iteratorFecha = iterator.getString("Fecha");
                String str = "Fecha";
                disponibilidad.add("sc://" + LocalDateTime.parse(new StringBuilder(String.valueOf(iteratorFecha)).append(" ").append(iterator.getString("HoraInicio")).toString(), scheduleReadFormatter).toString(schedulePrintFormatter) + " - " + LocalDateTime.parse(new StringBuilder(String.valueOf(iteratorFecha)).append(" ").append(iterator.getString("HoraFin")).toString(), scheduleReadFormatter).toString(schedulePrintFormatter) + "://" + iterator.getString(r26) + "://" + LocalDate.parse(iteratorFecha, availabilityReadFormatter).toString(availabilityPrintFormatter));
            }
            lastCodigoDia = codigoDia;
        }
        return disponibilidad;
    }
}
