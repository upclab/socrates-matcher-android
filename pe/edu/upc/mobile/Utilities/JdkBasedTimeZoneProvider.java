package pe.edu.upc.mobile.Utilities;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.TimeZone;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.Provider;

public class JdkBasedTimeZoneProvider implements Provider {
    private static Set<String> availableIds;

    static {
        availableIds = ImmutableSet.copyOf(TimeZone.getAvailableIDs());
    }

    public Set<String> getAvailableIDs() {
        return availableIds;
    }

    public DateTimeZone getZone(String id) {
        if (id == null) {
            return null;
        }
        return DateTimeZone.forOffsetMillis(TimeZone.getTimeZone(id).getOffset(System.currentTimeMillis()));
    }
}
