package my.hawks;

import android.net.NetworkInfo;

/**
 * Created by VINODHIVYA on 3/14/2015.
 */
public class BackGroundToDo {

    private MapEntity mapEntity;
    private String serviceMethodCall;
    private NetworkInfo networkInfo;

    public MapEntity getMapEntity() {
        return mapEntity;
    }

    public void setMapEntity(MapEntity mapEntity) {
        this.mapEntity = mapEntity;
    }

    public String getServiceMethodCall() {
        return serviceMethodCall;
    }

    public void setServiceMethodCall(String serviceMethodCall) {
        this.serviceMethodCall = serviceMethodCall;
    }

    public NetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    public void setNetworkInfo(NetworkInfo networkInfo) {
        this.networkInfo = networkInfo;
    }
}
