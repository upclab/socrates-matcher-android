package pe.edu.upc.mobile.Utilities;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CursoProfesorUtils {
    public static ArrayList<String> cursosFromResults(ArrayList<JSONObject> results) throws JSONException {
        ArrayList<String> cursos = new ArrayList();
        Iterator it = results.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
            cursos.add("mod://" + jsonObject.getString("codigo") + "://" + jsonObject.getString("descripcion") + "://" + jsonObject.getString("periodo"));
            JSONArray jsonArray = jsonObject.getJSONArray("cursos");
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject iterator = jsonArray.getJSONObject(i);
                cursos.add("cur://" + iterator.getString("curso") + "://" + iterator.getString("cursoId") + "://" + iterator.getString("seccion") + "://" + iterator.getString("grupo") + "://" + jsonObject.getString("codigo") + "://" + jsonObject.getString("periodo"));
            }
        }
        return cursos;
    }
}
