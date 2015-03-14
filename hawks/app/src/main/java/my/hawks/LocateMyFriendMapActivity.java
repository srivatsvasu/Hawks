package my.hawks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocateMyFriendMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapEntity entity;
    private NetworkInfo netInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();

        setContentView(R.layout.activity_locate_my_friend_map);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        // Update your UI here.
                    }
                });

        String userId = getIntent().getExtras().getString("userId");
        this.entity = getMyFriendLocation(userId);

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

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(32.858599, -97.322676)).title(this.userId));
        MarkerOptions mMarker = new MarkerOptions();
        mMap.addMarker(mMarker.position(new LatLng(entity.getLatitude(), entity.getLongitude())).title(entity.getUserName()));
        LatLng latLng = new LatLng(entity.getLatitude(), entity.getLongitude()); // giving your marker to zoom to your location area.
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 3000, null);
    }

    public MapEntity getMyFriendLocation(String friendUserId){
        MapEntity friendEntity = null;

        JSONObject jsonobj = new JSONObject();
        JSONArray jsonArray = null;

        try{

            jsonobj.put("UserID", friendUserId);

            BackGroundToDo toDo = new BackGroundToDo();
            toDo.setServiceURL("http://javagrasp.info/hawks/postMyLoc/postIt");
            toDo.setInputJSONObj(jsonobj);
            toDo.setNetworkInfo(netInfo);
            String outputData = new ServiceUtil().callWebService(toDo);

            if(outputData != null){
                jsonArray = new JSONArray(outputData);
                friendEntity = new MapEntity();
                friendEntity.setUserName(jsonArray.getJSONObject(0).getString("UserName"));
                friendEntity.setUserId(jsonArray.getJSONObject(0).getString("UserID"));
                friendEntity.setUserName(jsonArray.getJSONObject(0).getString("Latitude"));
                friendEntity.setUserId(jsonArray.getJSONObject(0).getString("Longitude"));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return friendEntity;
    }
}
