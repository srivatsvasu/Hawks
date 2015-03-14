package my.hawks;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by C815317 on 3/12/2015.
 */
public class MapEntity {

    private Double latitude;

    private Double longitude;

    private String userId;

    private String userName;

    private String nearMile;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getNearMile() {
        return nearMile;
    }

    public void setNearMile(String nearMile) {
        this.nearMile = nearMile;
    }
}
