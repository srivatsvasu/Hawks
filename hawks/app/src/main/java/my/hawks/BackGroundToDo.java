package my.hawks;

import android.net.NetworkInfo;

import org.json.JSONObject;

/**
 * Created by VINODHIVYA on 3/14/2015.
 */
public class BackGroundToDo {

    private JSONObject inputJSONObj;
    private String serviceURL;
    private NetworkInfo networkInfo;

    public JSONObject getInputJSONObj() {
        return inputJSONObj;
    }

    public void setInputJSONObj(JSONObject inputJSONObj) {
        this.inputJSONObj = inputJSONObj;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public NetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    public void setNetworkInfo(NetworkInfo networkInfo) {
        this.networkInfo = networkInfo;
    }
}
