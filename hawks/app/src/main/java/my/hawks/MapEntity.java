package my.hawks;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by C815317 on 3/12/2015.
 */
public class MapEntity {

    private Double latitude;

    private Double longitude;

    private String id;

    private String userName;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LatLng getPosition() {
        return new LatLng(this.latitude, this.longitude);
    }

    public LatLng getPosition(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
