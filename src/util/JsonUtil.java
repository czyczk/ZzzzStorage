package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by czyczk on 2017-6-23.
 */
public class JsonUtil {
    private static final Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    private static final Gson gson = new GsonBuilder().create();
    public static Gson getGsonPretty() {
        return gsonPretty;
    }
    public static Gson getGson() {
        return gson;
    }
}
