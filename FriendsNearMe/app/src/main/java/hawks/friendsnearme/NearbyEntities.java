package hawks.friendsnearme;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class NearbyEntities extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    MapEntity items;
    private static final String TAG = "FindFriends";
    private MapEntity mapEntiy = new MapEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    Location location;

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Log.d(TAG, "Init location lat :");


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(Context.LOCATION_SERVICE);

        if (location != null) {
            latlong = mapEntiy.getPosition(location);

            mMap.addMarker(new MarkerOptions().position(latlong).title("You are here"));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 9));
        }

        String mlocProvider;
        Criteria hdCrit = new Criteria();
        hdCrit.setAccuracy(Criteria.ACCURACY_COARSE);
        mlocProvider = lm.getBestProvider(hdCrit, true);
        Location currentLocation = lm.getLastKnownLocation(mlocProvider);

        findEntities();

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, locationListener);


    }


    private MarkerOptions getMarkerForItem(MapEntity item) {

        return new MarkerOptions().position(new LatLng(item.getLatitude(),
                item.getLongitude())).title(item.getUserName() + " is here");
    }

    LatLngBounds latLngBounds;
    LatLng latlong;
    boolean isZoomSet = false;

    LatLngBounds.Builder builder = new LatLngBounds.Builder();

    public void findEntities() {
        List<MapEntity> items = getEntityLocations();

        for (MapEntity item : items) {
            mMap.addMarker(getMarkerForItem(item));
            builder.include(item.getPosition());
        }
        latLngBounds = builder.build();

    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mMap.clear();
            builder = new LatLngBounds.Builder();
            latlong = new LatLng(location.getLatitude(),
                    location.getLongitude());
            Log.d(TAG, "Entering location lat :" + location.getLatitude() + " lng:" + location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latlong).title("You are here")

                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            builder.include(latlong);

            findEntities();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 40));


        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public List<MapEntity> getEntityLocations() {

        return HttpManager.getEntityLocations(mapEntiy.getId());

    }


}
