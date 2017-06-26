package util;

import model.libraryModel.MediaTypeEnum;

/**
 * Created by czycz on 2017-6-26.
 */
public class ServletUtil {
    public static MediaTypeEnum parseMediaType(String str) {
        if (str.equalsIgnoreCase("movie")) {
            return MediaTypeEnum.MOVIE;
        } else if (str.equalsIgnoreCase("music")) {
            return MediaTypeEnum.MUSIC;
        } else if (str.equalsIgnoreCase("tv show")) {
            return MediaTypeEnum.TV_SHOW;
        } else if (str.equalsIgnoreCase("episode")) {
            return MediaTypeEnum.EPISODE;
        } else {
            throw new IllegalArgumentException("Unsupported media type.");
        }
    }
}
