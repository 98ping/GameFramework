package club.matrixstudios.framework.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import okhttp3.Request;

public class HttpUtil {
    public static void authorize(Request.Builder builder) {
        builder.header("GameFramework", Settings.getApiKey());
    }

    public static String generateQueryString(Map<String, Object> parameters) {
        StringBuilder queryBuilder = new StringBuilder("?");
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (queryBuilder.length() > 1)
                queryBuilder.append("&");
            try {
                queryBuilder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException unsupportedEncodingException) {}
        }
        return queryBuilder.toString();
    }
}

