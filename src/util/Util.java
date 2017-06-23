package util;

/**
 * Created by czyczk on 2017-6-23.
 */
public class Util {
    public static String convertDurationToString(int duration) {
        if (duration < 0) throw new IllegalArgumentException("Duration should not be negative.");
        if (duration < 60) {
            return duration + " min";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(duration / 60).append(" h");
            if (duration % 60 != 0) {
                sb.append(' ').append(duration % 60).append(" min");
            }
            return sb.toString();
        }
    }
}
