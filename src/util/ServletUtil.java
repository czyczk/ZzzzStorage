package util;

import model.libraryModel.MediaTypeEnum;
import model.servletModel.ServletMessage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by czyczk on 2017-6-26.
 */
public class ServletUtil {
    public static MediaTypeEnum parseMediaType(String str) {
        if (str.equalsIgnoreCase("movie")) {
            return MediaTypeEnum.MOVIE;
        } else if (str.equalsIgnoreCase("music")) {
            return MediaTypeEnum.MUSIC;
        } else if (str.equalsIgnoreCase("tv_show")) {
            return MediaTypeEnum.TV_SHOW;
        } else if (str.equalsIgnoreCase("episode")) {
            return MediaTypeEnum.EPISODE;
        } else {
            throw new IllegalArgumentException("Unsupported media type.");
        }
    }

    public static void sendServletMessage(HttpServletResponse resp, String msgType, String msg) throws IOException {
        ServletMessage message = new ServletMessage(msgType, msg);
        resp.getWriter().write(message.toJson());
    }
}
