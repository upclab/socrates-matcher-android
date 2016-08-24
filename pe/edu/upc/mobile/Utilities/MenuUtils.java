package pe.edu.upc.mobile.Utilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.UPCMovil.CubiculosComputadorasActivity;
import pe.edu.upc.mobile.UPCMovil.CursosActivity;
import pe.edu.upc.mobile.UPCMovil.EventosActivity;
import pe.edu.upc.mobile.UPCMovil.HorarioActivity;
import pe.edu.upc.mobile.UPCMovil.InasistenciaActivity;
import pe.edu.upc.mobile.UPCMovil.IntranetActivity;
import pe.edu.upc.mobile.UPCMovil.NotasActivity;
import pe.edu.upc.mobile.UPCMovil.NoticiasActivity;
import pe.edu.upc.mobile.UPCMovil.PagosActivity;
import pe.edu.upc.mobile.UPCMovil.TramitesActivity;

public class MenuUtils {
    public static int resourceIdForMenu(boolean loggedIn, int position, String userType) {
        if (!loggedIn) {
            switch (position) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    return C0386R.drawable.intranet;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return C0386R.drawable.eventos;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return C0386R.drawable.noticias;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return C0386R.drawable.directorio;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return C0386R.drawable.convenios;
                default:
                    return 0;
            }
        } else if (userType.equalsIgnoreCase("PROFESOR")) {
            switch (position) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return C0386R.drawable.horario;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return C0386R.drawable.notas;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return C0386R.drawable.eventos;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return C0386R.drawable.noticias;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return C0386R.drawable.directorio;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    return C0386R.drawable.convenios;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    return C0386R.drawable.cerrar;
                default:
                    return 0;
            }
        } else {
            switch (position) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return C0386R.drawable.horario;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return C0386R.drawable.inasistencias;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return C0386R.drawable.pagos;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return C0386R.drawable.notas;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return C0386R.drawable.tramites;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    return C0386R.drawable.eventos;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    return C0386R.drawable.noticias;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    return C0386R.drawable.directorio;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    return C0386R.drawable.convenios;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    return !userType.equalsIgnoreCase("PADRE") ? C0386R.drawable.cubiculos : C0386R.drawable.cerrar;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    return C0386R.drawable.cerrar;
                default:
                    return 0;
            }
        }
    }

    public static String actionForMenu(boolean loggedIn, int position, String userType) {
        if (loggedIn) {
            if (userType.equalsIgnoreCase("PROFESOR")) {
                if (position <= 1) {
                    return "action nothing";
                }
                if (position == 6) {
                    return "action browser http://www3.upc.edu.pe/upc-movil/android-bb/directorio.htm";
                }
                if (position == 7) {
                    return "action browser http://www3.upc.edu.pe/upc-movil/android-bb/convenios.htm";
                }
                if (position == 8) {
                    return "action loggout";
                }
                return "action intent";
            } else if (position <= 1) {
                return "action nothing";
            } else {
                if (position == 9) {
                    return "action browser http://www3.upc.edu.pe/upc-movil/android-bb/directorio.htm";
                }
                if (position == 10) {
                    return "action browser http://www3.upc.edu.pe/upc-movil/android-bb/convenios.htm";
                }
                if (position == 11) {
                    if (userType.equalsIgnoreCase("PADRE")) {
                        return "action loggout";
                    }
                    return "action intent";
                } else if (position == 12) {
                    return "action loggout";
                } else {
                    return "action intent";
                }
            }
        } else if (position <= 0) {
            return "action nothing";
        } else {
            if (position == 4) {
                return "action browser http://www3.upc.edu.pe/upc-movil/android-bb/directorio.htm";
            }
            if (position == 5) {
                return "action browser http://www3.upc.edu.pe/upc-movil/android-bb/convenios.htm";
            }
            return "action intent";
        }
    }

    public static Intent intentWith(Context packageContext, boolean loggedIn, int position, String userType) {
        if (!loggedIn) {
            switch (position) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    return new Intent(packageContext, IntranetActivity.class);
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return new Intent(packageContext, EventosActivity.class);
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return new Intent(packageContext, NoticiasActivity.class);
                default:
                    return null;
            }
        } else if (userType.equalsIgnoreCase("PROFESOR")) {
            switch (position) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return new Intent(packageContext, HorarioActivity.class);
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return new Intent(packageContext, CursosActivity.class);
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return new Intent(packageContext, EventosActivity.class);
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return new Intent(packageContext, NoticiasActivity.class);
                default:
                    return null;
            }
        } else {
            switch (position) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return new Intent(packageContext, HorarioActivity.class);
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return new Intent(packageContext, InasistenciaActivity.class);
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return new Intent(packageContext, PagosActivity.class);
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return new Intent(packageContext, NotasActivity.class);
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return new Intent(packageContext, TramitesActivity.class);
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    return new Intent(packageContext, EventosActivity.class);
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    return new Intent(packageContext, NoticiasActivity.class);
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    return new Intent(packageContext, CubiculosComputadorasActivity.class);
                default:
                    return null;
            }
        }
    }

    public static View viewWithInflater(LayoutInflater inflater, boolean loggedIn, int optionNumber, String userType) {
        if (!loggedIn) {
            switch (optionNumber) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    return inflater.inflate(C0386R.layout.intranetlayout, null);
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return inflater.inflate(C0386R.layout.eventoslayout, null);
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return inflater.inflate(C0386R.layout.noticiaslayout, null);
                default:
                    return null;
            }
        } else if (userType.equalsIgnoreCase("PROFESOR")) {
            switch (optionNumber) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return inflater.inflate(C0386R.layout.horariolayout, null);
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return inflater.inflate(C0386R.layout.cursoslayout, null);
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return inflater.inflate(C0386R.layout.eventoslayout, null);
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return inflater.inflate(C0386R.layout.noticiaslayout, null);
                case 97:
                    return inflater.inflate(C0386R.layout.detallenotalayout, null);
                case 98:
                    return inflater.inflate(C0386R.layout.companeroslayout, null);
                case 99:
                    return inflater.inflate(C0386R.layout.inasistencialayout, null);
                default:
                    return null;
            }
        } else {
            switch (optionNumber) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return inflater.inflate(C0386R.layout.horariolayout, null);
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return inflater.inflate(C0386R.layout.inasistencialayout, null);
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return inflater.inflate(C0386R.layout.pagoslayout, null);
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return inflater.inflate(C0386R.layout.notaslayout, null);
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return inflater.inflate(C0386R.layout.tramiteslayout, null);
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    return inflater.inflate(C0386R.layout.eventoslayout, null);
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    return inflater.inflate(C0386R.layout.noticiaslayout, null);
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    return inflater.inflate(C0386R.layout.cubiculoscomputadoraslayout, null);
                case 94:
                    return inflater.inflate(C0386R.layout.reservarcubiculoscomputadoraslayout, null);
                case 95:
                    return inflater.inflate(C0386R.layout.disponibilidadecubiculoscomputadoraslayout, null);
                case 96:
                    return inflater.inflate(C0386R.layout.reservarespaciosdeportivoslayout, null);
                case 97:
                    return inflater.inflate(C0386R.layout.disponibilidadespaciosdeportivoslayout, null);
                case 98:
                    return inflater.inflate(C0386R.layout.companeroslayout, null);
                case 99:
                    return inflater.inflate(C0386R.layout.detallenotalayout, null);
                default:
                    return null;
            }
        }
    }
}
