package club.matrixstudios.framework.http;

import club.matrixstudios.framework.GameFramework;
import com.google.common.collect.ImmutableMap;
import java.net.ConnectException;
import java.util.Map;
import net.frozenorb.qlib.qLib;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RequestHandler {
    private static long lastAPIError = 0L;

    public static long getLastAPIError() {
        return lastAPIError;
    }

    private static long lastAPIRequest = 0L;

    public static long getLastAPIRequest() {
        return lastAPIRequest;
    }

    private static boolean apiDown = false;

    private static long averageLatency;

    public static boolean isApiDown() {
        return apiDown;
    }

    private static int averageLatencyTicks = 0;

    private static long lastLatency;

    public static long getLastLatency() {
        return lastLatency;
    }

    public static double getAverageLatency() {
        if (averageLatencyTicks == 0)
            return -1.0D;
        return averageLatency / averageLatencyTicks;
    }

    public static RequestResponse get(String endpoint) {
        return get(endpoint, null);
    }

    public static RequestResponse get(String endpoint, Map<String, Object> queryParameters) {
        Request.Builder builder = new Request.Builder();
        builder.get();
        builder.url(Settings.getApiHost() + endpoint + ((queryParameters != null) ? HttpUtil.generateQueryString(queryParameters) : ""));
        HttpUtil.authorize(builder);
        lastAPIRequest = System.currentTimeMillis();
        try {
            long start = System.currentTimeMillis();
            Response response = GameFramework.getOkHttpClient().newCall(builder.build()).execute();
            if (response.code() >= 500) {
                apiDown = true;
                lastAPIError = System.currentTimeMillis();
                return new RequestResponse(false, "Could not connect to API", null, builder);
            }
            apiDown = false;
            lastLatency = System.currentTimeMillis() - start;
            averageLatency += System.currentTimeMillis() - start;
            averageLatencyTicks++;
            String body = response.body().string();
            try {
                JSONObject object = new JSONObject(new JSONTokener(body));
                if (object.has("success") && !object.getBoolean("success"))
                    return new RequestResponse(false, object.getString("message"), body, builder);
            } catch (JSONException jSONException) {}
            return new RequestResponse(true, null, body, builder);
        } catch (ConnectException|java.net.UnknownHostException e) {
            apiDown = true;
            lastAPIError = System.currentTimeMillis();
            return new RequestResponse(false, "Could not connect to API", null, builder);
        } catch (Exception e) {
            e.printStackTrace();
            lastAPIError = System.currentTimeMillis();
            return new RequestResponse(false, "Failed to get response from API", null, builder);
        }
    }

    public static RequestResponse post(String endpoint, Map<String, Object> bodyParams) {
        Request.Builder builder = new Request.Builder();
        if (bodyParams != null)
            for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
                if (entry.getValue() instanceof Double && !Double.isFinite(((Double)entry.getValue()).doubleValue()))
                    entry.setValue(Double.valueOf(0.0D));
            }
        String bodyJson = qLib.PLAIN_GSON.toJson((bodyParams == null) ? ImmutableMap.of() : bodyParams);
        builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyJson));
        builder.url(Settings.getApiHost() + endpoint);
        HttpUtil.authorize(builder);
        lastAPIRequest = System.currentTimeMillis();
        try {
            long start = System.currentTimeMillis();
            Response response = GameFramework.getOkHttpClient().newCall(builder.build()).execute();
            if (response.code() >= 500) {
                apiDown = true;
                lastAPIError = System.currentTimeMillis();
                return new RequestResponse(false, "Could not connect to API", null, builder);
            }
            apiDown = false;
            lastLatency = System.currentTimeMillis() - start;
            averageLatency += System.currentTimeMillis() - start;
            averageLatencyTicks++;
            String body = response.body().string();
            try {
                JSONObject object = new JSONObject(new JSONTokener(body));
                if (object.has("success") && !object.getBoolean("success"))
                    return new RequestResponse(false, object.getString("message"), body, builder);
            } catch (JSONException jSONException) {}
            return new RequestResponse(true, null, body, builder);
        } catch (ConnectException|java.net.UnknownHostException e) {
            apiDown = true;
            lastAPIError = System.currentTimeMillis();
            return new RequestResponse(false, "Could not connect to API", null, builder);
        } catch (Exception e) {
            e.printStackTrace();
            lastAPIError = System.currentTimeMillis();
            return new RequestResponse(false, "Failed to get response from API", null, builder);
        }
    }

    public static RequestResponse delete(String endpoint, Map<String, Object> bodyParams) {
        Request.Builder builder = new Request.Builder();
        String bodyJson = qLib.PLAIN_GSON.toJson((bodyParams == null) ? ImmutableMap.of() : bodyParams);
        builder.delete(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyJson));
        builder.url(Settings.getApiHost() + endpoint);
        HttpUtil.authorize(builder);
        lastAPIRequest = System.currentTimeMillis();
        try {
            long start = System.currentTimeMillis();
            Response response = GameFramework.getOkHttpClient().newCall(builder.build()).execute();
            if (response.code() >= 500) {
                apiDown = true;
                lastAPIError = System.currentTimeMillis();
                return new RequestResponse(false, "Could not connect to API", null, builder);
            }
            apiDown = false;
            lastLatency = System.currentTimeMillis() - start;
            averageLatency += System.currentTimeMillis() - start;
            averageLatencyTicks++;
            String body = response.body().string();
            try {
                JSONObject object = new JSONObject(new JSONTokener(body));
                if (object.has("success") && !object.getBoolean("success"))
                    return new RequestResponse(false, object.getString("message"), body, builder);
            } catch (JSONException jSONException) {}
            return new RequestResponse(true, null, body, builder);
        } catch (ConnectException|java.net.UnknownHostException e) {
            apiDown = true;
            lastAPIError = System.currentTimeMillis();
            return new RequestResponse(false, "Could not connect to API", null, builder);
        } catch (Exception e) {
            e.printStackTrace();
            lastAPIError = System.currentTimeMillis();
            return new RequestResponse(false, "Failed to get response from API", null, builder);
        }
    }

    public static RequestResponse send(Request.Builder builder) {
        lastAPIRequest = System.currentTimeMillis();
        try {
            long start = System.currentTimeMillis();
            Response response = GameFramework.getOkHttpClient().newCall(builder.build()).execute();
            if (response.code() >= 500) {
                apiDown = true;
                lastAPIError = System.currentTimeMillis();
                return new RequestResponse(false, "Could not connect to API", null, builder);
            }
            apiDown = false;
            lastLatency = System.currentTimeMillis() - start;
            averageLatency += System.currentTimeMillis() - start;
            averageLatencyTicks++;
            String body = response.body().string();
            try {
                JSONObject object = new JSONObject(new JSONTokener(body));
                if (object.has("success") && !object.getBoolean("success"))
                    return new RequestResponse(false, object.getString("message"), body, builder);
            } catch (JSONException jSONException) {}
            return new RequestResponse(true, null, body, builder);
        } catch (ConnectException|java.net.UnknownHostException e) {
            apiDown = true;
            lastAPIError = System.currentTimeMillis();
            return new RequestResponse(false, "Could not connect to API", null, builder);
        } catch (Exception e) {
            e.printStackTrace();
            lastAPIError = System.currentTimeMillis();
            return new RequestResponse(false, "Failed to get response from API", null, builder);
        }
    }
}

